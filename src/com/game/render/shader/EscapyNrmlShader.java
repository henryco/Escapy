package com.game.render.shader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.render.fbo.psProcess.lights.SimpleLight;

public class EscapyNrmlShader extends EscapyStdShaderRenderer {

	private static final String VERTEX = "shaders\\nrml\\nrml.vert";
	private static final String FRAGMENT = "shaders\\nrml\\nrml.frag";
	private ShaderProgram nrmlShader;

	
	public EscapyNrmlShader() {
		
		super();
		
		this.nrmlShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		System.err.println(nrmlShader.isCompiled() ? "compiled: EscapyNrmlShader" : nrmlShader.getLog());
		return;
	}

	public Sprite renderNrmLight(Sprite colorMap, Sprite normalMap, OrthographicCamera camera, Vector2 position,
			Vector2 dim, Vector3 color, float intencity, float distance) {
		this.nrmlShader = initShader(colorMap.getTexture(), normalMap.getTexture(), this.nrmlShader, position, dim,
				color, intencity, distance);

		super.drawSprite(colorMap, camera);
		return colorMap;
	}

	public Sprite renderNrmLight(Sprite colorMap, Sprite normalMap, OrthographicCamera camera, SimpleLight simpLight) {
		this.nrmlShader = initShader(colorMap.getTexture(), normalMap.getTexture(), this.nrmlShader,
				simpLight.getPosition(), simpLight.getDimension(), simpLight.getColor(), simpLight.getIntencity(),
				simpLight.getDistance());

		super.drawSprite(colorMap, camera);
		return colorMap;
	}

	public Texture renderNrmLight(Texture colorMap, Texture normalMap, float x, float y, OrthographicCamera camera,
			SimpleLight simpLight) {
		this.nrmlShader = initShader(colorMap, normalMap, this.nrmlShader, simpLight.getPosition(),
				simpLight.getDimension(), simpLight.getColor(), simpLight.getIntencity(), simpLight.getDistance());

		super.drawTexture(colorMap, camera, x, y);
		return colorMap;
	}

	public Texture renderNrmLight(Texture colorMap, Texture normalMap, float x, float y, OrthographicCamera camera,
			Vector2 position, Vector2 dim, Vector3 color, float intencity, float distance) {
		this.nrmlShader = initShader(colorMap, normalMap, this.nrmlShader, position, dim, color, intencity, distance);

		super.drawTexture(colorMap, camera, x, y);
		return colorMap;
	}

	public TextureRegion renderNrmLight(TextureRegion colorMap, TextureRegion normalMap, float x, float y, float width,
			float height, OrthographicCamera camera, SimpleLight simpLight) {
		this.nrmlShader = initShader(colorMap.getTexture(), normalMap.getTexture(), this.nrmlShader,
				simpLight.getPosition(), simpLight.getDimension(), simpLight.getColor(), simpLight.getIntencity(),
				simpLight.getDistance());

		super.drawTextureRegion(colorMap, camera, x, y, width, height);
		return colorMap;
	}

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

}
