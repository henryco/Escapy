package com.x.game.render.fbo.psProcess.mask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.fbo.psRender.EscapyPostRenderable;

public abstract class EscapyMask implements EscapyPostRenderable {

	public final static int MULTIPLY = 0;
	public final static int COLOR_DODGE = 1;
	
	protected float WIDTH;
	protected float HEIGHT;
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
	
	public EscapyMask setMaskPosition(float sx, float sy, float ex, float ey)
	{
		startX = sx;
		startY = sy;
		WIDTH = ex;
		HEIGHT = ey;
		return this;
	}

	public EscapyMask setColor(Color color) {
		COLOR = color;
		return this;
	}
	
	public EscapyMask setMode(int mode) {
		this.modeType = modeType(mode);
		return this;
	}
	
	public EscapyMask setBatch(Batch maskBatch) {
		this.maskBatch = maskBatch;
		return this;
	}

	public EscapyMask setDefaultBatch() {
		this.maskBatch = new SpriteBatch();
		return this;
	}
	
	
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
