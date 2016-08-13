package com.game.render.fbo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;

// TODO: Auto-generated Javadoc
/**
 * <P>
 * Superclass of Frame Buffer Object, that contains GDX {@link FrameBuffer}
 * and some usefull methods to easy work with them.
 * </p>
 * @author HenryDCO
 */

public abstract class EscapyFBO {

	/** The render program. */
	protected FBORenderProgram<?> renderProgram;
	
	/** The fbo camera. */
	protected EscapyGdxCamera fboCamera;
	
	/** The fbo batch. */
	protected Batch fboBatch;
	
	/** The mainbuffer. */
	protected FrameBuffer MAINBUFFER;
	
	/** The mainregion. */
	protected TextureRegion MAINREGION;
	
	protected Sprite MAINSPRITE;
	
	protected final int regX, regY, regWidth, regHeight;
				
	private final int id;
	
	
	/** 
	 * Creates new FrameBufferObjects superclass, that contains Gdx {@link FrameBuffer} and other usefull stuff 
	 * <b>See:</b> {@link NormalMapFBO}, {@link StandartFBO}.
	 * */
	public EscapyFBO() {
		
		this.regX = 0;
		this.regY = 0;
		this.regWidth = Gdx.graphics.getWidth();
		this.regHeight = Gdx.graphics.getHeight();
		
		this.id = this.hashCode();
		this.initFBO();
	}
	
	public EscapyFBO(int ID) {
		
		this.regX = 0;
		this.regY = 0;
		this.regWidth = Gdx.graphics.getWidth();
		this.regHeight = Gdx.graphics.getHeight();
		
		this.id = ID;
		this.initFBO();
	}
	
	public EscapyFBO(int x, int y, int width, int height) {
		
		this.regX = x;
		this.regY = y;
		this.regWidth = width;
		this.regHeight = height;
		
		this.id = this.hashCode();
		this.initFBO();
	}
	
	public EscapyFBO(int ID, int x, int y, int width, int height) {
		
		this.regX = x;
		this.regY = y;
		this.regWidth = width;
		this.regHeight = height;
		
		this.id = ID;
		this.initFBO();
	}
	
	public EscapyFBO(int ID, int width, int height) {
		
		this.regX = 0;
		this.regY = 0;
		this.regWidth = width;
		this.regHeight = height;
		
		this.id = ID;
		this.initFBO();
		
		this.MAINREGION = new TextureRegion(MAINBUFFER.getColorBufferTexture());
		this.MAINSPRITE = new Sprite(MAINREGION);
	}
	
	public EscapyFBO(int width, int height) {
		
		this.regX = 0;
		this.regY = 0;
		this.regWidth = width;
		this.regHeight = height;
		
		this.id = this.hashCode();
		this.initFBO();
		
		this.MAINREGION = new TextureRegion(MAINBUFFER.getColorBufferTexture());
		this.MAINSPRITE = new Sprite(MAINREGION);
	}
	
	
	/**
	 * Inits the FBO.
	 */
	protected void initFBO() {
		
		this.renderProgram = initRenderProgram();
		this.MAINBUFFER = new FrameBuffer(Format.RGBA8888, regWidth, regHeight, false);
		
		this.MAINREGION = new TextureRegion(this.MAINBUFFER.getColorBufferTexture(), 
				regX, regY, regWidth, regHeight);
		
		this.MAINSPRITE = new Sprite(MAINREGION);
		
		this.fboCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.fboBatch = new SpriteBatch();
		System.out.println("COMPILED: "+this.toString());
		
	}
	
	
	/**
	 * <p>Render data from from internal {@link FrameBuffer}</p>
	 * <p>Use {@link FBORenderProgram}, {@link EscapyGdxCamera}, and {@link EscapyPostProcessed} data (null by default).
	 */
	public void renderFBO() {
		this.renderProgram.renderProgram(this.fboCamera, null);
	}
	
	/**
	 * Render data from internal {@link FrameBuffer}.
	 *
	 * @param ePP {@link EscapyPostProcessed} optional data
	 */
	public void renderFBO(EscapyPostProcessed ePP) {
		this.renderProgram.renderProgram(this.fboCamera, ePP);
	}
	
	/**
	 * Render data from from internal {@link FrameBuffer}.
	 *
	 * @param camera {@link EscapyGdxCamera} cannot be null
	 * @param ePP {@link EscapyPostProcessed} optional data
	 */
	public void renderFBO(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		this.renderProgram.renderProgram(camera, ePP);
	}
	
	/**
	 * Render data from from internal {@link FrameBuffer}.
	 *
	 * @param camera {@link EscapyGdxCamera} cannot be null
	 */
	public void renderFBO(EscapyGdxCamera camera) {
		this.renderProgram.renderProgram(camera, null);
	}
	
	
	
	/**
	 * Inits the render program.
	 *
	 * @return the FBO render program
	 */
	protected abstract FBORenderProgram<?> initRenderProgram();
	

	
	/**
	 * Begin.
	 *
	 * @return the escapy FBO
	 */
	public abstract EscapyFBO begin();
	
	/**
	 * End.
	 *
	 * @return the escapy FBO
	 */
	public abstract EscapyFBO end();
	
	/**
	 * Merge all frame buffers
	 * <p><b>See:</b>{@link FrameBuffer} </p>.
	 *
	 * @return the escapy FBO
	 */
	public abstract EscapyFBO mergeBuffer();
	
	public EscapyFBO restart(){
		return this.end().begin();
	}
	
	/**
	 *  Clear buffer, need to use {@link EscapyFBO#begin()} / {@link EscapyFBO#end()}.
	 *
	 * @return the escapy FBO
	 */
	public EscapyFBO wipeFBO() {
		return this.clearFBO(0f,0f,0f,0f);
	}
	
	/**
	 *  Force clear buffer.
	 *
	 * @return the escapy FBO
	 */
	public EscapyFBO forceWipeFBO() {
		this.begin();
		this.clearFBO(0f,0f,0f,0f);
		this.end();
		return this;
	}
	
	public EscapyFBO clearFBO(float r, float g, float b, float a) {
		Gdx.gl.glClearColor(r, g, b, a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		return this;
	}
	
	public EscapyFBO forceClearFBO(float r, float g, float b, float a) {
		this.begin();
		this.clearFBO(r,g,b,a);
		this.end();
		return this;
	}
	
	public EscapyFBO forceClearFBO() {
		return forceClearFBO(0f,0f,0f,1f);
	}
	
	public EscapyFBO clearFBO() {
		return this.clearFBO(0f,0f,0f,1f);
	}
	
	/**
	 * <p>Creates new {@link TextureRegion} from contained fbo. </p>
	 * <p>Dont use it in loop, it has trand to memory leak</p>
	 *
	 * @param ePP the {@link EscapyPostProcessed}, optional data for render.
	 * @return the new rendered region
	 */
	@Deprecated
	public TextureRegion getNewRenderedRegion(EscapyPostProcessed ePP) {
		
		FrameBuffer fBuffer = getNewRenderedBuffer(ePP);
	
		return new TextureRegion(fBuffer.getColorBufferTexture(), 
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/**
	 * <p>Creates new {@link FrameBuffer} from contained other fbo. </p>
	 * <p>Dont use it in loop, it has trand to memory leak</p>
	 *
	 * @param ePP the {@link EscapyPostProcessed}, optional data for render.
	 * @return the new rendered buffer
	 */
	@Deprecated 
	public FrameBuffer getNewRenderedBuffer(EscapyPostProcessed ePP) {
		
		FrameBuffer fBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), false);
		fBuffer = this.renderOutBuffer(fBuffer, ePP, this.fboCamera);
		return fBuffer;
	}
	
	
	
	/**Render data from contained {@link FrameBuffer} into target buffer,
	 * <br>this method uses for this {@link FBORenderProgram}.
	 * @param buffer - target {@link FrameBuffer} to render for.
	 * @param ePP - optional {@link EscapyPostProcessed} data for render.
	 * @param cam - {@link EscapyGdxCamera} cannot be null.
	 * @return buffer - {@link FrameBuffer} with rendered data.
	 * @see {@link FBORenderProgram#renderProgram(EscapyGdxCamera, EscapyPostProcessed)} */
	public FrameBuffer renderOutBuffer(FrameBuffer buffer, EscapyPostProcessed ePP, 
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
	 * @return buffer - {@link FrameBuffer} with rendered data.
	 * @see {@link FBORenderProgram#renderProgram(EscapyGdxCamera, EscapyPostProcessed)}*/
	public FrameBuffer renderOutBuffer(FrameBuffer buffer, EscapyPostProcessed ePP) {
		return renderOutBuffer(buffer, ePP, this.fboCamera);
	}
	
	/**Render data from contained {@link FrameBuffer} into target buffer,
	 * this method uses for this {@link FBORenderProgram}, default 
	 * camera {@link EscapyGdxCamera} and {@link EscapyPostProcessed} data for render.
	 * @param buffer - target {@link FrameBuffer} to render for.
	 * @return buffer - {@link FrameBuffer} with rendered data.
	 * @see {@link FBORenderProgram#renderProgram(EscapyGdxCamera, EscapyPostProcessed)}*/
	public FrameBuffer renderOutBuffer(FrameBuffer buffer) {
		return renderOutBuffer(buffer, null, this.fboCamera);
	}
	
	
	public EscapyFBO renderToFBO(EscapyFBO fbo) {
		fbo.renderFBO();
		return this;
	}
	
	public EscapyFBO renderToFBO(EscapyFBO fbo, EscapyPostProcessed ePP, EscapyGdxCamera cam) {	
		fbo.renderFBO(cam, ePP);
		return this;
	}
	public EscapyFBO renderToFBO(EscapyFBO fbo, EscapyPostProcessed ePP) {	
		return this.renderToFBO(fbo, ePP, this.fboCamera);
	}
	public EscapyFBO renderToFBO(EscapyFBO fbo, EscapyGdxCamera cam) {	
		return this.renderToFBO(fbo, null, cam);
	}
	
	
	public EscapyFBO forceRenderToFBO(EscapyFBO fbo) {	
		this.begin();
			fbo.renderFBO();
		this.end();	
		return this;
	}
	public EscapyFBO forceRenderToFBO(EscapyFBO fbo, EscapyPostProcessed ePP, 
			EscapyGdxCamera cam) {	
		this.begin();
			fbo.renderFBO(cam, ePP);
		this.end();	
		return this;
	}
	public EscapyFBO forceRenderToFBO(EscapyFBO fbo, EscapyPostProcessed ePP) {	
		return this.forceRenderToFBO(fbo, ePP, this.fboCamera);
	}
	
	public EscapyFBO forceRenderToFBO(EscapyFBO fbo, EscapyGdxCamera cam) {	
		return this.forceRenderToFBO(fbo, null, cam);
	}
	
	/**
	 * Method that <b>return </b>{@link FBORenderProgram}.
	 *
	 * @return the render program
	 */
	public FBORenderProgram<?> getRenderProgram() {
		return renderProgram;
	}
	
	/**
	 * Sets the render program.
	 *
	 * @param renderProgram
	 *            the render program
	 * @return the escapy FBO
	 */
	public EscapyFBO setRenderProgram(FBORenderProgram<?> renderProgram) {
		this.renderProgram = renderProgram;
		return this;
	}
	
	/**Method that return stored main framebuffer, sometimes need to use {@link EscapyFBO#mergeBuffer()} before.
	 * @return {@link FrameBuffer} - main fbo*/
	public FrameBuffer getFrameBuffer() {
		return MAINBUFFER;
	}
	
	/**
	 * Sets the frame buffer.
	 *
	 * @param buffer
	 *            the {@link FrameBuffer} main fbo.
	 * @return the escapy FBO
	 */
	public EscapyFBO setFrameBuffer(FrameBuffer buffer) {
		this.MAINBUFFER = buffer;
		return this;
	}


	/**
	 * Gets the texture region, sometimes need to use {@link EscapyFBO#mergeBuffer()} before.
	 *
	 * @return the texture main region
	 */
	public TextureRegion getTextureRegion() {
		return MAINREGION;
	}


	/**
	 * Sets the texture region.
	 *
	 * @param MAINREGION
	 *            the new main texture region
	 */
	public void setTextureRegion(TextureRegion MAINREGION) {
		this.MAINREGION = MAINREGION;
	}

	public abstract EscapyFBO endMergedBuffer();
	
	@Override
	public String toString() {
		return "EscapyFBO_"+this.getId();
	}

	public int getId() {
		return id;
	}

	public Sprite getSpriteRegion() {
		return MAINSPRITE;
	}

	public void setSpriteRegion(Sprite mAINSPRITE) {
		MAINSPRITE = mAINSPRITE;
	}

	public int getRegX() {
		return regX;
	}

	public int getRegY() {
		return regY;
	}

	public int getRegWidth() {
		return regWidth;
	}

	public int getRegHeight() {
		return regHeight;
	}

	public EscapyGdxCamera getFBOCamera() {
		return fboCamera;
	}

}
