package com.game.render.shader.shadow;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.EscapyShaderRender;
import com.game.utils.translationVec.TransVec;

public class EscapyShadowMapRenderer extends EscapyShaderRender {

	protected ShaderProgram shadowProgram;
	private final String SOURCEMAP;

	public EscapyShadowMapRenderer(String SOURCEMAP) {
		super();
		this.SOURCEMAP = SOURCEMAP;
	}

	public EscapyShadowMapRenderer(int id, String SOURCEMAP) {
		super(id);
		this.SOURCEMAP = SOURCEMAP;
	}
	public EscapyShadowMapRenderer(String SOURCEMAP, String VERTEX, String FRAGMENT) {
		super();
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.SOURCEMAP = SOURCEMAP;
	}

	public EscapyShadowMapRenderer(int id, String SOURCEMAP, String VERTEX, String FRAGMENT) {
		super(id);
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.SOURCEMAP = SOURCEMAP;
	}
	
	@Override
	public EscapyShaderRender initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.shadowProgram = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		super.checkStatus(shadowProgram);
		return this;
	}

	protected ShaderProgram initShader(Texture sourceMap, float lwidht, float lheight,
			TransVec angles, float correct, ShaderProgram shader) {
		
		shader.begin();
		{
			sourceMap.bind(0);
			super.batcher.setShader(shader);
			shader.setUniformi(SOURCEMAP, 0);
			shader.setUniformf("resolution", lwidht, lheight);
			shader.setUniformf("u_angles", angles.x, angles.y);
			shader.setUniformf("u_angCorrect", correct);
		}	shader.end();
		return shader;
	}
	
	public void renderShadow(TextureRegion reg, OrthographicCamera camera, 
			float resX, float resY, float x, float y, float widht, float height, 
			TransVec lightAngles, float correct) {
		
		this.shadowProgram = initShader(reg.getTexture(), resX, resY,
				lightAngles, correct, shadowProgram);
		super.drawTextureRegion(reg, camera, x, y, widht, height);
	}
	public void renderShadow(Texture reg, OrthographicCamera camera, float resX,
			float resY, float x, float y, TransVec lightAngles, float correct) {
		this.shadowProgram = initShader(reg, resX, resY, lightAngles, correct, shadowProgram);
		super.drawTexture(reg, camera, x, y);
	}
	public void renderShadow(Sprite reg, OrthographicCamera camera, float regX, float regY,
			TransVec lightAngles, float correct) {
		this.shadowProgram = initShader(reg.getTexture(), regX, regY,
				lightAngles, correct, shadowProgram);
		super.drawSprite(reg, camera);
	}
	
	
	@Override
	public String toString() {
		return "EscapyShadowMapRenderer_"+super.id;
	}

	@Override
	public EscapyShaderRender setCustomUniforms(boolean uniforms) {
		return null;
	}
}
