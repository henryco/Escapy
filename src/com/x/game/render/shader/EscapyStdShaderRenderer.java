package com.x.game.render.shader;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class EscapyStdShaderRenderer {

	protected SpriteBatch batcher;
	protected ShaderProgram stdProgram;
	
	public EscapyStdShaderRenderer() {
		
		ShaderProgram.pedantic = false;
		this.batcher = new SpriteBatch();
		this.stdProgram = SpriteBatch.createDefaultShader();
	
	}
	
	public void drawSprite(Sprite sprite, OrthographicCamera camera) {
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		sprite.draw(batcher);
		this.batcher.end();
	}

	public void drawTexture(Texture texture, OrthographicCamera camera, float x, float y) {
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		this.batcher.draw(texture, x, y);
		this.batcher.end();
	}

	public void drawTextureRegion(TextureRegion region, OrthographicCamera camera, float x, float y, float widht,
			float height) {
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		this.batcher.draw(region, x, y, widht, height);
		this.batcher.end();
	}
	
	
	
	
	public EscapyStdShaderRenderer setSpriteBatch(SpriteBatch batcher) {
		this.batcher = batcher;
		return this;
	}

	public ShaderProgram getStdProgram() {
		return stdProgram;
	}
}
