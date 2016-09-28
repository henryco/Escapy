package com.game.render.fbo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.camera.EscapyGdxCamera;
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


	public StandartFBO(int x, int y, int width, int height, String ... name) {
		super(x, y, width, height, name);
	}
	public StandartFBO(int ID, int x, int y, int width, int height, String ... name) {
		super(ID, x, y, width, height, name);
	}
	public StandartFBO(float x, float y, float width, float height, String ... name) {
		super((int)x, (int)y, (int)width, (int)height, name);
	}
	public StandartFBO(int ID, float x, float y, float width, float height, String ... name) {
		super(ID, (int)x, (int)y, (int)width, (int)height, name);
	}
	public StandartFBO(int ID, int width, int height, String ... name) {
		super(ID, width, height, name);
	}
	/**
	 * @param ID id
	 * @param dim diemnsion int[4] (x, y, widht, height)
	 */
	public StandartFBO(int ID, int[] dim, String ... name) {
		super(ID, dim[0], dim[1], dim[2], dim[3], name);
	}
	/**
	 * @param dim diemnsion int[4] (x, y, widht, height)
	 */
	public StandartFBO(int[] dim, String ... name) {
		super(dim[0], dim[1], dim[2], dim[3], name);
	}
	

	@Override
	protected FBORenderProgram<?> initRenderProgram() {
	
		return new FBORenderProgram<StandartFBO>(this) {
			
			private EscapyStdShaderRenderer stdShaderRender 
				= new EscapyStdShaderRenderer(super.getFBOTarget().getId());
	
			@Override
			public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) 
			{
				this.stdShaderRender.drawTexture(super.getFBOTarget().MAINREGION.getTexture(), 
						camera.getCamera(), super.getFBOTarget().regX, super.getFBOTarget().regY);
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
	 * Sets the std buffer.
	 *
	 * @param buffer
	 *            the buffer
	 * @return the escapy FBO
	 */
	public EscapyFBO setStdBuffer(FrameBuffer buffer) {
		super.MAINBUFFER = buffer;
		return this;
	}
	
	/**
	 * Gets the std region.
	 *
	 * @return the std region
	 */
	public TextureRegion getStdRegion() {
		return super.MAINREGION;
	}
	
	/**
	 * Sets the std region.
	 *
	 * @param region
	 *            the region
	 * @return the escapy FBO
	 */
	public EscapyFBO setStdRegion(TextureRegion region) {
		super.MAINREGION = region;
		return this;
	}


	@Override
	public EscapyFBO endMergedBuffer() {
		return this.end();
	}


	
	

}
