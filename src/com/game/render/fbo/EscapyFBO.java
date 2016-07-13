package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;

/**
 * <P>
 * Superclass of Frame Buffer Object, that contains GDX-frameBuffer {@link FrameBuffer}
 * and some usefull methods to work with them
 * </p>
 * @author HenryDCO
 */

public abstract class EscapyFBO {

	protected FBORenderProgram<?> renderProgram;
	protected EscapyGdxCamera fboCamera;
	protected Batch fboBatch;
	protected FrameBuffer MAINBUFFER;
	
	/** 
	 * <p>Creates new FrameBufferObjects superclass, that contains Gdx {@link FrameBuffer} and other usefull stuff</p> 
	 * <p><b>See:</b> {@link NormalMapFBO}, {@link StandartFBO}.</p>
	 * */
	public EscapyFBO() {
		this.renderProgram = initRenderProgram();
		this.MAINBUFFER = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
		this.fboCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.fboBatch = new SpriteBatch();
		this.initFBO();
		
	}
	
	
	/**<p>Render data from from internal {@link FrameBuffer}</p>
	 * <p>Use {@link FBORenderProgram}, {@link EscapyGdxCamera}, and {@link EscapyPostProcessed} data (null by default)*/
	public void renderFBO() {
		this.renderProgram.renderProgram(this.fboCamera, null);
	}
	/**Render data from internal {@link FrameBuffer}
	 * @param ePP {@link EscapyPostProcessed} optional data*/
	public void renderFBO(EscapyPostProcessed ePP) {
		this.renderProgram.renderProgram(this.fboCamera, ePP);
	}
	/**Render data from from internal {@link FrameBuffer}
	 * @param camera {@link EscapyGdxCamera} cannot be null
	 * @param ePP {@link EscapyPostProcessed} optional data*/
	public void renderFBO(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.renderProgram.renderProgram(camera, ePP);
	}
	/**Render data from from internal {@link FrameBuffer}
	 * @param camera {@link EscapyGdxCamera} cannot be null*/
	public void renderFBO(EscapyGdxCamera camera) {
		this.renderProgram.renderProgram(camera, null);
	}
	
	
	
	protected abstract FBORenderProgram<?> initRenderProgram();
	protected abstract void initFBO();
	public abstract EscapyFBO begin();
	public abstract EscapyFBO end();
	
	/**Merge all frame buffers
	 * <p><b>See:</b>{@link FrameBuffer} </p>*/
	public abstract EscapyFBO mergeBuffer();
	
	/** Clear buffer, need to use {@link EscapyFBO#begin()} / {@link EscapyFBO#end()}*/
	public EscapyFBO wipeFBO() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		return this;
	}
	/** Force clear buffer*/
	public EscapyFBO forceWipeFBO() {
		this.begin();
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.end();
		return this;
	}
	
	
	
	/**<p>Creates new {@link TextureRegion} from contained fbo. </p>
	 * <p>Dont use it in loop, it has trand to memory leak</p> */
	@Deprecated
	public TextureRegion getNewRenderedRegion(EscapyPostProcessed ePP) {
		
		FrameBuffer fBuffer = getNewRenderedBuffer(ePP);
	
		return new TextureRegion(fBuffer.getColorBufferTexture(), 
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/**<p>Creates new {@link FrameBuffer} from contained other fbo. </p>
	 * <p>Dont use it in loop, it has trand to memory leak</p> */
	@Deprecated 
	public FrameBuffer getNewRenderedBuffer(EscapyPostProcessed ePP) {
		
		FrameBuffer fBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
		fBuffer = this.renderToBuffer(fBuffer, ePP, this.fboCamera);
		return fBuffer;
	}
	
	
	
	/**Render data from contained {@link FrameBuffer} into target buffer,
	 * <br>this method uses for this {@link FBORenderProgram}.
	 * @param buffer - target {@link FrameBuffer} to render for.
	 * @param ePP - optional {@link EscapyPostProcessed} data for render.
	 * @param cam - {@link EscapyGdxCamera} cannot be null.
	 * @return {@link FrameBuffer} with rendered data.*/
	public FrameBuffer renderToBuffer(FrameBuffer buffer, EscapyPostProcessed ePP, 
			EscapyGdxCamera cam) {	
		buffer.begin();
			this.renderProgram.renderProgram(cam, ePP);
		buffer.end();
		return buffer;
	}
	
	/**Render data from contained {@link FrameBuffer} into target buffer,
	 * <br>this method uses for this {@link FBORenderProgram} and default camera {@link EscapyGdxCamera}.
	 * @param buffer - target {@link FrameBuffer} to render for.
	 * @param ePP - optional {@link EscapyPostProcessed} data for render.
	 * @return {@link FrameBuffer} with rendered data.*/
	public FrameBuffer renderToBuffer(FrameBuffer buffer, EscapyPostProcessed ePP) {
		return renderToBuffer(buffer, ePP, this.fboCamera);
	}
	
	/**Render data from contained {@link FrameBuffer} into target buffer,
	 * <br>this method uses for this {@link FBORenderProgram}, default 
	 * camera {@link EscapyGdxCamera} and {@link EscapyPostProcessed} data for render.
	 * @param buffer - target {@link FrameBuffer} to render for.
	 * @return {@link FrameBuffer} with rendered data.*/
	public FrameBuffer renderToBuffer(FrameBuffer buffer) {
		return renderToBuffer(buffer, null, this.fboCamera);
	}
	
	
	
	
	/**Method that <b>return </b>{@link FBORenderProgram}*/
	public FBORenderProgram<?> getRenderProgram() {
		return renderProgram;
	}
	
	/**Method that return main frame buffer, sometimes need to use {@link EscapyFBO#mergeBuffer()} before.
	 * @return {@link FrameBuffer} - main fbo*/
	public FrameBuffer getFrameBuffer() {
		return MAINBUFFER;
	}
	

}
