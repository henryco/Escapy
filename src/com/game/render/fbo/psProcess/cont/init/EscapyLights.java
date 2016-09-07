package com.game.render.fbo.psProcess.cont.init;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.psProcess.cont.LightContainer;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.fbo.psProcess.program.FBOStdBlendProgramFactory;

import java.util.function.Function;


/**
 * @author Henry on 06/09/16.
 */
public class EscapyLights {

    public final static class lightType extends
            FBOStdBlendProgramFactory {}

    private EscapyFBO stdFBO;
    private EscapyMultiFBO[] multiFBO;
    public LightContainer[] lights;

    {
        lights = new LightContainer[0];
        multiFBO = new EscapyMultiFBO[0];
    }

    public EscapyLights() {
    }
    public EscapyLights(EscapyFBO stdFBO) {
        setStandartFBO(stdFBO);
    }


    public EscapyLights addLightContainer(FBORenderProgram<EscapyMultiFBO> program,
                                          boolean blur) {
        int size = lights.length;
        LightContainer[] temp = new LightContainer[size + 1];
        EscapyMultiFBO[] tempFBO = new EscapyMultiFBO[size + 1];
        System.arraycopy(multiFBO, 0, tempFBO, 0, size);
        System.arraycopy(lights, 0, temp, 0, size);
        tempFBO[size] = new StandartMultiFBO(stdFBO);
        multiFBO = tempFBO;
        temp[size] = new LightContainer(multiFBO[size], program, blur);
        lights = temp;
        return this;
    }

    public EscapyLights apply(Function<LightContainer, Object> fucnt) {
        for (LightContainer target : lights) {
            fucnt.apply(target);
        }   return this;
    }

    public void mergeAndRender(EscapyFBO lightBuff, EscapyGdxCamera cam,
                               int mergeIter, int rendIter){
        for (LightContainer target : lights)
            target.mergeContainedFBO(cam, mergeIter);
        for (LightContainer target : lights)
            target.postRender(lightBuff, cam.getTranslationVec(), rendIter);
    }

    public EscapyLights wipeMultiBuffers() {
        for (EscapyMultiFBO fbo : multiFBO)
            fbo.forceWipeFBO();
        return this;
    }

    public EscapyLights setStandartFBO(EscapyFBO stdFBO){
        this.stdFBO = stdFBO;
        return this;
    }

}
