package com.game.render.shader.blend.userState;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.render.fbo.psProcess.lights.SimpleLight;
import com.game.render.shader.EscapyShaderRender;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyStdNrmlShader.
 */
public class EscapyStdNrmlShader extends EscapyShaderRender {

	private static final String VERTEX = "shaders\\nrml\\nrml.vert";
	private static final String FRAGMENT = "shaders\\nrml\\nrml.frag";
	private ShaderProgram nrmlShader;

	
	/**
	 * Instantiates a new escapy nrml shader.
	 */
	public EscapyStdNrmlShader() {
		super();
		this.startInit();
		return;
	}
	
	/**
	 * Instantiates a new escapy std nrml shader.
	 *
	 * @param ID
	 *            the id
	 */
	public EscapyStdNrmlShader(int ID) {
		super(ID);	
		this.startInit();
		return;
	}
	
	private void startInit() {
		this.nrmlShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		//System.err.println(nrmlShader.isCompiled() ? "COMPILED: "+this.toString() : "ERROR: "+this.toString()+"\n"+nrmlShader.getLog());
		super.checkStatus(nrmlShader);
	}
	
	

	/**
	 * Render nrm light.
	 *
	 * @param colorMap
	 *            the color map
	 * @param normalMap
	 *            the normal map
	 * @param camera
	 *            the camera
	 * @param position
	 *            the position
	 * @param dim
	 *            the dim
	 * @param color
	 *            the color
	 * @param intencity
	 *            the intencity
	 * @param distance
	 *            the distance
	 * @return the sprite
	 */
	public Sprite renderNrmLight(Sprite colorMap, Sprite normalMap, OrthographicCamera camera, Vector2 position,
			Vector2 dim, Vector3 color, float intencity, float distance) {
		this.nrmlShader = initShader(colorMap.getTexture(), normalMap.getTexture(), this.nrmlShader, position, dim,
				color, intencity, distance);
	
		super.drawSprite(colorMap, camera);
		return colorMap;
	}

	/**
	 * Render nrm light.
	 *
	 * @param colorMap
	 *            the color map
	 * @param normalMap
	 *            the normal map
	 * @param camera
	 *            the camera
	 * @param simpLight
	 *            the simp light
	 * @return the sprite
	 */
	public Sprite renderNrmLight(Sprite colorMap, Sprite normalMap, OrthographicCamera camera, SimpleLight simpLight) {
		this.nrmlShader = initShader(colorMap.getTexture(), normalMap.getTexture(), this.nrmlShader,
				simpLight.getPosition(), simpLight.getDimension(), simpLight.getColor(), simpLight.getIntencity(),
				simpLight.getDistance());

		super.drawSprite(colorMap, camera);
		return colorMap;
	}

	/**
	 * Render nrm light.
	 *
	 * @param colorMap
	 *            the color map
	 * @param normalMap
	 *            the normal map
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param camera
	 *            the camera
	 * @param simpLight
	 *            the simp light
	 * @return the texture
	 */
	public Texture renderNrmLight(Texture colorMap, Texture normalMap, float x, float y, OrthographicCamera camera,
			SimpleLight simpLight) {
		this.nrmlShader = initShader(colorMap, normalMap, this.nrmlShader, simpLight.getPosition(),
				simpLight.getDimension(), simpLight.getColor(), simpLight.getIntencity(), simpLight.getDistance());

		super.drawTexture(colorMap, camera, x, y);
		return colorMap;
	}

	/**
	 * Render nrm light.
	 *
	 * @param colorMap
	 *            the color map
	 * @param normalMap
	 *            the normal map
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param camera
	 *            the camera
	 * @param position
	 *            the position
	 * @param dim
	 *            the dim
	 * @param color
	 *            the color
	 * @param intencity
	 *            the intencity
	 * @param distance
	 *            the distance
	 * @return the texture
	 */
	public Texture renderNrmLight(Texture colorMap, Texture normalMap, float x, float y, OrthographicCamera camera,
			Vector2 position, Vector2 dim, Vector3 color, float intencity, float distance) {
		this.nrmlShader = initShader(colorMap, normalMap, this.nrmlShader, position, dim, color, intencity, distance);

		super.drawTexture(colorMap, camera, x, y);
		return colorMap;
	}

	/**
	 * Render nrm light.
	 *
	 * @param colorMap
	 *            the color map
	 * @param normalMap
	 *            the normal map
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param camera
	 *            the camera
	 * @param simpLight
	 *            the simp light
	 * @return the texture region
	 */
	public TextureRegion renderNrmLight(TextureRegion colorMap, TextureRegion normalMap, float x, float y, float width,
			float height, OrthographicCamera camera, SimpleLight simpLight) {
		this.nrmlShader = initShader(colorMap.getTexture(), normalMap.getTexture(), this.nrmlShader,
				simpLight.getPosition(), simpLight.getDimension(), simpLight.getColor(), simpLight.getIntencity(),
				simpLight.getDistance());

		super.drawTextureRegion(colorMap, camera, x, y, width, height);
		return colorMap;
	}

	/**
	 * Render nrm light.
	 *
	 * @param colorMap
	 *            the color map
	 * @param normalMap
	 *            the normal map
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param camera
	 *            the camera
	 * @param position
	 *            the position
	 * @param dim
	 *            the dim
	 * @param color
	 *            the color
	 * @param intencity
	 *            the intencity
	 * @param distance
	 *            the distance
	 * @return the texture region
	 */
	public TextureRegion renderNrmLight(TextureRegion colorMap, TextureRegion normalMap, float x, float y, float width,
			float height, OrthographicCamera camera, Vector2 position, Vector2 dim, Vector3 color, float intencity,
			float distance) {
		this.nrmlShader = initShader(colorMap.getTexture(), normalMap.getTexture(), this.nrmlShader, position, dim,
				color, intencity, distance);

		super.drawTextureRegion(colorMap, camera, x, y, width, height);
		return colorMap;
	}

	private ShaderProgram initShader(Texture colorMap, Texture normalMap, ShaderProgram shader, Vector2 position,
			Vector2 dim, Vector3 color, float intencity, float distance) {
		shader.begin();
		{
			normalMap.bind(1);
			colorMap.bind(0);

			super.batcher.setShader(shader);

			shader.setUniformi("normalMap", 1);
			shader.setUniformi("color_map", 0);
			shader.setUniformf("light", position);
			shader.setUniformf("screen", dim);
			shader.setUniformf("dist", distance);
			shader.setUniformf("io_lightColor", color.x, color.y, color.z, intencity);
		}
		shader.end();

		return shader;
	}

	/* (non-Javadoc)
	 * @see com.game.render.shader.EscapyShaderRender#toString()
	 */
	@Override
	public String toString() {
		return "EscapyStdNrmlShader_"+super.id;
	}

}
