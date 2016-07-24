package com.game.render.fbo.psProcess.program.std;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.EscapyStdShaderRenderer;

public class FBOStdMultiProgram extends FBORenderProgram <EscapyMultiFBO>{

	private EscapyStdShaderRenderer stdRenderer;
		
	{
		this.stdRenderer = new EscapyStdShaderRenderer();
	}
	
	public FBOStdMultiProgram() {
		super();
	}
	public FBOStdMultiProgram(EscapyMultiFBO target) {
		super(target);
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.stdRenderer.drawTextureRegion(super.fbo.getTextureRegion(), camera.getCamera(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.stdRenderer.drawTextureRegion(super.fbo.getMultiTextureRegion(),camera.getCamera(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	}

}
