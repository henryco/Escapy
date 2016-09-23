package com.game.render.fbo.psProcess.program.stdBlend;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.program.shader.blend.EscapyBlendRenderer;


public class FBOStdBlendProgram extends FBORenderProgram<EscapyMultiFBO>{

	private EscapyBlendRenderer settedBlender;

	public FBOStdBlendProgram(EscapyMultiFBO fboProgramTarget, EscapyBlendRenderer blender) {
		super(fboProgramTarget);
		this.setBlendRenderer(blender);
	}
	public FBOStdBlendProgram(EscapyBlendRenderer blender) {
		super();
		this.setBlendRenderer(blender);
	}
	
	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.settedBlender.renderBlended(super.getFBOTarget().getMultiTextureRegion(), super.getFBOTarget().getTextureRegion(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
		
	}
	public EscapyBlendRenderer getBlendRenderer() {
		return settedBlender;
	}
	public FBOStdBlendProgram setBlendRenderer(EscapyBlendRenderer settedBlender) {
		this.settedBlender = settedBlender;
		return this;
	}

	

}
