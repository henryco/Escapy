package com.game.render.fbo.psProcess.cont.init;

import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.cont.LightContainer;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsLightProxy;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyShadedLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightSrcFactory;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.fbo.psProcess.program.FBOStdBlendProgramFactory;
import net.henryco.struct.Struct;
import net.henryco.struct.container.StructContainer;
import net.henryco.struct.container.exceptions.StructContainerException;
import net.henryco.struct.container.tree.StructNode;
import net.henryco.struct.container.tree.StructTree;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Henry on 06/09/16.
 */
public class InitLights {


	public EscapyLights lights;
	public int[][] lightID;
	public float ambientInt, ligthInt;

	public InitLights(EscapyFBO stdFBO) {
		this.lights = new EscapyLights(stdFBO);
	}

	public InitLights(EscapyFBO stdFBO, EscapyFBO lightMapFBO, String url) {
		this.lights = new EscapyLights(stdFBO);
		this.create(url, lightMapFBO);
	}

	public InitLights(EscapyFBO stdFBO, ExtraRenderContainer lightMapContainer, String url) {
		this.lights = new EscapyLights(stdFBO);
		this.create(url, lightMapContainer);
	}


	public InitLights create(String url, EscapyFBO lightMapFBO) {

		lightID = loadLights(lightMapFBO, new ArrayList<>(), url);
		return this;
	}

	public InitLights create(String url, ExtraRenderContainer lightMapContainer) {

		lightID = loadLights(lightMapContainer, new ArrayList<>(), url);
		return this;
	}


	private int[][] loadLights(EscapyFBO lightMapFBO, ArrayList<int[]> IDList, String url) {

		ligthInt = 0.2f;
		ambientInt = 0.75f;

		lights.addLightContainer(LightContainer.light.screenDodge(), true);
		lights.addLightContainer(LightContainer.light.strongSoftLight(), false);

		IDList.add(new int[]{0, this.lights.lights[0].addSource(new EscapyShadedLight(
				lightMapFBO, 4, EscapyLightSrcFactory.RND_1024()).setMaxRadius(1.5f).
				setPosition(400, 450).setColor(0, 0, 0).
				setAngle(0.125f).setVisible(true).setScale(2f).setThreshold(0.7f)
		)});

		IDList.add(new int[]{1, this.lights.lights[1].addSource(new EscapyStdLight(lightMapFBO,
				EscapyLightSrcFactory.RND_512()).setPosition(0, 420).
				setColor(205, 107, 107).setVisible(true).setScale(3f)
		)});

		int[][] forReturn = new int[IDList.size()][2];
		for (int i = 0; i < IDList.size(); i++)
			forReturn[i] = IDList.get(i);
		return forReturn;
	}


	private int[][] loadLights(ExtraRenderContainer lightMapContainer, ArrayList<int[]> IDList, String url) {

		EscapyFBO transitFBO = new StandartFBO().forceClearFBO();

		ligthInt = 0.2f;
		ambientInt = 0.75f;

		List<String[]>[] lightList = Struct.printDataFile(Struct.in.readStructData(url));
		StructTree lightContainer = StructContainer.tree(lightList);
		System.out.println(lightContainer);

		try {
			StructNode lightsNode = lightContainer.mainNode.getStruct(node.lights);
			StructNode containersNode = lightsNode.getStruct(node.containers);
			lights = loadContainer(lights, lightContainer, containersNode);
			IDList = loadFromContainer(IDList, containersNode, lightMapContainer, lights);
		} catch (StructContainerException e) {e.printStackTrace();}


		int[][] forReturn = new int[IDList.size()][2];
		for (int i = 0; i < IDList.size(); i++)
			forReturn[i] = IDList.get(i);
		return forReturn;
	}


	@SuppressWarnings("unchecked")
	private static EscapyLights loadContainer(EscapyLights lights, StructTree lightContainer, StructNode containersNode) throws StructContainerException {

		boolean stop = false;
		int iter = 0;
		while (!stop) {
			if (containersNode.contains(Integer.toString(iter))) {
				try {
					String methodName = containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getPrimitive("0");
					boolean blur = Boolean.parseBoolean(containersNode.getStruct(Integer.toString(iter)).getStruct(node.type).getPrimitive("1"));
					Method blendProgram = FBOStdBlendProgramFactory.class.getDeclaredMethod(methodName);
					lights.addLightContainer((FBORenderProgram) blendProgram.invoke(FBOStdBlendProgramFactory.class.newInstance()), blur);
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
		EscapyFBO transitFBO = new StandartFBO().forceClearFBO();

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
							proxy.angle = Float.parseFloat(lightNode.getStruct(node.angle).getPrimitive("0"));
							try {
								proxy.angleShift = Float.parseFloat(lightNode.getStruct(node.angle).getPrimitive("1"));
								proxy.angleCorr = Float.parseFloat(lightNode.getStruct(node.angle).getPrimitive("2"));
							} catch (StructContainerException ignored){}
						}
						if (lightNode.contains(node.position)) {
							try {
								proxy.position = new float[] {
										Float.parseFloat(lightNode.getStruct(node.position).getPrimitive("0")),
										Float.parseFloat(lightNode.getStruct(node.position).getPrimitive("1"))
								};
							} catch (StructContainerException e){
								proxy.position = null;
							}
						}
						if (lightNode.contains(node.color)) {
							try {
								proxy.color = new int[] {
										Integer.parseInt(lightNode.getStruct(node.color).getPrimitive("0")),
										Integer.parseInt(lightNode.getStruct(node.color).getPrimitive("1")),
										Integer.parseInt(lightNode.getStruct(node.color).getPrimitive("2"))
								};
							} catch (StructContainerException e){
								proxy.color = null;
							}
						}
						if (lightNode.contains(node.umbra)) {
							if (lightNode.getStruct(node.umbra).contains(node.umbraCoeff))
								proxy.umbraCoeff = Float.parseFloat(lightNode.getStruct(node.umbra).getPrimitive(node.umbraCoeff));
							if (lightNode.getStruct(node.umbra).contains(node.umbraRecess))
								proxy.umbraRecess = Float.parseFloat(lightNode.getStruct(node.umbra).getPrimitive(node.umbraRecess));
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
						else absStdLight = new EscapyStdLight(transitFBO, escapyLightType);

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

						IDList.add(new int[]{iter, lights.lights[iter].addSource(absStdLight)});

					} else stop2 = true;
					iter2 += 1;
				}
			} else stop = true;
			iter += 1;
		}
		return IDList;
	}

	public AbsStdLight getSourceByID(int[] id) {
		return lights.lights[id[0]].getSourceByID(id[1]);
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
	}

}
