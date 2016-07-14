package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.EscapyStdShaderRenderer;

/**
 * 
 * Default FrameBufferObject that extends {@link EscapyFBO}.
 * @author Henry
 *
 */

public class StandartFBO extends EscapyFBO {

	protected TextureRegion stdRegion;
	
	/**
	 * Create standart frame buffer object <br>
	 * that extends {@link EscapyFBO}
	 */
	public StandartFBO() {
		super();
	}

	
	@Override
	protected void initFBO() {
		
		this.stdRegion = new TextureRegion(super.MAINBUFFER.getColorBufferTexture(), 
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	
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

	
	@Override
	public EscapyFBO begin() {
		super.MAINBUFFER.begin();
		return this;
	}

	@Override
	public EscapyFBO end() {
		super.MAINBUFFER.end();
		return this;
	}
	
	/**{@link EscapyFBO#mergeBuffer()}*/
	@Override
	public EscapyFBO mergeBuffer() {
		return this;
	}


	
	public FrameBuffer getStdBuffer() {
		return super.MAINBUFFER;
	}
	public TextureRegion getStdRegion() {
		return stdRegion;
	}
	
	


	
	

}
