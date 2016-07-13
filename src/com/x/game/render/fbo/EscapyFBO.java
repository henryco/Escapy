package com.x.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.fbo.psProcess.EscapyPostProcessed;
import com.x.game.render.fbo.psProcess.program.FBORenderProgram;


public abstract class EscapyFBO {

	protected FBORenderProgram<?> renderProgram;
	protected EscapyGdxCamera fboCamera;
	protected Batch fboBatch;
	protected FrameBuffer MAINBUFFER;
	
	/** Creates new FrameBufferObjects, that contains Gdx FrameBuffer and other usefull stuff */
	public EscapyFBO() {
		this.renderProgram = initRenderProgram();
		this.MAINBUFFER = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
		this.fboCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.fboBatch = new SpriteBatch();
		this.initFBO();
	}
	
	
	public void renderFBO() {
		this.renderProgram.renderProgram(this.fboCamera, null);
	}
	public void renderFBO(EscapyPostProcessed ePP) {
		this.renderProgram.renderProgram(this.fboCamera, ePP);
	}
	public void renderFBO(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.renderProgram.renderProgram(camera, ePP);
	}
	public void renderFBO(EscapyGdxCamera camera) {
		this.renderProgram.renderProgram(camera, null);
	}
	
	
	
	protected abstract FBORenderProgram<?> initRenderProgram();
	protected abstract void initFBO();
	public abstract EscapyFBO begin();
	public abstract EscapyFBO end();
	public abstract EscapyFBO mergeBuffer();
	
	public EscapyFBO wipeFBO() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		return this;
	}
	public EscapyFBO forceWipeFBO() {
		this.begin();
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.end();
		return this;
	}
	
	
	
	@Deprecated
	public TextureRegion getNewRenderedRegion(EscapyPostProcessed ePP) {
		
		FrameBuffer fBuffer = getNewRenderedBuffer(ePP);
	
		return new TextureRegion(fBuffer.getColorBufferTexture(), 
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	@Deprecated
	public FrameBuffer getNewRenderedBuffer(EscapyPostProcessed ePP) {
		
		FrameBuffer fBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
		fBuffer = this.renderToBuffer(fBuffer, ePP, this.fboCamera);
		return fBuffer;
	}
	
	
	
	
	public FrameBuffer renderToBuffer(FrameBuffer buffer, EscapyPostProcessed ePP, 
			EscapyGdxCamera cam) {	
		buffer.begin();
			this.renderProgram.renderProgram(cam, ePP);
		buffer.end();
		return buffer;
	}
	public FrameBuffer renderToBuffer(FrameBuffer buffer, EscapyPostProcessed ePP) {
		return renderToBuffer(buffer, ePP, this.fboCamera);
	}
	public FrameBuffer renderToBuffer(FrameBuffer buffer) {
		return renderToBuffer(buffer, null, this.fboCamera);
	}
	
	
	
	
	
	
	
	public FBORenderProgram<?> getRenderProgram() {
		return renderProgram;
	}
	public FrameBuffer getFrameBuffer() {
		return MAINBUFFER;
	}
	

}
