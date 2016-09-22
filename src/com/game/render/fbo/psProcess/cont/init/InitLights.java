package com.game.render.fbo.psProcess.cont.init;

import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.cont.LightContainer;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Henry on 06/09/16.
 */
public class InitLights {

	protected static final class node {
		private static final String lights = "lights";
		private static final String containers = "containers";
		private static final String type = "type";
		private static final String source = "source";
		private static final String sourceType = "srcType";
	}

	public static final String cfgUrl = "data/config/lights";
	public static final String flatLight = "/flat.struct";


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

		List<String[]>[] lightList = Struct.printDataFile(Struct.in.readStructData(cfgUrl + flatLight));
		StructTree lightContainer = StructContainer.tree(lightList);
		System.out.println(lightContainer);

		try {
			StructNode lightsNode = lightContainer.mainNode.getStruct(node.lights);
			StructNode containersNode = lightsNode.getStruct(node.containers);

			lights = loadContainer(lights, lightContainer, containersNode);




		} catch (StructContainerException ignored) {}



		IDList.add(new int[]{0, this.lights.lights[0].addSource(new EscapyShadedLight(
				lightMapContainer, 4, EscapyLightSrcFactory.RND_1024()).setMaxRadius(1.4f).
				setPosition(400, 450).setColor(0, 0, 0).
				setAngle(0.125f).setVisible(true).setScale(1.5f).setThreshold(0.7f)
		)});


		IDList.add(new int[]{1, this.lights.lights[1].addSource(new EscapyStdLight(transitFBO,
				EscapyLightSrcFactory.RND_512()).setPosition(350, 500).
				setColor(45, 40, 250).setVisible(true).setScale(2.5f)
		)});

		IDList.add(new int[]{1, this.lights.lights[1].addSource(new EscapyStdLight(transitFBO,
				EscapyLightSrcFactory.RND_512()).setPosition(0, 420).
				setColor(205, 107, 107).setVisible(true).setScale(2.5f)
		)});


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

	private static void loadLight(StructNode containersNode) throws StructContainerException {
		boolean stop = false;
		int iter = 0;
		while (!stop) {
			if (containersNode.contains(Integer.toString(iter))) {
				StructNode sourceNode = containersNode.getStruct(node.source);
				boolean stop2 = false;
				int iter2 = 0;
				while (!stop2) {
					if (sourceNode.contains(Integer.toString(iter2))) {
						StructNode source = sourceNode.getStruct(Integer.toString(iter2));
						AbsStdLight absStdLight;
						EscapyLightType escapyLightType;
						String lightType = "";
						if (source.contains("EscapyShadedLight")) lightType = "EscapyShadedLight";
						else if (source.contains("EscapyStdLight")) lightType = "EscapyStdLight";

						try {
							String methodName = source.getStruct(lightType).getPrimitive(node.sourceType);
							Method sourceType = EscapyLightSrcFactory.class.getDeclaredMethod(methodName);
							escapyLightType = (EscapyLightType) sourceType.invoke(EscapyLightSrcFactory.class.newInstance());
						} catch (Exception e) {
							e.printStackTrace();
							stop2 = true;
							stop = true;
						}



					} else stop2 = true;
					iter2 += 1;
				}
			} else stop = true;
			iter += 1;
		}
	}

	public AbsStdLight getSourceByID(int[] id) {
		return lights.lights[id[0]].getSourceByID(id[1]);
	}

}
