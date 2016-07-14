package com.game.render.fbo.psProcess.mask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.psRender.EscapyPostRenderable;


/**
 * GL based lightmask superclass.
 * @see EscapyLightMask
 * @author Henry
 *
 */
public abstract class EscapyMask implements EscapyPostRenderable {

	public final static int MULTIPLY = 0;
	public final static int COLOR_DODGE = 1;
	
	protected float WIDTH, HEIGHT;
	protected float startX, startY;
	
	protected Color COLOR;
	protected int[] modeType;
	protected int defBlendModeSRC, defBlendModeDST;
	
	protected Batch maskBatch;
	protected EscapyGdxCamera postRenderCamera;
	
	
	public EscapyMask() {
		this.maskBatch = new SpriteBatch();
		this.getDstSrcBf();
		this.postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.initMask();
	}
	public EscapyMask(EscapyGdxCamera postRenderCamera) {
		this.maskBatch = new SpriteBatch();
		this.getDstSrcBf();
		this.postRenderCamera = postRenderCamera;
		this.initMask();
	}
	
	protected abstract void initMask();
	
	/**
	 * 
	 * @param sx - start x point
	 * @param sy - start y point
	 * @param width
	 * @param height
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
	 * 
	 * @param maskBatch - {@link Batch} cannot be null.
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
	 * <br>
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
	
	private void getDstSrcBf() {
		this.defBlendModeDST = this.maskBatch.getBlendDstFunc();
		this.defBlendModeSRC = this.maskBatch.getBlendSrcFunc();
	}
	
}
