package com.game.render.fbo.psProcess.program.userState;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.blend.userState.EscapyStdSoftLightRenderer;

public class FBOSoftLightProgram extends FBORenderProgram<EscapyMultiFBO>{

	private EscapyStdSoftLightRenderer softLightRenderer;

	public FBOSoftLightProgram(EscapyMultiFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.softLightRenderer = new EscapyStdSoftLightRenderer(super.getFBOTarget().getId());
	}

	public FBOSoftLightProgram() {
		super();
		this.softLightRenderer = new EscapyStdSoftLightRenderer();
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.softLightRenderer.renderBlended(super.getFBOTarget().getMultiTextureRegion(), super.getFBOTarget().getTextureRegion(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
	}

}
