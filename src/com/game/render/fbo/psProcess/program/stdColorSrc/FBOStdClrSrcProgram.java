package com.game.render.fbo.psProcess.program.stdColorSrc;

import com.badlogic.gdx.graphics.Color;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.lightSrc.userState.EscapyStdLightSrcRenderer;
import com.game.utils.translationVec.TransVec;

/**
 * @author Henry on 07/09/16.
 */
public class FBOStdClrSrcProgram extends FBORenderProgram<EscapyMultiFBO> {

    private EscapyStdLightSrcRenderer srcRenderer;
    private Color color;

    private TransVec fSize;
    private TransVec radius;
    private TransVec umbra;
    private TransVec angles;

    private float coeff;
    private float correct;

    public FBOStdClrSrcProgram() {
        super();
        srcRenderer = new EscapyStdLightSrcRenderer();
    }

    public FBOStdClrSrcProgram(EscapyMultiFBO fboProgramTarget) {
        super(fboProgramTarget);
        srcRenderer = new EscapyStdLightSrcRenderer(fboProgramTarget.getId());
    }

    @Override
    public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
        this.srcRenderer.renderLightSrc(
                getFBOTarget().getTextureRegion(),
                getFBOTarget().getMultiTextureRegion(), camera.getCamera(),
                color, 0, 0, getFBOTarget().getTextureRegion().getRegionWidth(),
                getFBOTarget().getTextureRegion().getRegionHeight(), angles,
                fSize, coeff, correct, radius, umbra
        );
    }


    public FBOStdClrSrcProgram setColor(Color color) {
        this.color = color;
        return this;
    }
    public FBOStdClrSrcProgram setfSize(TransVec fSize) {
        this.fSize = fSize;
        return this;
    }
    public FBOStdClrSrcProgram setRadius(TransVec radius) {
        this.radius = radius;
        return this;
    }
    public FBOStdClrSrcProgram setUmbra(TransVec umbra) {
        this.umbra = umbra;
        return this;
    }
    public FBOStdClrSrcProgram setAngles(TransVec angles) {
        this.angles = angles;
        return this;
    }
    public FBOStdClrSrcProgram setCoeff(float coeff) {
        this.coeff = coeff;
        return this;
    }
    public FBOStdClrSrcProgram setCorrect(float correct) {
        this.correct = correct;
        return this;
    }

}
