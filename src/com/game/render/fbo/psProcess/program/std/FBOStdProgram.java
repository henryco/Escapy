package com.game.render.fbo.psProcess.program.std;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.EscapyStdShaderRenderer;

public class FBOStdProgram extends FBORenderProgram<EscapyFBO>{

	private EscapyStdShaderRenderer stdShaderRender;
	
	public FBOStdProgram() {
		super();
		this.stdShaderRender = new EscapyStdShaderRenderer();
	}

	public FBOStdProgram(EscapyFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.stdShaderRender = new EscapyStdShaderRenderer(super.fbo.getId());
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.stdShaderRender.drawTextureRegion(super.fbo.getTextureRegion(), camera.getCamera(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

}
