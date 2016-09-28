package com.game.render.shader;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyShaderRender.
 */
public abstract class EscapyShaderRender {

		
	/** The id. */
	protected final int id;
	
	/** The batcher. */
	protected SpriteBatch batcher;
	
	
	/**
	 * Instantiates a new escapy shader render.
	 */
	public EscapyShaderRender() {
		this.id = generateID();
		this.batcher = new SpriteBatch();
	}
	
	/**
	 * Instantiates a new escapy shader render.
	 *
	 * @param id
	 *            the id
	 */
	public EscapyShaderRender(int id) {
		this.id = id;
		this.batcher = new SpriteBatch();
	}
	


	public abstract String toString();
	
	public abstract EscapyShaderRender initShaderProgram(String VERTEX, String FRAGMENT);
	
	/**
	 * Check status.
	 *
	 * @param program
	 *            the program
	 */
	protected void checkStatus(ShaderProgram program) {
		System.err.println(program.isCompiled() ? "COMPILED: "+this.toString() : "ERROR: "+this.toString()+"\n"+program.getLog()+"\n");
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
		batcher.flush();
		camera.update(); //FIXME
		this.batcher.setProjectionMatrix(camera.combined);
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
		batcher.flush();
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
		batcher.flush();
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
	 * @return the escapy shader render
	 */
	public EscapyShaderRender setSpriteBatch(SpriteBatch batcher) {
		this.batcher = batcher;
		return this;
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
	
	protected String removeFRAG(String url) {
		//.frag
		StringBuffer strb = new StringBuffer(url);
		if (strb.charAt(strb.length()-5) == '.')
			strb.delete(strb.length()-5, strb.length());
		return strb.toString();
	}
	
}
