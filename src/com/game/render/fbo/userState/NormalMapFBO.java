package com.game.render.fbo.userState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.program.FBORenderProgram;

// TODO: Auto-generated Javadoc
/**@deprecated
 * The Class NormalMapFBO.
 */
public class NormalMapFBO extends EscapyMultiFBO {


	/**@deprecated
	 * Instantiates a new normal map FBO.
	 */
	public NormalMapFBO() {
		super();
	}
	
	/**@deprecated
	 * Instantiates a new normal map FBO.
	 *
	 * @param targetFrameBuffer
	 *            the target frame buffer
	 */
	public NormalMapFBO(FrameBuffer targetFrameBuffer) {
		super(targetFrameBuffer);
	}
	
	/**@deprecated
	 * Instantiates a new normal map FBO.
	 *
	 * @param targetFrameBuffer
	 *            the target frame buffer
	 */
	public NormalMapFBO(FrameBuffer[] targetFrameBuffer) {
		super(targetFrameBuffer);
	}

	@Override
	protected void initFBO() {
		super.initFBO();
	}
	

	@Override
	protected FBORenderProgram<?> initRenderProgram() {

		return null;
		//		new FBOVolumeLightProgram(this);
	}

	
	/**@deprecated
	 * Mask normal.
	 *
	 * @return the escapy FBO
	 */
	public EscapyFBO maskNormal() {
		Gdx.gl.glClearColor(0.502f, 0.502f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		return this;
	}
	
	
	
	/**
	 * Gets the fbo.
	 *
	 * @return the fbo
	 * @deprecated
	 */
	public FrameBuffer getFBO() {
		return super.MAINBUFFER;
	}
	
	/**
	 * Gets the normal map buffer.
	 *
	 * @return the normal map buffer
	 */
	public FrameBuffer getNormalMapBuffer() {
		return super.MAINBUFFER;
	}
	
	/**
	 * Sets the normal map buffer.
	 *
	 * @param normalMapBuffer
	 *            the new normal map buffer
	 */
	public void setNormalMapBuffer(FrameBuffer normalMapBuffer) {
		super.MAINBUFFER = normalMapBuffer;
	}
	
	/**
	 * Gets the normal map texure region.
	 *
	 * @return the normal map texure region
	 */
	public TextureRegion getNormalMapTexureRegion() {
		return super.MAINREGION;
	}
	
	/**
	 * Sets the normal map texure region.
	 *
	 * @param normalMapTexureRegion
	 *            the new normal map texure region
	 */
	public void setNormalMapTexureRegion(TextureRegion normalMapTexureRegion) {
		super.MAINREGION = normalMapTexureRegion;
	}
	
	/**
	 * Gets the target texture region.
	 *
	 * @return the target texture region
	 */
	public TextureRegion getTargetTextureRegion() {
		return super.mutliTexureRegion;
	}
	
	/**
	 * Sets the target texture region.
	 *
	 * @param targetTextureRegion
	 *            the new target texture region
	 */
	public void setTargetTextureRegion(TextureRegion targetTextureRegion) {
		super.mutliTexureRegion = targetTextureRegion;
	}
	
	/**
	 * Gets the target frame buffer.
	 *
	 * @return the target frame buffer
	 */
	public FrameBuffer[] getTargetFrameBuffer() {
		return super.targetMultiBuffer;
	}
	
	
}
