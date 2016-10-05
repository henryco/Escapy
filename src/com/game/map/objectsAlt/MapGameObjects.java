package com.game.map.objectsAlt;

import com.badlogic.gdx.graphics.GL20;
import com.game.map.objectsAlt.layers.LayerContainer;
import com.game.map.objectsAlt.layers.ObjectLayer;
import com.game.map.objectsAlt.objects.AnimatedObject;
import com.game.map.objectsAlt.objects.GameObject;
import com.game.map.objectsAlt.objects.StaticObject;
import com.game.map.objectsAlt.objects.utils.PositionCorrector;
import com.game.map.objectsAlt.objects.utils.ZoomCalculator;
import com.game.render.EscapyUniRender;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.mask.LightMask;
import net.henryco.struct.Struct;
import net.henryco.struct.container.StructContainer;
import net.henryco.struct.container.tree.StructNode;
import net.henryco.struct.container.tree.StructTree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Henry on 03/10/16.
 */
public class MapGameObjects {

	public final LayerContainer[] layerContainers;

	public MapGameObjects(int[] dim, String location, String cfg, EscapyUniRender ... uniRenders) {
		layerContainers = this.initGameObjects(dim, location, cfg, uniRenders);
	}

	public void forEach(Consumer<LayerContainer> cons) {
		Arrays.stream(layerContainers).forEach(cons);
	}

	public void renderNormals(EscapyGdxCamera camera) {
		for (LayerContainer c : layerContainers) c.forEach(l -> l.renderNormals(camera));
	}

	public void renderLightMap(EscapyGdxCamera camera) {
		for (LayerContainer c : layerContainers) c.forEach(l -> l.renderLightMap(camera));
	}

	private LayerContainer[] initGameObjects(int[] dim, String location, String cfgFile, EscapyUniRender ... uniRenders) {

		System.out.println(cfgFile);

		List<String[]>[] containerList = Struct.printDataFile(Struct.in.readStructData(cfgFile));
		StructTree containerTree = StructContainer.tree(containerList);
		System.out.println(containerTree);

		return loadContainer(containerTree.mainNode.getStruct("map"), dim, location, uniRenders);
	}

	private static LayerContainer[] loadContainer(StructNode mapNode, int[] dim, String location, EscapyUniRender ... uniRenders) {

		List<LayerContainer> containers = new ArrayList<>();
		StructNode container = mapNode.getStruct("container");
		int[] iter = getIntArray(container.getStructChild());
		for (int i : iter) {
			StructNode actualCont = container.getStruct(Integer.toString(i));
			String fboName = "";
			if (actualCont.containsPrimitive("fboName")) fboName = actualCont.getPrimitive("fboName");
			containers.add(fillContainer(new LayerContainer(dim, fboName), actualCont, location, dim, uniRenders));
		}
		return containers.toArray(new LayerContainer[containers.size()]);
	}

	private static LayerContainer fillContainer(LayerContainer container, StructNode containerNode, String location, int[] dim, EscapyUniRender ... uniRenders) {

		if (containerNode.containsStruct("mask")) container.setMask(loadMask(containerNode.getStruct("mask"), dim));
		if (containerNode.containsStruct("layer")) {
			StructNode layerNode = containerNode.getStruct("layer");
			int[] iter = getIntArray(layerNode.getStructChild());
			for (int i : iter) container.addSource(loadLayer(layerNode.getStruct(Integer.toString(i)), location, dim, uniRenders));
		}
		return container;
	}

	private static LightMask loadMask(StructNode maskNode, int[] dim) {

		if (maskNode.containsPrimitive("0") && maskNode.getPrimitive("0").equalsIgnoreCase("null")) return null;

		String name;
		int[] color = new int[4];
		int[] dimension = new int[]{0, 0, dim[0], dim[1]};
		int[] glblend = new int[2];
		int id;
		boolean buffered;

		name = maskNode.getPrimitive("0", "name");
		buffered = Boolean.parseBoolean(maskNode.getPrimitive("4", "buffered"));
		id = Integer.parseInt(maskNode.getPrimitive("5", "id"));

		color[0] = Integer.parseInt(maskNode.getStruct("3", "color").getPrimitive("0", "r"));
		color[1] = Integer.parseInt(maskNode.getStruct("3", "color").getPrimitive("1", "g"));
		color[2] = Integer.parseInt(maskNode.getStruct("3", "color").getPrimitive("2", "b"));
		color[3] = Integer.parseInt(maskNode.getStruct("3", "color").getPrimitive("3", "a"));

		int[] iter = getIntArray(maskNode.getStruct("2", "dimension").getPrimitiveChild());
		if (iter.length == 4) {
			for (int i : iter) dimension[i] = Integer.parseInt(maskNode.getStruct("2",  "dimension").getPrimitive(Integer.toString(i)));
		} else if (iter.length == 2) {
			dimension[2] = Integer.parseInt(maskNode.getStruct("2",  "dimension").getPrimitive("0"));
			dimension[3] = Integer.parseInt(maskNode.getStruct("2",  "dimension").getPrimitive("1"));
		}

		StructNode blendNode = maskNode.getStruct("1", "glProgram");
		if (blendNode.containsStruct("glBlendFunc")) {

			StructNode addBlendNode = blendNode.getStruct("glBlendFunc");
			for (int i = 0; i < glblend.length; i++) {
				String nameFun = addBlendNode.getPrimitive(Integer.toString(i));
				try {
					Field addProgram = GL20.class.getDeclaredField(nameFun);
					addProgram.setAccessible(true);
					glblend[i] = (int) addProgram.get(GL20.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else if (blendNode.containsStruct("builtIn")) {
			StructNode addBlendNode = blendNode.getStruct("builtIn");
			String nameField = addBlendNode.getPrimitive("0", "name");
			try {
				Field addProgram = LightMask.class.getDeclaredField(nameField);
				addProgram.setAccessible(true);
				glblend = (int[]) addProgram.get(LightMask.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new LightMask(dimension, buffered, name).setColor(color).setMaskFunc(glblend).setID(id);
	}

	private static ObjectLayer loadLayer(StructNode actLayerNode, String location, int[] dim, EscapyUniRender ... uniRenders){

		String layerName = "";
		float[] xyShift = new float[2];
		if (actLayerNode.containsPrimitive("name")) layerName = actLayerNode.getPrimitive("name");
		if (actLayerNode.containsStruct("stepShift")) {
			xyShift[0] = Float.parseFloat(actLayerNode.getStruct("stepShift").getPrimitive("0", "x"));
			xyShift[1] = Float.parseFloat(actLayerNode.getStruct("stepShift").getPrimitive("1", "y"));
		}
		int[] dimension = new int[]{dim[dim.length - 2], dim[dim.length - 1]};
		return fillLayer(new ObjectLayer(xyShift[0], xyShift[1], dimension[0], dimension[1], layerName), actLayerNode, location, dim, uniRenders);
	}

	private static ObjectLayer fillLayer(ObjectLayer layer, StructNode actLayerNode, String location, int[] dim, EscapyUniRender ... uniRenders) {

		if (actLayerNode.containsStruct("objects")) {
			StructNode objectsNode = actLayerNode.getStruct("objects");
			int[] iter = getIntArray(objectsNode.getStructChild());
			for (int i : iter) layer.addSource(loadGameObject(objectsNode.getStruct(Integer.toString(i)), location, dim));
		}
		if (actLayerNode.containsStruct("uniRender"))
			layer = loadUniRenders(layer, actLayerNode.getStruct("uniRender"), uniRenders);
		return layer;
	}

	private static ObjectLayer loadUniRenders(ObjectLayer layer, StructNode uniRenderNode, EscapyUniRender ... uniRenders) {

		int[] iter = getIntArray(uniRenderNode.getPrimitiveChild());
		for (int i : iter) {
			if (uniRenderNode.getPrimitive(Integer.toString(i)).equalsIgnoreCase("CHARACTERS")) layer.addSource(uniRenders[0]);
		}
		return layer;
	}

	private static GameObject loadGameObject(StructNode actObjectNode, String location, int[] dim) {

		GameObject gameObject = null;
		String textureUrl;
		float[] position = new float[2];
		float scale;
		int type;
		boolean hookUp;
		int[] period = new int[10];
		int zPos;

		try {
			Field typeField = GameObject.type.class.getDeclaredField(actObjectNode.getPrimitive("type", "3"));
			typeField.setAccessible(true);

			for (int i : getIntArray(actObjectNode.getStruct("period", "5").getStructChild()))
				period[i] = Integer.parseInt(actObjectNode.getStruct("period", "5").getPrimitive(Integer.toString(i)));

			scale = getScale(actObjectNode.getPrimitive("scale", "2"));
			textureUrl = getFileName(location, prep(actObjectNode.getPrimitive("url", "file", "img", "0")));
			position[0] = Float.parseFloat(actObjectNode.getStruct("position", "1").getPrimitive("0", "x"));
			position[1] = Float.parseFloat(actObjectNode.getStruct("position", "1").getPrimitive("1", "y"));
			type = (int) typeField.get(GameObject.type.class.newInstance());
			hookUp = getHook(actObjectNode.getPrimitive("hook", "4"));
			zPos = Integer.parseInt(actObjectNode.getPrimitive("z", "zPos", "6"));

			gameObject = createGameObject(textureUrl, position, scale, type, hookUp, period, zPos, dim);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return gameObject;
	}

	private static GameObject createGameObject(String url, float[] pos, float scale, int type, boolean hookUp, int[] period, int zpos, int[] dim) {

		GameObject gameObject = null;
		PositionCorrector posF = null;
		ZoomCalculator zoomF = null;
		if (!hookUp) posF = (frameW, frameH, w, h) -> new float[]{0, (0 - (h - frameH))};
		if (Float.isNaN(scale)) {
			scale = 1;
			zoomF = (frameW, frameH, w, h, zoom) -> Math.max((frameW / w), (frameH / h));
		}
		if (type == GameObject.type.ANIMATED || type == GameObject.type.INTERACTIVE)
			gameObject = new AnimatedObject(pos[0], pos[1], zpos, url, scale, type, period, dim[0], dim[1]);
		else if (type == GameObject.type.STATIC) gameObject = new StaticObject(pos[0], pos[1], zpos, url, scale, type, dim[0], dim[1]);
		if (gameObject != null) {
			boolean reinit = false;
			if (posF != null) {
				gameObject.setPositionFunc(posF);
				reinit = true;
			}
			if (zoomF != null) {
				gameObject.setZoomFunc(zoomF);
				reinit = true;
			}
			if (reinit) gameObject.initializeGraphic();
		}
		return gameObject;
	}

	private static float getScale(String scale) {
		return (scale.equalsIgnoreCase("MAX")) ? Float.NaN : Float.parseFloat(scale);
	}

	private static boolean getHook(String hook) {
		return (!hook.equalsIgnoreCase("DOWN"));
	}

	private static String getFileName(String ... name) {
		String forReturn = "";
		for (String n : name) forReturn += n;
		return forReturn;
	}

	private static String prep(String name) {
		if (!name.startsWith("/")) name = "/" + name;
		return name;
	}

	private static int[] getIntArray(String[] arr) {
		List<Integer> intList = new ArrayList<>();
		for (String iter : arr) try {
				intList.add(Integer.parseInt(iter));
			} catch (Exception ignored) {}
		Integer[] iar = intList.toArray(new Integer[intList.size()]);
		int[] ret = new int[iar.length];
		for (int i = 0; i < ret.length; i++) ret[i] = iar[i];
		Arrays.sort(ret);
		return ret;
	}
}
