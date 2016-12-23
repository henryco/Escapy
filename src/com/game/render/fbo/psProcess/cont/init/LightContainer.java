package com.game.render.fbo.psProcess.cont.init;

import com.badlogic.gdx.graphics.GL20;
import com.game.map.objects.layers.utils.LayerCameraShift;
import com.game.map.objects.objects.utils.PositionTranslator;
import com.game.map.objects.objects.utils.translators.GameObjTranslators;
import com.game.render.EscapyMapRenderer;
import com.game.render.fbo.psProcess.cont.EscapyLightContainer;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsLightProxy;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyShadedLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightSrcFactory;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.render.fbo.psProcess.lights.type.EscapyPeriodicLight;
import com.game.render.program.gl.separate.GLBlendProgram;
import com.game.render.program.shader.blend.EscapyBlendRenderer;
import com.game.render.program.shader.blend.ShaderBlendProgram;
import net.henryco.struct.Struct;
import net.henryco.struct.container.StructContainer;
import net.henryco.struct.container.exceptions.StructContainerException;
import net.henryco.struct.container.tree.StructNode;
import net.henryco.struct.container.tree.StructTree;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Henry on 06/09/16.
 */
public class LightContainer {


	public EscapyLights lights;

	public int[][] lightID;

	public LightContainer(EscapyMapRenderer lightMapRenderer, String url, int[] dim_xywh) {
		this.lights = new EscapyLights();
		this.create(url, lightMapRenderer, dim_xywh);
	}


	private LightContainer create(String url, EscapyMapRenderer lightMapRenderer, int[] dimension) {

		lightID = loadLights(lightMapRenderer, new ArrayList<>(), url, dimension);
		return this;
	}


	private int[][] loadLights(EscapyMapRenderer lightMapRenderer, ArrayList<int[]> IDList, String url, int[] dimension) {

		List<String[]>[] lightList = Struct.in.readStructData(url);
		StructTree lightContainer = StructContainer.tree(lightList, url);
		System.out.println(lightContainer);

		try {
			StructNode lightsNode = lightContainer.mainNode.getStruct(node.lights);
			lights = loadContainer(lights, lightsNode.getStruct(node.containers), dimension);
			IDList = loadFromContainer(IDList, lightsNode.getStruct(node.containers), lightMapRenderer, lights);
		} catch (StructContainerException e) {e.printStackTrace();}


		int[][] forReturn = new int[IDList.size()][2];
		for (int i = 0; i < IDList.size(); i++)
			forReturn[i] = IDList.get(i);
		return forReturn;
	}

	@SuppressWarnings("unchecked")
	private static EscapyLights loadContainer(EscapyLights lights, StructNode containersNode, int[] dimension) throws StructContainerException {

		boolean stop = false;
		int iter = 0;
		while (!stop) {
			if (containersNode.contains(Integer.toString(iter))) {
				try {
					int[] additiveProgram = new int[4];
					boolean blur;
					String shaderName = "";
					EscapyBlendRenderer blendProgram = null;

					if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).contains("0")) {
						String fieldName = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct("0").getPrimitive(node.builtIn);
						Field additiveBlendProgram = GLBlendProgram.class.getDeclaredField(fieldName);
						additiveBlendProgram.setAccessible(true);
						additiveProgram = (int[])additiveBlendProgram.get(GLBlendProgram.class.newInstance());
					} else {
						StructNode addBlendNode = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.glProgram).getStruct(node.addFunc);
						for (int i = 0; i < 4; i++) {
							String name = addBlendNode.getPrimitive(Integer.toString(i));
							Field addProgram = GL20.class.getDeclaredField(name);
							addProgram.setAccessible(true);
							additiveProgram[i] = (int) addProgram.get(GL20.class);
						}
					}

					if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).contains("2"))
						blur = Boolean.parseBoolean(containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getPrimitive("2"));
					else if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).contains(node.blur))
						blur = Boolean.parseBoolean(containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getPrimitive(node.blur));


					if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).contains("1")) {
						blendProgram = ShaderBlendProgram.blendProgram(getStringField(containersNode.getStruct(Integer.toString(iter)).getStruct(node.type), "1"));
					} else {
						if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).containsPrimitive(node.builtIn))
							blendProgram = ShaderBlendProgram.blendProgram(getStringField(containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader), node.builtIn));
						else if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).containsStruct(node.shader)) {
							if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).contains(node.file) ||
									containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).contains(node.fileDir)) {
								String vertex = "";
								String fragment = "";
								StructNode fileNode;

								if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).contains(node.file)) {
									fileNode = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).getStruct(node.file);
									if (fileNode.contains("0")) vertex = fileNode.getPrimitive("0");
									else if (fileNode.contains(node.shaderVertex)) vertex = fileNode.getPrimitive(node.shaderVertex);
									if (fileNode.contains("1")) fragment = fileNode.getPrimitive("1");
									else if (fileNode.contains(node.shaderFragment)) fragment = fileNode.getPrimitive(node.shaderFragment);
								}
								if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).contains(node.fileDir)) {
									fileNode = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).getStruct(node.fileDir);
									String dirname = "";
									String fileName = "";

									if (fileNode.contains("0")) dirname = fileNode.getPrimitive("0");
									else if (fileNode.contains(node.dir)) dirname = fileNode.getPrimitive(node.dir);
									if (fileNode.contains("1")) fileName = fileNode.getPrimitive("1");
									else if (fileNode.contains(node.shaderName)) fileName = fileNode.getPrimitive(node.shaderName);

									if (!dirname.endsWith("/")) dirname = dirname + "/";

									vertex = dirname + fileName + ".vert";
									fragment = dirname + fileName + ".frag";
								}

								if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).contains(node.shaderUniforms)) {
									StructNode uniformNode = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).getStruct(node.shaderUniforms);
									String target = "";
									String blend = "";
									if (uniformNode.contains("0")) target = uniformNode.getPrimitive("0");
									else if (uniformNode.contains(node.shaderTarget)) target = uniformNode.getPrimitive(node.shaderTarget);
									if (uniformNode.contains("1")) blend = uniformNode.getPrimitive("1");
									else if (uniformNode.contains(node.shaderBlend)) blend = uniformNode.getPrimitive(node.shaderBlend);

									blendProgram = ShaderBlendProgram.blendProgram(vertex, fragment, target, blend);

								} else blendProgram = ShaderBlendProgram.blendProgram(vertex, fragment);
							}
						}
					}
					EscapyLightContainer newContainer = new EscapyLightContainer(additiveProgram, blendProgram, dimension[dimension.length - 2], dimension[dimension.length - 1]);
					StructNode shiftNode = containersNode.getStruct(Integer.toString(iter)).getStructSafe("stepShift");
					if (shiftNode != null){
						float xShift = shiftNode.getFloat(1, "0", "x", "s");
						float yShift = shiftNode.getFloat(1, "1", "y", "t");
						if (xShift != 1 && yShift != 1) {
							final float[] frameVec = new float[]{0.5f * dimension[dimension.length - 2], 0.5f * dimension[dimension.length - 1]};
							LayerCameraShift shift = (camera, pos) -> {
								camera.setCameraPosition((xShift * (pos[0] - frameVec[0])) + frameVec[0], (yShift * (pos[1] - frameVec[1])) + frameVec[1]);
								return camera;
							};
							newContainer.setCameraShifter(shift);
						}
					}
					lights.addLightContainer(newContainer);

				} catch (Exception e) {
					e.printStackTrace();
					stop = true;
				}
			} else stop = true;
			iter += 1;
		}	return lights;
	}

	private static ArrayList<int[]> loadFromContainer(ArrayList<int[]> IDList, StructNode containersNode,
													  EscapyMapRenderer lightMapRenderer, EscapyLights lights) throws StructContainerException {
		boolean stop = false;
		int iter = 0;
		while (!stop) {
			if (containersNode.contains(Integer.toString(iter))) {
				StructNode sourceNode = containersNode.getStruct(Integer.toString(iter)).getStruct(node.source);
				boolean stop2 = false;
				int iter2 = 0;
				while (!stop2) {
					if (sourceNode.contains(Integer.toString(iter2))) {
						StructNode source = sourceNode.getStruct(Integer.toString(iter2));
						for (StructNode snode : source.getStructArray()) {
							AbsStdLight absStdLight = null;
							try {
								absStdLight = snode.instanceAndInvokeObject(null, true, true);
							} catch (Exception e) {
								e.fillInStackTrace();
							}
							if (absStdLight != null) {
								if (absStdLight instanceof EscapyShadedLight) {
									((EscapyShadedLight) absStdLight).setLightMapRenderer(lightMapRenderer);
								}
								IDList.add(new int[]{iter, lights.lightContainers[iter].addSource(absStdLight)});
							}
						}
					} else stop2 = true;
					iter2 += 1;
				}
			} else stop = true;
			iter += 1;
		}
		return IDList;
	}

	private static String getStringField(StructNode containersNode, String val) throws NoSuchFieldException, IllegalAccessException, InstantiationException {

		String fieldName2 = containersNode.getPrimitive(val);
		Field blendProgramName = ShaderBlendProgram.program.class.getDeclaredField(fieldName2);
		blendProgramName.setAccessible(true);
		return (String) blendProgramName.get(ShaderBlendProgram.program.class.newInstance());
	}

	private static String getVaguePrimitive(StructNode node, String ... st) throws StructContainerException {
		for (String s : st) if (node.containsPrimitive(s)) return node.getPrimitive(s);
		throw new StructContainerException("any fields");
	}


	public AbsStdLight getSourceByID(int[] id) {
		return lights.lightContainers[id[0]].getSourceByID(id[1]);
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
		private static final String luminance = "luminance";
		private static final String shift = "shift";

	}

}
