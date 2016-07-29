package com.game.render.fbo.psProcess.program.userState;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.blend.userState.EscapyStdScreenDodgeRenderer;

public class FBOScreenDodgeProgram extends FBORenderProgram<EscapyMultiFBO>{

	private EscapyStdScreenDodgeRenderer scrDodgeRenderer;

	public FBOScreenDodgeProgram(EscapyMultiFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.scrDodgeRenderer = new EscapyStdScreenDodgeRenderer(super.fbo.getId());
	}

	public FBOScreenDodgeProgram() {
		super();
		this.scrDodgeRenderer = new EscapyStdScreenDodgeRenderer();
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.scrDodgeRenderer.renderBlended(super.fbo.getMultiTextureRegion(), super.fbo.getTextureRegion(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
	}

}
