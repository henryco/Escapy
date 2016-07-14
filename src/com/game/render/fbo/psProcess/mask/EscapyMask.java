package com.game.render.fbo.psProcess.mask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psRender.EscapyPostRenderable;


// TODO: Auto-generated Javadoc
/**
 * GL based lightmask superclass.
 *
 * @author Henry
 * @see EscapyLightMask
 */
public abstract class EscapyMask implements EscapyPostRenderable {

	/** The Constant MULTIPLY. */
	public final static int MULTIPLY = 0;
	
	/** The Constant COLOR_DODGE. */
	public final static int COLOR_DODGE = 1;
	
	/** The height. */
	protected float WIDTH, HEIGHT;
	
	/** The start Y. */
	protected float startX, startY;
	
	/** The color. */
	protected Color COLOR;
	
	/** The mode type. */
	protected int[] modeType;
	
	/** The def blend mode DST. */
	protected int defBlendModeSRC, defBlendModeDST;
	
	/** The mask batch. */
	protected Batch maskBatch;
	
	/** The post render camera. */
	protected EscapyGdxCamera postRenderCamera;
	
	
	/**
	 * Instantiates a new escapy mask.
	 */
	public EscapyMask() {
		this.maskBatch = new SpriteBatch();
		this.getDstSrcBlendfunc();
		this.postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.initMask();
	}
	
	/**
	 * Instantiates a new escapy mask.
	 *
	 * @param postRenderCamera
	 *            the post render camera
	 */
	public EscapyMask(EscapyGdxCamera postRenderCamera) {
		this.maskBatch = new SpriteBatch();
		this.getDstSrcBlendfunc();
		this.postRenderCamera = postRenderCamera;
		this.initMask();
	}
	
	/**
	 * Inits the mask.
	 */
	protected abstract void initMask();
	
	/**
	 * Sets the mask position.
	 *
	 * @param sx
	 *            - start x point
	 * @param sy
	 *            - start y point
	 * @param width
	 *            - Width
	 * @param height
	 *            - Height
	 * @return mask.
	 */
	public EscapyMask setMaskPosition(float sx, float sy, float width, float height)
	{
		this.startX = sx;
		this.startY = sy;
		this.WIDTH = width;
		this.HEIGHT = height;
		return this;
	}

	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the color
	 * @return the escapy mask
	 */
	public EscapyMask setColor(Color color) {
		COLOR = color;
		return this;
	}
	
	/**
	 * Set blend mode.
	 * @param mode - blend mode
	 * @return mask
	 */
	public EscapyMask setMode(int mode) {
		this.modeType = modeType(mode);
		return this;
	}
	
	/**
	 * Sets the batch.
	 *
	 * @param maskBatch
	 *            - {@link Batch} cannot be null.
	 * @return mask.
	 */
	public EscapyMask setBatch(Batch maskBatch) {
		this.maskBatch = maskBatch;
		return this;
	}
	
	/**
	 * Restore standart Batch.
	 * @return mask
	 */
	public EscapyMask setDefaultBatch() {
		this.maskBatch = new SpriteBatch();
		return this;
	}
	
	/**
	 *
	 * @param camera
	 *            the camera cannot be null.
	 * @return the escapy post renderable
	 */
	@Override
	public EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}
	
	
	private int[] modeType(int mode)
	{	
		if (mode == MULTIPLY)
			return new int[]{GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_SRC_ALPHA};
		if (mode == COLOR_DODGE)
			return new int[]{GL20.GL_DST_COLOR, GL20.GL_ONE};
		return new int[]{GL20.GL_DST_COLOR, GL20.GL_ONE_MINUS_SRC_ALPHA};
	}
	
	protected void getDstSrcBlendfunc() {
		this.defBlendModeDST = this.maskBatch.getBlendDstFunc();
		this.defBlendModeSRC = this.maskBatch.getBlendSrcFunc();
	}
	
}
