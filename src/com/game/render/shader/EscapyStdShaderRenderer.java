package com.game.render.shader;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyStdShaderRenderer.
 */
public class EscapyStdShaderRenderer {

	/** The batcher. */
	protected SpriteBatch batcher;
	
	/** The std program. */
	protected final ShaderProgram stdProgram;
	
	/** The id. */
	protected final int id;
	
	/**
	 * Instantiates a new escapy std shader renderer.
	 */
	public EscapyStdShaderRenderer() {
		ShaderProgram.pedantic = false;
		this.batcher = new SpriteBatch();
		this.stdProgram = SpriteBatch.createDefaultShader();
		this.id = generateID();
	}
	
	/**
	 * Instantiates a new escapy std shader renderer.
	 *
	 * @param id
	 *            the id
	 */
	public EscapyStdShaderRenderer(int id) {
		
		ShaderProgram.pedantic = false;
		this.batcher = new SpriteBatch();
		this.stdProgram = SpriteBatch.createDefaultShader();
		this.id = id;
	}
	
	/**
	 * Generate ID.
	 *
	 * @return the int
	 */
	protected int generateID() {
		return this.hashCode();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Draw sprite.
	 *
	 * @param sprite
	 *            the sprite
	 * @param camera
	 *            the camera
	 */
	public void drawSprite(Sprite sprite, OrthographicCamera camera) {
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		sprite.draw(batcher);
		this.batcher.end();
	}

	/**
	 * Draw texture.
	 *
	 * @param texture
	 *            the texture
	 * @param camera
	 *            the camera
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void drawTexture(Texture texture, OrthographicCamera camera, float x, float y) {
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		this.batcher.draw(texture, x, y);
		this.batcher.end();
	}

	/**
	 * Draw texture region.
	 *
	 * @param region
	 *            the region
	 * @param camera
	 *            the camera
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param widht
	 *            the widht
	 * @param height
	 *            the height
	 */
	public void drawTextureRegion(TextureRegion region, OrthographicCamera camera, float x, float y, float widht,
			float height) {
		this.batcher.setProjectionMatrix(camera.combined);
		camera.update();
		this.batcher.begin();
		this.batcher.draw(region, x, y, widht, height);
		this.batcher.end();
	}
	
	
	/**
	 * Sets the sprite batch.
	 *
	 * @param batcher
	 *            the batcher
	 * @return the escapy std shader renderer
	 */
	public EscapyStdShaderRenderer setSpriteBatch(SpriteBatch batcher) {
		this.batcher = batcher;
		return this;
	}

	/**
	 * Gets the std program.
	 *
	 * @return the std program
	 */
	public ShaderProgram getStdProgram() {
		return stdProgram;
	}
	
}
