package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.EscapyStdShaderRenderer;

// TODO: Auto-generated Javadoc
/**
 * 
 * Default FrameBufferObject that extends {@link EscapyFBO}.
 * @author Henry
 *
 */

public class StandartFBO extends EscapyFBO {

	/** The std region. */
	protected TextureRegion stdRegion;
	
	/**
	 * Create standart frame buffer object <br>
	 * that extends {@link EscapyFBO}.
	 */
	public StandartFBO() {
		super();
	}

	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#initFBO()
	 */
	@Override
	protected void initFBO() {
		
		this.stdRegion = new TextureRegion(super.MAINBUFFER.getColorBufferTexture(), 
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#initRenderProgram()
	 */
	@Override
	protected FBORenderProgram<?> initRenderProgram() {
	
		return new FBORenderProgram<StandartFBO>(this) {
			
			private EscapyStdShaderRenderer stdShaderRender 
				= new EscapyStdShaderRenderer();
			
			@Override
			public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) 
			{
				this.stdShaderRender.drawTextureRegion(super.fbo.stdRegion, camera.getCamera(),
						0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}
		};
	}

	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#begin()
	 */
	@Override
	public EscapyFBO begin() {
		super.MAINBUFFER.begin();
		return this;
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#end()
	 */
	@Override
	public EscapyFBO end() {
		super.MAINBUFFER.end();
		return this;
	}
	
	/**
	 * {@link EscapyFBO#mergeBuffer()}.
	 *
	 * @return the escapy FBO
	 */
	@Override
	public EscapyFBO mergeBuffer() {
		return this;
	}


	
	/**
	 * Gets the std buffer.
	 *
	 * @return the std buffer
	 */
	public FrameBuffer getStdBuffer() {
		return super.MAINBUFFER;
	}
	
	/**
	 * Gets the std region.
	 *
	 * @return the std region
	 */
	public TextureRegion getStdRegion() {
		return stdRegion;
	}
	
	


	
	

}
