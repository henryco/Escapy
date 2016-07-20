package com.game.render.fbo.psProcess.program.userState;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.blend.userState.EscapyStdLinearDodgeRenderer;

public class FBOLinearDodgeProgram extends FBORenderProgram<EscapyMultiFBO>{

	private EscapyStdLinearDodgeRenderer linearDodgeRenderer;
	
	public FBOLinearDodgeProgram(EscapyMultiFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.linearDodgeRenderer = new EscapyStdLinearDodgeRenderer();
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.linearDodgeRenderer.renderBlended(super.fbo.getMultiTextureRegion(), super.fbo.getTextureRegion(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
	
	}

	

}
