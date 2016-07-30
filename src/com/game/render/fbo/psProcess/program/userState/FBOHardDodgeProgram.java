package com.game.render.fbo.psProcess.program.userState;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.blend.userState.EscapyStdHardDodgeRenderer;

public class FBOHardDodgeProgram extends FBORenderProgram<EscapyMultiFBO>{

	private EscapyStdHardDodgeRenderer hardDodgeRenderer;
	
	public FBOHardDodgeProgram(EscapyMultiFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.hardDodgeRenderer = new EscapyStdHardDodgeRenderer(super.getFBOTarget().getId());
	}

	public FBOHardDodgeProgram(){
		super();
		this.hardDodgeRenderer = new EscapyStdHardDodgeRenderer();
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.hardDodgeRenderer.renderBlended(super.getFBOTarget().getMultiTextureRegion(), super.getFBOTarget().getTextureRegion(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
	}


}
