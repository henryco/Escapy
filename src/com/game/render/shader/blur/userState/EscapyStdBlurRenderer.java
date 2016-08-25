package com.game.render.shader.blur.userState;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.render.shader.blur.EscapyBlurRenderer;
import com.game.utils.translationVec.TransVec;

public class EscapyStdBlurRenderer extends EscapyBlurRenderer{

	public static final class EscapyBlur {
		
		private static final String url = "data/shaders/blur/";

		public static final String[] GAUSSIAN_5 = new String[]{
				url+"gauss5/gauss5.vert", url+"gauss5/gauss5.frag"};
		public static final String[] GAUSSIAN_9 = new String[]{
				url+"gauss9/gauss9.vert", url+"gauss9/gauss9.frag"};
		public static final String[] GAUSSIAN_13 = new String[]{
				url+"gauss13/gauss13.vert", url+"gauss13/gauss13.frag"};
	}
	
	public static final String SOURCEMAP = "u_texture";
	private TransVec defBlurDirection = new TransVec(1.f, 1.f);
	
	public EscapyStdBlurRenderer(String[] blurType) {
		super(SOURCEMAP, blurType[0], blurType[1]);
	}
	public EscapyStdBlurRenderer(int id, String[] blurType) {
		super(id, SOURCEMAP, blurType[0], blurType[1]);
	}
	public EscapyStdBlurRenderer(String[] blurType, float[] blurDir) {
		super(SOURCEMAP, blurType[0], blurType[1]);
		setBlurDirection(blurDir);
	}
	public EscapyStdBlurRenderer(int id, String[] blurType, float[] blurDir) {
		super(id, SOURCEMAP, blurType[0], blurType[1]);
		setBlurDirection(blurDir);
	}
	public EscapyStdBlurRenderer(String[] blurType, TransVec blurDir) {
		super(SOURCEMAP, blurType[0], blurType[1]);
		setBlurDirection(blurDir);
	}
	public EscapyStdBlurRenderer(int id, String[] blurType, TransVec blurDir) {
		super(id, SOURCEMAP, blurType[0], blurType[1]);
		setBlurDirection(blurDir);
	}
	public EscapyStdBlurRenderer(String[] blurType, float x, float y) {
		super(SOURCEMAP, blurType[0], blurType[1]);
		setBlurDirection(x, y);
	}
	public EscapyStdBlurRenderer(int id, String[] blurType, float x, float y) {
		super(id, SOURCEMAP, blurType[0], blurType[1]);
		setBlurDirection(x, y);
	}
	
	public EscapyStdBlurRenderer setBlurDirection(TransVec defBlurDirection) {
		return this.setBlurDirection(defBlurDirection.x, defBlurDirection.y);
	}
	public EscapyStdBlurRenderer setBlurDirection(float[] defBlurDirection) {
		return this.setBlurDirection(defBlurDirection[0], defBlurDirection[1]);
	}
	public EscapyStdBlurRenderer setBlurDirection(float x, float y) {
		this.defBlurDirection = new TransVec(x, y);
		return this;
	}
	
	public void renderBlured(Sprite reg, OrthographicCamera cam, float widht, float height) {
		super.renderBlured(reg, cam, widht, height, this.defBlurDirection);
	}
	public void renderBlured(Texture reg, OrthographicCamera cam, float widht, float height, 
			float x, float y) {
		super.renderBlured(reg, cam, widht, height, x, y, this.defBlurDirection);
	}
	public void renderBlured(TextureRegion reg, OrthographicCamera cam, float widht, float height, 
			float x, float y) {
		super.renderBlured(reg, cam, widht, height, x, y, this.defBlurDirection);
	}
	
	@Override
	public String toString() {
		return "EscapyStdBlurRenderer_"+super.id;
	}

	
}
