package com.game.map.objects;

import com.badlogic.gdx.graphics.GL20;
import com.game.map.objects.layers.LayerContainer;
import com.game.map.objects.layers.ObjectLayer;
import com.game.map.objects.objects.AnimatedObject;
import com.game.map.objects.objects.GameObject;
import com.game.map.objects.objects.StaticObject;
import com.game.map.objects.objects.utils.PositionCorrector;
import com.game.map.objects.objects.utils.PositionTranslator;
import com.game.map.objects.objects.utils.ZoomCalculator;
import com.game.map.objects.objects.utils.translators.GameObjTranslators;
import com.game.render.EscapyUniRender;
import com.game.render.fbo.psProcess.cont.init.LightContainer;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.volLight.userState.LightsPostExecutor;
import com.game.render.mask.LightMask;
import net.henryco.struct.Struct;
import net.henryco.struct.container.StructContainer;
import net.henryco.struct.container.exceptions.StructContainerException;
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
	public AbsStdLight getSourceByID(int... id) {
		return layerContainers[id[0]].getSourceByID(id[1]);
	}
	public int[] getIndexID(int index){
		return layerContainers[index].getIndexID();
	}

	private LayerContainer[] initGameObjects(int[] dim, String location, String cfgFile, EscapyUniRender ... uniRenders) {

		System.out.println(cfgFile);
		List<String[]>[] containerList = Struct.in.readStructData(cfgFile);
		StructTree containerTree = StructContainer.tree(containerList, cfgFile);
		System.out.println(containerTree);
		return loadContainer(containerTree.mainNode.getStruct("map"), dim, location, cfgFile.substring(0, cfgFile.lastIndexOf("/") + 1), this, uniRenders);
	}

	private static LayerContainer[] loadContainer(StructNode mapNode, int[] dim, String location, String cfgLoc, MapGameObjects mgo, EscapyUniRender ... uniRenders) {

		List<LayerContainer> containers = new ArrayList<>();
		StructNode container = mapNode.getStruct("container");
		int[] iter = getIntArray(container.getStructChild());
		for (int i : iter) {
			StructNode actualCont = container.getStruct(Integer.toString(i));
			String fboName = "";
			if (actualCont.containsPrimitive("fboName")) fboName = actualCont.getPrimitive("fboName");
			containers.add(fillContainer(new LayerContainer(dim, fboName), actualCont, location, cfgLoc, mgo, dim, uniRenders));
		}
		return containers.toArray(new LayerContainer[containers.size()]);
	}

	private static LayerContainer fillContainer(LayerContainer container, StructNode containerNode, String location,
												String cfgLoc, MapGameObjects mgo, int[] dim, EscapyUniRender ... uniRenders) {

		if (containerNode.containsStruct("lightExecutor")) {
			StructNode execNode = containerNode.getStructSafe("lightExecutor");
			if (execNode != null) {
				container.setLightExecutor(loadExecutor(execNode, dim, container));
			}
		}

		if (containerNode.containsStruct("lights") && containerNode.getStruct("lights").containsStruct("ext")){
			LightContainer tm = loadLights(containerNode.getPath("lights", "ext"), cfgLoc, container, dim);
			container.setLights(tm.lights);
			container.lightID = tm.lightID;
		}
		if (containerNode.containsStruct("mask")) container.setMask(loadMask(containerNode.getStruct("mask"), dim));
		if (containerNode.containsStruct("layer")) {
			StructNode layerNode = containerNode.getStruct("layer");
			int[] iter = getIntArray(layerNode.getStructChild());
			for (int i : iter) container.addSource(loadLayer(layerNode.getStruct(Integer.toString(i)), location, dim, uniRenders));
		}
		return container;
	}

	private static LightContainer loadLights(StructNode lightsNode, String location, LayerContainer container, int[] dim) {

		String loc = location;
		if (lightsNode.containsPrimitive("path") || lightsNode.containsPrimitive("0")) loc = ePrep(lightsNode.getPrimitive("path", "0"));
		loc += lightsNode.getPrimitive("file", "1");
		System.out.println("\nLIGHTS: "+loc);
		return new LightContainer(escapyCamera -> container.forEach(c -> c.renderLightMap(escapyCamera)), loc, new int[]{0, 0, dim[0], dim[1]});
	}

	private static LightMask loadMask(StructNode maskNode, int[] dim) {

		if (maskNode.containsPrimitive("0") && maskNode.getPrimitive("0").equalsIgnoreCase("null")) return null;
		if (maskNode.containsPrimitive("0") && maskNode.getPrimitive("0").equalsIgnoreCase("NULL")) return null;

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
		PositionTranslator trsF = null;
		String textureUrl;
		float[] position = new float[2];
		float scale;
		int type;
		boolean hookUp;
		boolean repeat;
		int[] period = new int[10];
		int zPos;

		try {
			Field typeField = GameObject.type.class.getDeclaredField(actObjectNode.getPrimitive("type", "3"));
			typeField.setAccessible(true);

			for (int i : getIntArray(actObjectNode.getStruct("period", "5").getStructChild()))
				period[i] = Integer.parseInt(actObjectNode.getStruct("period", "5").getPrimitive(Integer.toString(i)));

			scale = getScale(actObjectNode.getPrimitive("scale", "2"));
			textureUrl = getFileName(location, sPrep(actObjectNode.getPrimitive("url", "file", "img", "0")));
			position[0] = Float.parseFloat(actObjectNode.getStruct("position", "1").getPrimitive("0", "x"));
			position[1] = Float.parseFloat(actObjectNode.getStruct("position", "1").getPrimitive("1", "y"));
			type = (int) typeField.get(GameObject.type.class.newInstance());
			hookUp = getHook(actObjectNode.getPrimitive("hook", "4"));
			zPos = Integer.parseInt(actObjectNode.getPrimitive("z", "zPos", "8"));
			repeat = actObjectNode.getBool(false, "repeat", "7");
			trsF = GameObjTranslators.loadPosTranslator(actObjectNode.getStructSafe("shift", "6"));

			gameObject = createGameObject(textureUrl, position, scale, type, hookUp, period, zPos, repeat, dim, trsF);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return gameObject;
	}

	private static GameObject createGameObject(String url, float[] pos, float scale, int type, boolean hookUp, int[] period, int zpos, boolean repeat,
											   int[] dim, PositionTranslator trsF) {

		GameObject gameObject = null;
		PositionCorrector posF = null;
		ZoomCalculator zoomF = null;
		if (!hookUp) posF = (frameW, frameH, w, h) -> new float[]{0, (0 - (h - frameH))};
		if (Float.isNaN(scale)) {
			scale = 1;
			zoomF = (frameW, frameH, w, h, zoom) -> Math.max((frameW / w), (frameH / h));
		}
		if (type == GameObject.type.ANIMATED || type == GameObject.type.INTERACTIVE)
			gameObject = new AnimatedObject(pos[0], pos[1], zpos, url, scale, type, repeat, period, dim[0], dim[1]);
		else if (type == GameObject.type.STATIC) gameObject = new StaticObject(pos[0], pos[1], zpos, url, scale, type, repeat, dim[0], dim[1]);
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
			if (trsF != null) {
				gameObject.setTranlatorFunc(trsF);
				reinit = true;
			}
			if (reinit) gameObject.initializeGraphic();
		}
		return gameObject;
	}


	private static LightsPostExecutor loadExecutor(StructNode executorNode, int[] dim_xywh, LayerContainer container) throws StructContainerException {

		LightsPostExecutor lightExecutor = new LightsPostExecutor(dim_xywh[dim_xywh.length - 2], dim_xywh[dim_xywh.length - 1],
				escapyCamera -> container.forEach(l -> l.renderNormals(escapyCamera)));

		boolean blur = false;
		boolean enable = false;
		try {
			blur = Boolean.parseBoolean(getVaguePrimitive(executorNode, node.blur, "0"));
		} catch (StructContainerException ex) {ex.printStackTrace();}
		lightExecutor.setBlur(blur);
		if (executorNode.containsStruct(node.normals)) {
			try {
				enable = Boolean.parseBoolean(getVaguePrimitive(executorNode.getStruct(node.normals), node.enable, "0"));
			} catch (StructContainerException ex) {ex.printStackTrace();}
			lightExecutor.setNormalMappingOn(enable);
			if (executorNode.getStruct(node.normals).containsStruct(node.shader)) {
				StructNode shaderNode = executorNode.getStruct(node.normals).getStruct(node.shader);
				if (shaderNode.containsPrimitive(node.builtIn)) {
					if (shaderNode.getPrimitive(node.builtIn).equalsIgnoreCase(node.defaults)) {
						if (shaderNode.containsStruct(node.shaderFields)) {
							StructNode fieldNode = shaderNode.getStruct(node.shaderFields);
							if (fieldNode.containsPrimitive(node.spriteSize) || fieldNode.containsPrimitive("0"))
								lightExecutor.setSpriteSize(Float.parseFloat(getVaguePrimitive(fieldNode, node.spriteSize, "0")));
							if (fieldNode.containsStruct(node.intensity)) {
								StructNode intensityNode = fieldNode.getStruct(node.intensity);
								if (intensityNode.containsPrimitive(node.direct) || intensityNode.containsPrimitive("0"))
									lightExecutor.setDirectIntensity(Float.parseFloat(getVaguePrimitive(intensityNode, node.direct, "0")));
								if (intensityNode.containsPrimitive(node.ambient) || intensityNode.containsPrimitive("1"))
									lightExecutor.setAmbientIntesity(Float.parseFloat(getVaguePrimitive(intensityNode, node.ambient, "1")));
								if (intensityNode.containsPrimitive(node.shadow) || intensityNode.containsPrimitive("2"))
									lightExecutor.setShadowIntensity(Float.parseFloat(getVaguePrimitive(intensityNode, node.shadow, "2")));
								if (intensityNode.containsPrimitive(node.height) || intensityNode.containsPrimitive("3"))
									lightExecutor.setHeight(Float.parseFloat(getVaguePrimitive(intensityNode, node.height, "3")));
							}
						}
					}
				}
			}

		}
		return lightExecutor;
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

	private static String sPrep(String name) {
		if (!name.startsWith("/")) name = "/" + name;
		return name;
	}
	private static String ePrep(String name) {
		if (!name.endsWith("/")) return name + "/";
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

	private static String getVaguePrimitive(StructNode node, String ... st) throws StructContainerException {
		for (String s : st) if (node.containsPrimitive(s)) return node.getPrimitive(s);
		throw new StructContainerException("any fields");
	}


	private static final class node {
		private static final String lights = "lights";
		private static final String containers = "containers";
		private static final String type = "type";
		private static final String source = "source";
		private static final String sourceType = "srcType";
		private static final String accuracy = "accuracy";
		private static final String minRadius = "minRadius";
		private static final String maxRadius = "maxRadius";
		private static final String position = "position";
		private static final String color = "color";
		private static final String angle = "angle";
		private static final String scale = "scale";
		private static final String threshold = "threshold";
		private static final String visible = "visible";
		private static final String umbra = "umbra";
		private static final String umbraCoeff = "coeff";
		private static final String umbraRecess = "recess";
		private static final String blur = "blur";
		private static final String shader = "shader";
		private static final String shaderFields = "fields";
		private static final String builtIn = "builtIn";
		private static final String shaderVertex = "vertex";
		private static final String shaderFragment = "fragment";
		private static final String shaderUniforms = "uniforms";
		private static final String shaderTarget = "target";
		private static final String shaderBlend = "blend";
		private static final String file = "file";
		private static final String fileDir = "fileDir";
		private static final String dir = "dir";
		private static final String shaderName = "name";
		private static final String addFunc = "glBlendFuncSeparate";
		private static final String glProgram = "glProgram";
		private static final String direct = "direct";
		private static final String ambient = "ambient";
		private static final String shadow = "shadow";
		private static final String spriteSize = "spriteSize";
		private static final String enable = "enable";
		private static final String normals = "normalMapping";
		private static final String defaults = "DEFAULT";
		private static final String lightExecutor = "executor";
		private static final String intensity = "intensity";
		private static final String height = "height";

	}
}
