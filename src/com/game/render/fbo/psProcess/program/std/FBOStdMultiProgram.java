package com.game.render.fbo.psProcess.program.std;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.EscapyStdShaderRenderer;

public class FBOStdMultiProgram extends FBORenderProgram <EscapyMultiFBO>{

	private EscapyStdShaderRenderer stdRenderer;

	public FBOStdMultiProgram() {
		super();
		this.stdRenderer = new EscapyStdShaderRenderer();
	}
	public FBOStdMultiProgram(EscapyMultiFBO target) {
		super(target);
		this.stdRenderer = new EscapyStdShaderRenderer(super.getFBOTarget().getId());
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.stdRenderer.drawTextureRegion(super.getFBOTarget().getTextureRegion(), camera.getCamera(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.stdRenderer.drawTextureRegion(super.getFBOTarget().getMultiTextureRegion(),camera.getCamera(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
      );
	}

}
