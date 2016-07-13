package com.x.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.fbo.psProcess.program.FBORenderProgram;
import com.x.game.render.fbo.psProcess.program.FBOVolumeLightProgram;

public class NormalMapFBO extends EscapyFBO {

	protected FrameBuffer normalMapBuffer;
	protected FrameBuffer[] targetFrameBuffer; 
	protected TextureRegion normalMapTexureRegion;
	protected TextureRegion targetTextureRegion;
	protected SpriteBatch batcher;
	
	public NormalMapFBO() {
		super();
		this.setTargetFrameBuffer(new FrameBuffer[0]);
	}
	public NormalMapFBO(FrameBuffer targetFrameBuffer) {
		super();
		this.setTargetFrameBuffer(targetFrameBuffer);
	}
	public NormalMapFBO(FrameBuffer[] targetFrameBuffer) {
		super();
		this.setTargetFrameBuffer(targetFrameBuffer);
	}
	
	
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
	
	@Override
	protected FBORenderProgram<?> initRenderProgram() {
		return new FBOVolumeLightProgram(this);
	}

	@Override
	public EscapyFBO begin() {
		this.normalMapBuffer.begin();
		return this;
	}
	@Override
	public EscapyFBO end() {
		this.normalMapBuffer.end();
		return this;
	}
	@Override
	public EscapyFBO mergeBuffer() {
		return this.mergeTarget();
	}
	
	
	
	public EscapyFBO maskNormal() {
		Gdx.gl.glClearColor(0.502f, 0.502f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		return this;
	}
	
	public EscapyFBO mergeTarget() {
		return mergeTarget(this.batcher, super.fboCamera.getCamera());
	}
	public EscapyFBO mergeTarget(SpriteBatch batcher) {
		return mergeTarget(batcher, super.fboCamera.getCamera());
	}
	public EscapyFBO mergeTarget(EscapyGdxCamera camera) {
		return mergeTarget(this.batcher, camera.getCamera());
	}
	public EscapyFBO mergeTarget(OrthographicCamera camera) {
		return mergeTarget(this.batcher, camera);
	}
	public EscapyFBO mergeTarget(SpriteBatch batcher, EscapyGdxCamera camera) {
		return mergeTarget(batcher, camera.getCamera());
	}
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
	
	public NormalMapFBO setTargetFrameBuffer(FrameBuffer[] targetFrameBuffer) {
		this.targetFrameBuffer = targetFrameBuffer;	
		return this;
	}
	public NormalMapFBO setTargetFrameBuffer(FrameBuffer targetFrameBuffer) {
		this.targetFrameBuffer = new FrameBuffer[]{targetFrameBuffer};	
		return this;
	}
	public FrameBuffer getFBO() {
		return this.normalMapBuffer;
	}
	public FrameBuffer getNormalMapBuffer() {
		return normalMapBuffer;
	}
	public void setNormalMapBuffer(FrameBuffer normalMapBuffer) {
		this.normalMapBuffer = normalMapBuffer;
	}
	public TextureRegion getNormalMapTexureRegion() {
		return normalMapTexureRegion;
	}
	public void setNormalMapTexureRegion(TextureRegion normalMapTexureRegion) {
		this.normalMapTexureRegion = normalMapTexureRegion;
	}
	public TextureRegion getTargetTextureRegion() {
		return targetTextureRegion;
	}
	public void setTargetTextureRegion(TextureRegion targetTextureRegion) {
		this.targetTextureRegion = targetTextureRegion;
	}
	public FrameBuffer[] getTargetFrameBuffer() {
		return targetFrameBuffer;
	}

	
	
}
