package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.fbo.psProcess.program.userState.FBOVolumeLightProgram;

// TODO: Auto-generated Javadoc
/**
 * The Class NormalMapFBO.
 */
public class NormalMapFBO extends EscapyFBO {

	/** The normal map buffer. */
	protected FrameBuffer normalMapBuffer;
	
	/** The target frame buffer. */
	protected FrameBuffer[] targetFrameBuffer; 
	
	/** The normal map texure region. */
	protected TextureRegion normalMapTexureRegion;
	
	/** The target texture region. */
	protected TextureRegion targetTextureRegion;
	
	/** The batcher. */
	protected SpriteBatch batcher;
	
	/**
	 * Instantiates a new normal map FBO.
	 */
	public NormalMapFBO() {
		super();
		this.setTargetFrameBuffer(new FrameBuffer[0]);
	}
	
	/**
	 * Instantiates a new normal map FBO.
	 *
	 * @param targetFrameBuffer
	 *            the target frame buffer
	 */
	public NormalMapFBO(FrameBuffer targetFrameBuffer) {
		super();
		this.setTargetFrameBuffer(targetFrameBuffer);
	}
	
	/**
	 * Instantiates a new normal map FBO.
	 *
	 * @param targetFrameBuffer
	 *            the target frame buffer
	 */
	public NormalMapFBO(FrameBuffer[] targetFrameBuffer) {
		super();
		this.setTargetFrameBuffer(targetFrameBuffer);
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#initFBO()
	 */
	@Override
	protected void initFBO()
	{
		this.normalMapBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
		
		this.normalMapTexureRegion = new TextureRegion(this.normalMapBuffer.getColorBufferTexture(), 
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	
		this.targetTextureRegion = new TextureRegion(/**_init later _**/);
		
		this.batcher = new SpriteBatch();
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#initRenderProgram()
	 */
	@Override
	protected FBORenderProgram<?> initRenderProgram() {
		return new FBOVolumeLightProgram(this);
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#begin()
	 */
	@Override
	public EscapyFBO begin() {
		this.normalMapBuffer.begin();
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#end()
	 */
	@Override
	public EscapyFBO end() {
		this.normalMapBuffer.end();
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#mergeBuffer()
	 */
	@Override
	public EscapyFBO mergeBuffer() {
		return this.mergeTarget();
	}
	
	
	
	/**
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
	 * Merge target.
	 *
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTarget() {
		return mergeTarget(this.batcher, super.fboCamera.getCamera());
	}
	
	/**
	 * Merge target.
	 *
	 * @param batcher
	 *            the batcher
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTarget(SpriteBatch batcher) {
		return mergeTarget(batcher, super.fboCamera.getCamera());
	}
	
	/**
	 * Merge target.
	 *
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTarget(EscapyGdxCamera camera) {
		return mergeTarget(this.batcher, camera.getCamera());
	}
	
	/**
	 * Merge target.
	 *
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTarget(OrthographicCamera camera) {
		return mergeTarget(this.batcher, camera);
	}
	
	/**
	 * Merge target.
	 *
	 * @param batcher
	 *            the batcher
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTarget(SpriteBatch batcher, EscapyGdxCamera camera) {
		return mergeTarget(batcher, camera.getCamera());
	}
	
	/**
	 * Merge target.
	 *
	 * @param batcher
	 *            the batcher
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTarget(SpriteBatch batcher, OrthographicCamera camera)
	{
		if (targetFrameBuffer.length > 1)
		{
			super.MAINBUFFER.begin();
			camera.update();
			super.wipeFBO();
			for (FrameBuffer buffer : this.targetFrameBuffer)
			{
				batcher.setProjectionMatrix(camera.combined);
				batcher.begin();
				batcher.draw(buffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batcher.end();
			}
			super.MAINBUFFER.end();
			this.targetTextureRegion.setRegion(super.MAINBUFFER.getColorBufferTexture());
			return this;
		}
		else if (targetFrameBuffer.length == 1) {
			super.MAINBUFFER = targetFrameBuffer[0];
			this.targetTextureRegion.setRegion(super.MAINBUFFER.getColorBufferTexture());
			return this;
		}
		this.targetTextureRegion.setRegion(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return this;
	}
	
	
	
	

	
	/**
	 * Adds the target frame buffer.
	 *
	 * @param targetFrameBuffer
	 *            the target frame buffer
	 * @return the normal map FBO
	 */
	public NormalMapFBO addTargetFrameBuffer(FrameBuffer targetFrameBuffer) {
		
		FrameBuffer[] tempBuff = new FrameBuffer[this.targetFrameBuffer.length + 1];
		int it = 0;
		for (FrameBuffer buffer : this.targetFrameBuffer) {
			tempBuff[it] = buffer;
			it++;
		}
		tempBuff[tempBuff.length - 1] = targetFrameBuffer;
		this.targetFrameBuffer = tempBuff;
		return this;
	}
	
	/**
	 * Sets the target frame buffer.
	 *
	 * @param targetFrameBuffer
	 *            the target frame buffer
	 * @return the normal map FBO
	 */
	public NormalMapFBO setTargetFrameBuffer(FrameBuffer[] targetFrameBuffer) {
		this.targetFrameBuffer = targetFrameBuffer;	
		return this;
	}
	
	/**
	 * Sets the target frame buffer.
	 *
	 * @param targetFrameBuffer
	 *            the target frame buffer
	 * @return the normal map FBO
	 */
	public NormalMapFBO setTargetFrameBuffer(FrameBuffer targetFrameBuffer) {
		this.targetFrameBuffer = new FrameBuffer[]{targetFrameBuffer};	
		return this;
	}
	
	/**
	 * Gets the fbo.
	 *
	 * @return the fbo
	 */
	public FrameBuffer getFBO() {
		return this.normalMapBuffer;
	}
	
	/**
	 * Gets the normal map buffer.
	 *
	 * @return the normal map buffer
	 */
	public FrameBuffer getNormalMapBuffer() {
		return normalMapBuffer;
	}
	
	/**
	 * Sets the normal map buffer.
	 *
	 * @param normalMapBuffer
	 *            the new normal map buffer
	 */
	public void setNormalMapBuffer(FrameBuffer normalMapBuffer) {
		this.normalMapBuffer = normalMapBuffer;
	}
	
	/**
	 * Gets the normal map texure region.
	 *
	 * @return the normal map texure region
	 */
	public TextureRegion getNormalMapTexureRegion() {
		return normalMapTexureRegion;
	}
	
	/**
	 * Sets the normal map texure region.
	 *
	 * @param normalMapTexureRegion
	 *            the new normal map texure region
	 */
	public void setNormalMapTexureRegion(TextureRegion normalMapTexureRegion) {
		this.normalMapTexureRegion = normalMapTexureRegion;
	}
	
	/**
	 * Gets the target texture region.
	 *
	 * @return the target texture region
	 */
	public TextureRegion getTargetTextureRegion() {
		return targetTextureRegion;
	}
	
	/**
	 * Sets the target texture region.
	 *
	 * @param targetTextureRegion
	 *            the new target texture region
	 */
	public void setTargetTextureRegion(TextureRegion targetTextureRegion) {
		this.targetTextureRegion = targetTextureRegion;
	}
	
	/**
	 * Gets the target frame buffer.
	 *
	 * @return the target frame buffer
	 */
	public FrameBuffer[] getTargetFrameBuffer() {
		return targetFrameBuffer;
	}

	
	
}
