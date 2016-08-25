package com.game.render.shader.shadow.userState;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.shadow.EscapyShadowMapRenderer;
import com.game.utils.translationVec.TransVec;

public class EscapyStdShadowMapRenderer extends EscapyShadowMapRenderer {

	public static final String VERTEX = "data/shaders/shadows/creator/shadows.vert";
	public static final String FRAGMENT = "data/shaders/shadows/creator/shadows.frag";
	public static final String SOURCEMAP = "u_texture";
	
	public EscapyStdShadowMapRenderer() {
		super(SOURCEMAP, VERTEX, FRAGMENT);
	}
	public EscapyStdShadowMapRenderer(int id) {
		super(id, SOURCEMAP, VERTEX, FRAGMENT);
	}
	
	@Override
	protected ShaderProgram initShader(Texture sourceMap, float lwidht, float lheight,
			TransVec angles, float correct, ShaderProgram shader) {
		
		shader.begin();
		{
			sourceMap.bind(0);
			super.batcher.setShader(shader);
			shader.setUniformi(SOURCEMAP, 0);
			shader.setUniformf("resolution", lwidht, lheight);
		}	shader.end();
		return shader;
	}

	public void renderShadowMap(TextureRegion reg, OrthographicCamera camera, 
			float resX, float resY, float x, float y, float widht, float height) {
		
		this.shadowProgram = initShader(reg.getTexture(), resX, resY,
				null, 0, shadowProgram);
		super.drawTextureRegion(reg, camera, x, y, widht, height);
	}
	public void renderShadowMap(Texture reg, OrthographicCamera camera, float resX,
			float resY, float x, float y) {
		this.shadowProgram = initShader(reg, resX, resY, null, 0, shadowProgram);
		super.drawTexture(reg, camera, x, y);
	}
	public void renderShadowMap(Sprite reg, OrthographicCamera camera, float regX, float regY) {
		this.shadowProgram = initShader(reg.getTexture(), regX, regY,
				null, 0, shadowProgram);
		super.drawSprite(reg, camera);
	}
	@Override
	public String toString() {
		return "EscapyStdShadowMapRenderer_"+super.id;
	}
}
