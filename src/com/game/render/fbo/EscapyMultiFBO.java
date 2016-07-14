package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;

public abstract class EscapyMultiFBO extends EscapyFBO {

	protected FrameBuffer[] targetMultiBuffer;
	protected FrameBuffer multiBuffer;
	protected TextureRegion mutliTexureRegion;
	
	protected Batch batcher;
	
	
	public EscapyMultiFBO() {
		super();
		this.setMultiFrameBuffer(new FrameBuffer[0]);
	}
	

	public EscapyMultiFBO(FrameBuffer multiFrameBuffer) {
		super();
		this.setMultiFrameBuffer(multiFrameBuffer);
	}
	

	public EscapyMultiFBO(FrameBuffer[] multiFrameBuffer) {
		super();
		this.setMultiFrameBuffer(multiFrameBuffer);
	}
	
	@Override
	protected void initFBO() {
		this.batcher = new SpriteBatch();
		this.mutliTexureRegion = new TextureRegion();
		this.multiBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
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


	@Override
	public EscapyFBO mergeBuffer() {
		this.mergeTargetMultiBuffer();
		return this;
	}
	
	
	
	public EscapyMultiFBO setMultiFrameBuffer(FrameBuffer[] multiFrameBuffer) {
		this.targetMultiBuffer = multiFrameBuffer;	
		return this;
	}
	
	public EscapyMultiFBO setMultiFrameBuffer(FrameBuffer multiFrameBuffer) {
		this.targetMultiBuffer = new FrameBuffer[]{multiFrameBuffer};	
		return this;
	}
	
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

	
	
	public EscapyFBO mergeTargetMultiBuffer() {
		return mergeTargetMultiBuffer(this.batcher, super.fboCamera.getCamera());
	}

	public EscapyFBO mergeTargetMultiBuffer(Batch batcher) {
		return mergeTargetMultiBuffer(batcher, super.fboCamera.getCamera());
	}

	public EscapyFBO mergeTargetMultiBuffer(EscapyGdxCamera camera) {
		return mergeTargetMultiBuffer(this.batcher, camera.getCamera());
	}
	
	public EscapyFBO mergeTargetMultiBuffer(OrthographicCamera camera) {
		return mergeTargetMultiBuffer(this.batcher, camera);
	}
	
	public EscapyFBO mergeTargetMultiBuffer(Batch batcher, EscapyGdxCamera camera) {
		return mergeTargetMultiBuffer(batcher, camera.getCamera());
	}
	

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
	
	


}
