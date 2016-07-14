package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.EscapyStdShaderRenderer;

public class StandartMultiFBO extends EscapyMultiFBO {

	public StandartMultiFBO() {
		super();
	}

	public StandartMultiFBO(FrameBuffer multiFrameBuffer) {
		super(multiFrameBuffer);
	}

	public StandartMultiFBO(FrameBuffer[] multiFrameBuffer) {
		super(multiFrameBuffer);
	}

	@Override
	protected FBORenderProgram<?> initRenderProgram() {
		
		return new FBORenderProgram<StandartMultiFBO>(this) {
			
			private EscapyStdShaderRenderer stdRenderer
				= new EscapyStdShaderRenderer();
			
			@Override
			public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
				this.stdRenderer.drawTextureRegion(super.fbo.MAINREGION, camera.getCamera(),
						0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				this.stdRenderer.drawTextureRegion(super.fbo.mutliTexureRegion,camera.getCamera(),
						0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				
			}
		};
	}

}
