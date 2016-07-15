package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyMultiFBO.
 */
public abstract class EscapyMultiFBO extends EscapyFBO {

	/** The target multi buffer. */
	protected FrameBuffer[] targetMultiBuffer;
	
	/** The multi buffer. */
	protected FrameBuffer multiBuffer;
	
	/** The mutli texure region. */
	protected TextureRegion mutliTexureRegion;
	
	/** The batcher. */
	protected Batch batcher;
	
	
	/**
	 * Instantiates a new escapy multi FBO.
	 */
	public EscapyMultiFBO() {
		super();
		this.setMultiFrameBuffer(new FrameBuffer[0]);
	}
	

	/**
	 * Instantiates a new escapy multi FBO.
	 *
	 * @param multiFrameBuffer
	 *            the multi frame buffer
	 */
	public EscapyMultiFBO(FrameBuffer multiFrameBuffer) {
		super();
		this.setMultiFrameBuffer(multiFrameBuffer);
	}
	

	/**
	 * Instantiates a new escapy multi FBO.
	 *
	 * @param multiFrameBuffer
	 *            the multi frame buffer
	 */
	public EscapyMultiFBO(FrameBuffer[] multiFrameBuffer) {
		super();
		this.setMultiFrameBuffer(multiFrameBuffer);
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#initFBO()
	 */
	@Override
	protected void initFBO() {
		this.batcher = new SpriteBatch();
		this.mutliTexureRegion = new TextureRegion();
		this.multiBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
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


	/* (non-Javadoc)
	 * @see com.game.render.fbo.EscapyFBO#mergeBuffer()
	 */
	@Override
	public EscapyFBO mergeBuffer() {
		this.mergeTargetMultiBuffer();
		return this;
	}
	
	
	/**
	 * Sets the multi texture region.
	 *
	 * @param multiTextureRegion
	 *            the multi texture region
	 * @return the escapy multi FBO
	 */
	public EscapyMultiFBO setMultiTextureRegion(TextureRegion multiTextureRegion) {
		this.mutliTexureRegion = multiTextureRegion;
		return this;
	}
	
	/**
	 * Sets the multi frame buffer.
	 *
	 * @param multiFrameBuffer
	 *            the multi frame buffer
	 * @return the escapy multi FBO
	 */
	public EscapyMultiFBO setMultiFrameBuffer(FrameBuffer[] multiFrameBuffer) {
		this.targetMultiBuffer = multiFrameBuffer;	
		return this;
	}
	
	/**
	 * Sets the multi frame buffer.
	 *
	 * @param multiFrameBuffer
	 *            the multi frame buffer
	 * @return the escapy multi FBO
	 */
	public EscapyMultiFBO setMultiFrameBuffer(FrameBuffer multiFrameBuffer) {
		this.targetMultiBuffer = new FrameBuffer[]{multiFrameBuffer};	
		return this;
	}
	
	/**
	 * Adds the multi frame buffer.
	 *
	 * @param multiFrameBuffer
	 *            the multi frame buffer
	 * @return the escapy multi FBO
	 */
	public EscapyMultiFBO addMultiFrameBuffer(FrameBuffer multiFrameBuffer) {
		
		FrameBuffer[] tempBuff = new FrameBuffer[this.targetMultiBuffer.length + 1];
		int it = 0;
		for (FrameBuffer buffer : this.targetMultiBuffer) {
			tempBuff[it] = buffer;
			it++;
		}
		tempBuff[tempBuff.length - 1] = multiFrameBuffer;
		this.targetMultiBuffer = tempBuff;
		return this;
	}
	
	/**
	 * Merge target multi buffer.
	 *
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTargetMultiBuffer() {
		return mergeTargetMultiBuffer(this.batcher, super.fboCamera.getCamera());
	}

	/**
	 * Merge target multi buffer.
	 *
	 * @param batcher
	 *            the batcher
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTargetMultiBuffer(Batch batcher) {
		return mergeTargetMultiBuffer(batcher, super.fboCamera.getCamera());
	}

	/**
	 * Merge target multi buffer.
	 *
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTargetMultiBuffer(EscapyGdxCamera camera) {
		return mergeTargetMultiBuffer(this.batcher, camera.getCamera());
	}
	
	/**
	 * Merge target multi buffer.
	 *
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTargetMultiBuffer(OrthographicCamera camera) {
		return mergeTargetMultiBuffer(this.batcher, camera);
	}
	
	/**
	 * Merge target multi buffer.
	 *
	 * @param batcher
	 *            the batcher
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTargetMultiBuffer(Batch batcher, EscapyGdxCamera camera) {
		return mergeTargetMultiBuffer(batcher, camera.getCamera());
	}
	
	/**
	 * Merge target multi buffer.
	 *
	 * @param batcher
	 *            the batcher
	 * @param camera
	 *            the camera
	 * @return the escapy FBO
	 */
	public EscapyFBO mergeTargetMultiBuffer(Batch batcher, OrthographicCamera camera)
	{
		if (targetMultiBuffer.length > 0)
		{
			this.multiBuffer.begin();
			camera.update();
			super.wipeFBO();
			for (FrameBuffer buffer : this.targetMultiBuffer)
			{
				batcher.setProjectionMatrix(camera.combined);
				batcher.begin();
				batcher.draw(buffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batcher.end();
			}
			this.multiBuffer.end();
			this.mutliTexureRegion.setRegion(this.multiBuffer.getColorBufferTexture());
		}
		return this;
	}
	
	/**
	 * Gets the multi texture region.
	 *
	 * @return the multi texture region
	 */
	public TextureRegion getMultiTextureRegion() {
		return this.mutliTexureRegion;
	}
	
	/**
	 * Gets the multi frame buffer.
	 *
	 * @return the multi frame buffer
	 */
	public FrameBuffer[] getMultiFrameBuffer() {
		return this.targetMultiBuffer;
	}


}
