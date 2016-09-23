package com.game.render.fbo.psProcess.cont.init;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.psProcess.cont.LightContainerBCKP;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.fbo.psProcess.program.FBOStdBlendProgramFactory;

import java.util.function.Function;


/**
 * @author Henry on 06/09/16.
 */
@Deprecated
public class EscapyLightsBCKP {

    public final static class lightType extends
            FBOStdBlendProgramFactory {}

    private EscapyFBO stdFBO;
    private EscapyMultiFBO[] multiFBO;
    public LightContainerBCKP[] lights;

    {
        lights = new LightContainerBCKP[0];
        multiFBO = new EscapyMultiFBO[0];
    }

    public EscapyLightsBCKP() {
    }
    public EscapyLightsBCKP(EscapyFBO stdFBO) {
        setStandartFBO(stdFBO);
    }


    public EscapyLightsBCKP addLightContainer(FBORenderProgram<EscapyMultiFBO> program,
											  boolean blur) {
        int size = lights.length;
        LightContainerBCKP[] temp = new LightContainerBCKP[size + 1];
        EscapyMultiFBO[] tempFBO = new EscapyMultiFBO[size + 1];
        System.arraycopy(multiFBO, 0, tempFBO, 0, size);
        System.arraycopy(lights, 0, temp, 0, size);
        tempFBO[size] = new StandartMultiFBO(stdFBO);
        multiFBO = tempFBO;
        temp[size] = new LightContainerBCKP(multiFBO[size], program, blur);
        lights = temp;
        return this;
    }

    public EscapyLightsBCKP apply(Function<LightContainerBCKP, Object> fucnt) {
        for (LightContainerBCKP target : lights) {
            fucnt.apply(target);
        }   return this;
    }

    public void mergeAndRender(EscapyFBO lightBuff, EscapyGdxCamera cam,
                               int mergeIter, int rendIter){
        for (LightContainerBCKP target : lights)
            target.mergeContainedFBO(cam, mergeIter);
        for (LightContainerBCKP target : lights)
            target.postRender(lightBuff, cam.getTranslationVec(), rendIter);
    }

    public EscapyLightsBCKP wipeMultiBuffers() {
        for (EscapyMultiFBO fbo : multiFBO)
            fbo.forceWipeFBO();
        return this;
    }

    public EscapyLightsBCKP setStandartFBO(EscapyFBO stdFBO){
        this.stdFBO = stdFBO;
        return this;
    }

}
