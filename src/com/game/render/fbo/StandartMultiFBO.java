package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.EscapyStdShaderRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class StandartMultiFBO.
 */
public class StandartMultiFBO extends EscapyMultiFBO {

	/**
	 * Instantiates a new standart multi FBO.
	 */
	public StandartMultiFBO() {
		super();
	}

	/**
	 * Instantiates a new standart multi FBO.
	 *
	 * @param multiFrameBuffer
	 *            the multi frame buffer
	 */
	public StandartMultiFBO(FrameBuffer multiFrameBuffer) {
		super(multiFrameBuffer);
	}
	public StandartMultiFBO(EscapyFBO fbo) {
		super(fbo.getFrameBuffer());
	}
	/**
	 * Instantiates a new standart multi FBO.
	 *
	 * @param multiFrameBuffer
	 *            the multi frame buffer
	 */
	public StandartMultiFBO(FrameBuffer[] multiFrameBuffer) {
		super(multiFrameBuffer);
	}
	public StandartMultiFBO(EscapyFBO[] fbo) {
		super(getBuffer(fbo));
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#initRenderProgram()
	 */
	@Override
	protected FBORenderProgram<?> initRenderProgram() {
		
		return new FBORenderProgram<StandartMultiFBO>(this) {
			
			private EscapyStdShaderRenderer stdRenderer
				= new EscapyStdShaderRenderer(super.getFBOTarget().getId());
			
			@Override
			public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
				this.stdRenderer.drawTextureRegion(super.getFBOTarget().MAINREGION, camera.getCamera(),
						0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				this.stdRenderer.drawTextureRegion(super.getFBOTarget().mutliTexureRegion,camera.getCamera(),
						0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			}
		};
	}


}
