package com.game.render.fbo.psProcess.cont.init;

import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.cont.LightContainer;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyShadedLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightSrcFactory;

import java.util.ArrayList;


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

        IDList.add(new int[]{0,this.lights.lights[0].addSource(new EscapyShadedLight(
                lightMapFBO, 4, EscapyLightSrcFactory.RND_1024()).setMaxRadius(1.5f).
                setPosition(400, 450).setColor(0, 0, 0).
                setAngle(0.125f).setVisible(true).setScale(2f).setThreshold(0.7f)
        )});

        IDList.add(new int[]{1,this.lights.lights[1].addSource(new EscapyStdLight(lightMapFBO,
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



        lights.addLightContainer(LightContainer.light.screenDodge(), true);
        lights.addLightContainer(LightContainer.light.strongSoftLight(), false);


        IDList.add(new int[]{0,this.lights.lights[0].addSource(new EscapyShadedLight(
                lightMapContainer, 4, EscapyLightSrcFactory.RND_1024()).setMaxRadius(1.4f).
                setPosition(400, 450).setColor(0, 0, 0).
                setAngle(0.125f).setVisible(true).setScale(1.5f).setThreshold(0.7f)
        )});


        IDList.add(new int[]{1,this.lights.lights[1].addSource(new EscapyStdLight(transitFBO,
                EscapyLightSrcFactory.RND_512()).setPosition(350, 500).
                setColor(45, 40, 250).setVisible(true).setScale(2.5f)
        )});

        IDList.add(new int[]{1,this.lights.lights[1].addSource(new EscapyStdLight(transitFBO,
                EscapyLightSrcFactory.RND_512()).setPosition(0, 420).
                setColor(205, 107, 107).setVisible(true).setScale(2.5f)
        )});


        int[][] forReturn = new int[IDList.size()][2];
        for (int i = 0; i < IDList.size(); i++)
            forReturn[i] = IDList.get(i);
        return forReturn;
    }


    public AbsStdLight getSourceByID(int[] id) {
        return lights.lights[id[0]].getSourceByID(id[1]);
    }

}
