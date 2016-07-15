package com.game.render.shader.blend;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.EscapyShaderRender;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyBlendRenderer.
 */
public class EscapyBlendRenderer extends EscapyShaderRender {

	private ShaderProgram blendShader;
	private final String targetMap, blendMap;
	
	/**
	 * Instantiates a new escapy blend renderer.
	 *
	 * @param VERTEX
	 *            the vertex
	 * @param FRAGMENT
	 *            the fragment
	 * @param targetMap
	 *            the target map
	 * @param blendMap
	 *            the blend map
	 */
	public EscapyBlendRenderer(String VERTEX, String FRAGMENT, String targetMap, String blendMap) {
		super();
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.targetMap = targetMap;
		this.blendMap = blendMap;
	}
	
	/**
	 * Instantiates a new escapy blend renderer.
	 *
	 * @param ID
	 *            the id
	 * @param VERTEX
	 *            the vertex
	 * @param FRAGMENT
	 *            the fragment
	 * @param targetMap
	 *            the target map
	 * @param blendMap
	 *            the blend map
	 */
	public EscapyBlendRenderer(int ID, String VERTEX, String FRAGMENT, String targetMap, String blendMap) {
		super(ID);
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.targetMap = targetMap;
		this.blendMap = blendMap;
	}
	
	/**
	 * Instantiates a new escapy blend renderer.
	 *
	 * @param targetMap
	 *            the target map
	 * @param blendMap
	 *            the blend map
	 */
	public EscapyBlendRenderer(String targetMap, String blendMap) {
		super();
		this.blendShader = SpriteBatch.createDefaultShader();
		this.targetMap = targetMap;
		this.blendMap = blendMap;
	}
	
	/**
	 * Instantiates a new escapy blend renderer.
	 *
	 * @param ID
	 *            the id
	 * @param targetMap
	 *            the target map
	 * @param blendMap
	 *            the blend map
	 */
	public EscapyBlendRenderer(int ID, String targetMap, String blendMap) {
		super(ID);
		this.blendShader = SpriteBatch.createDefaultShader();
		this.targetMap = targetMap;
		this.blendMap = blendMap;
	}
	
	/**
	 * Inits the shader program.
	 *
	 * @param VERTEX
	 *            the vertex
	 * @param FRAGMENT
	 *            the fragment
	 * @return the escapy blend renderer
	 */
	public EscapyBlendRenderer initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.blendShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		super.checkStatus(blendShader);
		return this;
	}
	
	
	/**
	 * Render blended.
	 *
	 * @param target
	 *            the target
	 * @param blend
	 *            the blend
	 * @param camera
	 *            the camera
	 * @return the sprite
	 */
	public Sprite renderBlended(Sprite target, Sprite blend, OrthographicCamera camera) {
		this.blendShader = initShader(target.getTexture(), blend.getTexture(), this.blendShader);
	
		super.drawSprite(target, camera);
		return target;
	}
	
	/**
	 * Render blended.
	 *
	 * @param target
	 *            the target
	 * @param blend
	 *            the blend
	 * @param x
	 *            the start X point
	 * @param y
	 *            the start Y point
	 * @param camera
	 *            the camera
	 * @return the texture
	 */
	public Texture renderBlended(Texture target, Texture blend, float x, float y, OrthographicCamera camera) {
		this.blendShader = initShader(target, blend, this.blendShader);

		super.drawTexture(target, camera, x, y);
		return target;
	}
	
	/**
	 * Render blended.
	 *
	 * @param target
	 *            the target
	 * @param blend
	 *            the blend
	 * @param x
	 *            the start X point
	 * @param y
	 *            the start Y point
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param camera
	 *            the camera
	 * @return the texture region
	 */
	public TextureRegion renderBlended(TextureRegion target, TextureRegion blend, float x, float y, float width,
			float height, OrthographicCamera camera) {
		this.blendShader = initShader(target.getTexture(), blend.getTexture(), this.blendShader);

		super.drawTextureRegion(target, camera, x, y, width, height);
		return target;
	}

	private ShaderProgram initShader(Texture target, Texture blend, ShaderProgram shader) {
		shader.begin();
		{
			blend.bind(1);
			target.bind(0);
			super.batcher.setShader(shader);
			shader.setUniformi(this.blendMap, 1);
			shader.setUniformi(this.targetMap, 0);
		}
		shader.end();
		return shader;
	}

	/* (non-Javadoc)
	 * @see com.game.render.shader.EscapyShaderRender#toString()
	 */
	@Override
	public String toString() {
		return "EscapyBlendShader_"+super.id;
	}
	
	
}
