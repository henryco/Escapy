package com.game.render.fbo.psProcess.cont.init;

import com.badlogic.gdx.graphics.GL20;
import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.fbo.psProcess.cont.EscapyLightContainer;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsLightProxy;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyShadedLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightSrcFactory;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.render.fbo.psProcess.lights.volLight.userState.LightsPostExecutor;
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
public class InitLights {


	public EscapyLights lights;
	public LightsPostExecutor postExecutor;

	public int[][] lightID;

	public InitLights() {
		this.lights = new EscapyLights();
	}
	public InitLights(ExtraRenderContainer lightMapContainer, String url, int[] dim_xywh) {
		this.lights = new EscapyLights();
		this.postExecutor = new LightsPostExecutor(dim_xywh[dim_xywh.length - 2], dim_xywh[dim_xywh.length - 1]);
		this.create(url, lightMapContainer, dim_xywh);
	}


	public InitLights create(String url, ExtraRenderContainer lightMapContainer, int[] dimension) {

		lightID = loadLights(lightMapContainer, new ArrayList<>(), url, dimension);
		return this;
	}


	private int[][] loadLights(ExtraRenderContainer lightMapContainer, ArrayList<int[]> IDList, String url, int[] dimension) {

		List<String[]>[] lightList = Struct.printDataFile(Struct.in.readStructData(url));
		StructTree lightContainer = StructContainer.tree(lightList);
		System.out.println(lightContainer);

		try {
			StructNode lightsNode = lightContainer.mainNode.getStruct(node.lights);
			postExecutor = loadExecutor(postExecutor, lightsNode.getStruct(node.lightExecutor));
			lights = loadContainer(lights, lightsNode.getStruct(node.containers), dimension);
			IDList = loadFromContainer(IDList, lightsNode.getStruct(node.containers), lightMapContainer, lights);
		} catch (StructContainerException e) {e.printStackTrace();}


		int[][] forReturn = new int[IDList.size()][2];
		for (int i = 0; i < IDList.size(); i++)
			forReturn[i] = IDList.get(i);
		return forReturn;
	}

	private static LightsPostExecutor loadExecutor(LightsPostExecutor lightExecutor, StructNode executorNode) throws StructContainerException {

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
				if (shaderNode.containsPrimitive(node.shaderBuiltIn)) {
					if (shaderNode.getPrimitive(node.shaderBuiltIn).equalsIgnoreCase(node.defaults)) {
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
								if (intensityNode.containsPrimitive(node.luminance) || intensityNode.containsPrimitive("3"))
									lightExecutor.setLumimance(Float.parseFloat(getVaguePrimitive(intensityNode, node.luminance, "3")));
							}
						}
					}
				}
			}

		}
		return lightExecutor;
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
						String fieldName = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getPrimitive("0");
						Field additiveBlendProgram = GLBlendProgram.class.getDeclaredField(fieldName);
						additiveBlendProgram.setAccessible(true);
						additiveProgram = (int[])additiveBlendProgram.get(GLBlendProgram.class.newInstance());
					} else {
						StructNode addBlendNode = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.addFunc);
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
						if (containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader).containsPrimitive(node.shaderBuiltIn))
							blendProgram = ShaderBlendProgram.blendProgram(getStringField(containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getStruct(node.shader), node.shaderBuiltIn));
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
					lights.addLightContainer(new EscapyLightContainer(additiveProgram, blendProgram, dimension[dimension.length - 2], dimension[dimension.length - 1]));

				} catch (Exception e) {
					e.printStackTrace();
					stop = true;
				}
			} else stop = true;
			iter += 1;
		}	return lights;
	}

	private static ArrayList<int[]> loadFromContainer(ArrayList<int[]> IDList, StructNode containersNode,
													  ExtraRenderContainer lightMapContainer, EscapyLights lights) throws StructContainerException {
		boolean stop = false;
		int iter = 0;
		while (!stop) {
			if (containersNode.contains(Integer.toString(iter))) {
				StructNode sourceNode = containersNode.getStruct(Integer.toString(iter)).getStruct(node.source);
				boolean stop2 = false;
				int iter2 = 0;
				while (!stop2) {
					if (sourceNode.contains(Integer.toString(iter2))) {

						AbsLightProxy proxy = new AbsLightProxy();

						StructNode source = sourceNode.getStruct(Integer.toString(iter2));
						AbsStdLight absStdLight;
						EscapyLightType escapyLightType;
						String lightType = "";

						if (source.contains("EscapyShadedLight")) lightType = "EscapyShadedLight";
						else if (source.contains("EscapyStdLight")) lightType = "EscapyStdLight";

						StructNode lightNode = source.getStruct(lightType);

						try {
							String methodName = lightNode.getPrimitive(node.sourceType);
							Method sourceType = EscapyLightSrcFactory.class.getDeclaredMethod(methodName);
							escapyLightType = (EscapyLightType) sourceType.invoke(EscapyLightSrcFactory.class.newInstance());
						} catch (Exception e) {
							e.printStackTrace();
							stop = true;
							break;
						}

						if (lightNode.contains(node.angle)) {
							proxy.angle = Float.parseFloat(getVaguePrimitive(lightNode.getStruct(node.angle), "0", "angle"));
							try {
								proxy.angleShift = Float.parseFloat(getVaguePrimitive(lightNode.getStruct(node.angle), "1", "shift"));
								proxy.angleCorr = Float.parseFloat(getVaguePrimitive(lightNode.getStruct(node.angle), "2", "corr"));
							} catch (StructContainerException ignored){}
						}
						if (lightNode.contains(node.position)) {
							try {
								proxy.position = new float[] {

										Float.parseFloat(getVaguePrimitive(lightNode.getStruct(node.position), "0", "x")),
										Float.parseFloat(getVaguePrimitive(lightNode.getStruct(node.position), "1", "y"))
								};
							} catch (StructContainerException e){
								proxy.position = null;
							}
						}
						if (lightNode.contains(node.color)) {
							try {
								proxy.color = new int[] {
										Integer.parseInt(getVaguePrimitive(lightNode.getStruct(node.color), "0", "r")),
										Integer.parseInt(getVaguePrimitive(lightNode.getStruct(node.color), "1", "g")),
										Integer.parseInt(getVaguePrimitive(lightNode.getStruct(node.color), "2", "b"))
								};
							} catch (StructContainerException e){
								proxy.color = null;
							}
						}
						if (lightNode.contains(node.umbra)) {
							proxy.umbraCoeff = Float.parseFloat(getVaguePrimitive(lightNode.getStruct(node.umbra), "0", node.umbraCoeff));
							proxy.umbraRecess = Float.parseFloat(getVaguePrimitive(lightNode.getStruct(node.umbra), "1", node.umbraRecess));
						}

						if (lightNode.contains(node.accuracy))	proxy.accuracy = Integer.parseInt(lightNode.getPrimitive(node.accuracy));
						if (lightNode.contains(node.minRadius))	proxy.minRadius = Float.parseFloat(lightNode.getPrimitive(node.minRadius));
						if (lightNode.contains(node.maxRadius))	proxy.maxRadius = Float.parseFloat(lightNode.getPrimitive(node.maxRadius));
						if (lightNode.contains(node.scale))		proxy.scale = Float.parseFloat(lightNode.getPrimitive(node.scale));
						if (lightNode.contains(node.threshold))	proxy.threshold = Float.parseFloat(lightNode.getPrimitive(node.threshold));
						if (lightNode.contains(node.visible)) 	proxy.visible = Boolean.parseBoolean(lightNode.getPrimitive(node.visible));

						if (lightType.equalsIgnoreCase("EscapyShadedLight")) {
							if (proxy.accuracy != Integer.MAX_VALUE) absStdLight = new EscapyShadedLight(lightMapContainer, proxy.accuracy, escapyLightType);
							else absStdLight = new EscapyShadedLight(lightMapContainer, escapyLightType);
						}
						else absStdLight = new EscapyStdLight(escapyLightType);

						if (!Float.isNaN(proxy.maxRadius)) 		absStdLight.setMaxRadius(proxy.maxRadius);
						if (!Float.isNaN(proxy.minRadius)) 		absStdLight.setMinRadius(proxy.minRadius);
						if (!Float.isNaN(proxy.threshold)) 		absStdLight.setThreshold(proxy.threshold);
						if (!Float.isNaN(proxy.scale)) 			absStdLight.setScale(proxy.scale);
						if (!Float.isNaN(proxy.umbraRecess)) 	absStdLight.setUmbraCoeff(proxy.umbraRecess);
						if (!Float.isNaN(proxy.umbraCoeff)) 	absStdLight.setUmbraCoeff(proxy.umbraCoeff);
						if (proxy.color != null) 				absStdLight.setColor(proxy.color[0], proxy.color[1], proxy.color[2]);
						if (proxy.position != null) 			absStdLight.setPosition(proxy.position);

						if (!Float.isNaN(proxy.angle)) {
							if (!Float.isNaN(proxy.angleShift) && !Float.isNaN(proxy.angleCorr))
									absStdLight.setAngle(proxy.angle, proxy.angleShift, proxy.angleCorr);
							else 	absStdLight.setAngle(proxy.angle);
						}
						absStdLight.setVisible(proxy.visible);
						IDList.add(new int[]{iter, lights.lightContainers[iter].addSource(absStdLight)});

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
		private static final String shaderBuiltIn = "builtIn";
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

	}

}
