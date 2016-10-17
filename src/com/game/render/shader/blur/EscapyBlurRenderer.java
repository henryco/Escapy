package com.game.render.shader.blur;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.EscapyShaderRender;
import com.game.utils.translationVec.TransVec;

public class EscapyBlurRenderer extends EscapyShaderRender{

	private final String SOURCEMAP;
	private ShaderProgram blurProgram;
	
	public EscapyBlurRenderer(String SOURCEMAP) {
		super();
		this.SOURCEMAP = SOURCEMAP;
	}
	public EscapyBlurRenderer(int id, String SOURCEMAP) {
		super(id);
		this.SOURCEMAP = SOURCEMAP;
	}
	public EscapyBlurRenderer(String SOURCEMAP, String VERTEX, String FRAGMENT) {
		super();
		this.SOURCEMAP = SOURCEMAP;
		this.initShaderProgram(VERTEX, FRAGMENT);
	}
	public EscapyBlurRenderer(int id, String SOURCEMAP, String VERTEX, String FRAGMENT) {
		super(id);
		this.SOURCEMAP = SOURCEMAP;
		this.initShaderProgram(VERTEX, FRAGMENT);
	}

	@Override
	public EscapyShaderRender initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.blurProgram = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		super.checkStatus(blurProgram);
		return this;
	}
	
	protected ShaderProgram initShader(Texture srcMap, ShaderProgram program, 
			float widht, float height, float dirX, float dirY) {
		
		program.begin();
		{
			srcMap.bind(0);
			super.batcher.setShader(program);
			program.setUniformi(SOURCEMAP, 0);
			program.setUniformf("u_fieldSize", widht, height);
			program.setUniformf("u_direction", dirX, dirY);
		}	program.end();
		return program;
	}
	
	public void renderBlured(Sprite reg, OrthographicCamera cam, float widht, float height, 
			TransVec dir) {
		renderBlured(reg, cam, widht, height, dir.x, dir.y);
	}
	public void renderBlured(Texture reg, OrthographicCamera cam, float widht, float height, 
			float x, float y, TransVec dir) {
		renderBlured(reg, cam, widht, height, x, y, dir.x, dir.y);
	}
	public void renderBlured(TextureRegion reg, OrthographicCamera cam, float widht, float height, 
			float x, float y, TransVec dir) {
		renderBlured(reg, cam, widht, height, x, y, dir.x, dir.y);
	}
	public void renderBlured(Sprite reg, OrthographicCamera cam, float widht, float height, 
			float dirX, float dirY) {
		this.blurProgram = initShader(reg.getTexture(), blurProgram, widht, height, dirX, dirY);
		super.drawSprite(reg, cam);
	}
	public void renderBlured(Texture reg, OrthographicCamera cam, float widht, float height, 
			float x, float y, float dirX, float dirY) {
		this.blurProgram = initShader(reg, blurProgram, widht, height, dirX, dirY);
		super.drawTexture(reg, cam, x, y);
	}
	public void renderBlured(TextureRegion reg, OrthographicCamera cam, float widht, float height, 
			float x, float y, float dirX, float dirY) {
		this.blurProgram = initShader(reg.getTexture(), blurProgram, widht, height, dirX, dirY);
		super.drawTextureRegion(reg, cam, x, y, widht, height);
	}
	
	@Override
	public String toString() {
		return "EscapyBlurRenderer_"+super.id;
	}

}
