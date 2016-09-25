package com.game.render.mask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;

/**
 * @author Henry on 23/09/16.
 */
public class LightMask {

	public static final int[] MULTIPLY = new int[]{GL30.GL_DST_COLOR, GL30.GL_ONE_MINUS_SRC_ALPHA};
	public static final int[] SEPIA = new int[]{GL30.GL_SRC_ALPHA, GL30.GL_DST_COLOR};
	public static final int[] SCREEN = new int[]{GL30.GL_ONE_MINUS_DST_COLOR, GL30.GL_ONE};
	public static final int[] LINEAR_DODGE = new int[]{GL30.GL_ONE, GL30.GL_ONE};


	private Color color;
	private Batch batch;
	private int width, height, x, y;
	private Texture maskTexture;
	private int[] blendFunc;
	private EscapyGdxCamera camera;

	public LightMask(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		camera = new EscapyGdxCamera(width, height);
		setMaskFunc(MULTIPLY);
		setColor((60f/255f), (60f/255f), (60f/255f), 1f);
	}
	public LightMask(int[] dim) {
		this.x = dim[0];
		this.y = dim[1];
		this.width = dim[2];
		this.height = dim[3];
		camera = new EscapyGdxCamera(dim[2], dim[3]);
		setMaskFunc(MULTIPLY);
		setColor((60f/255f), (60f/255f), (60f/255f), 1f);
	}

	public LightMask initMaskTexture() {

		FrameBuffer tmp = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
		tmp.begin();
		Gdx.gl.glClearColor(this.color.r, this.color.g, this.color.b, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tmp.end();
		maskTexture = tmp.getColorBufferTexture();
		return this;
	}

	public LightMask setMaskFunc(int dst, int src) {
		batch = new SpriteBatch();
		blendFunc = new int[]{dst, src};
		return this;
	}
	public LightMask setMaskFunc(int[] func) {
		return setMaskFunc(func[0], func[1]);
	}

	public LightMask setColor(float r, float g, float b, float a) {
		color = new Color(r, g, b, a);
		initMaskTexture();
		return this;
	}
	public LightMask setColor(int r, int g, int b, int a) {
		return setColor(r/255f, g/255f, b/255f, a/255f);
	}

	public EscapyFBO renderMaskBuffered(Texture target, EscapyFBO fbo){

		fbo.begin();
		renderMask(target);
		return fbo.end();
	}

	public void renderMask(Texture target) {
		batch.setProjectionMatrix(camera.getCamera().combined);

		int srcFunc = batch.getBlendSrcFunc();
		int dstFunc = batch.getBlendDstFunc();

		batch.begin();
		batch.draw(target, 0, 0);
		batch.end();

		batch.begin();
		batch.enableBlending();
		batch.setBlendFunction(blendFunc[0], blendFunc[1]);
		batch.draw(maskTexture, x, y);
		batch.disableBlending();
		batch.end();

		batch.begin();
		batch.setBlendFunction(srcFunc, dstFunc);
		batch.end();
	}

}
