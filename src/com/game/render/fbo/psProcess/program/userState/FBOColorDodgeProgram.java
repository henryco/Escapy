package com.game.render.fbo.psProcess.program.userState;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.blend.userState.EscapyStdClrDodgeRenderer;

public class FBOColorDodgeProgram extends FBORenderProgram<EscapyMultiFBO>{

	private EscapyStdClrDodgeRenderer clrDodgeRenderer;

	public FBOColorDodgeProgram(EscapyMultiFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.clrDodgeRenderer = new EscapyStdClrDodgeRenderer(super.fbo.getId());
	}
	public FBOColorDodgeProgram() {
		super();
		this.clrDodgeRenderer = new EscapyStdClrDodgeRenderer();
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.clrDodgeRenderer.renderBlended(super.fbo.getMultiTextureRegion(), super.fbo.getTextureRegion(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
	
	}

}
