package com.game.render.shader.volumeLight;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.EscapyShaderRender;
import com.game.utils.translationVec.TransVec;

public class EscapyVolumeRenderer extends EscapyShaderRender {

	private ShaderProgram volShader;
	private static final String colorMap = "colorMap", maskMap = "maskMap", normalMap = "normalMap",
								fieldSize = "fieldSize", ambientIntensity = "ambientIntensity",
								directIntensity = "directIntensity", shadowIntensity = "shadowIntensity",
								spriteSize = "spriteSize", height = "height", threshold = "threshold";
	
	private String FRAGMENT_NAME = "_";
	
	public EscapyVolumeRenderer(int ID, String VERTEX, String FRAGMENT) {
		super(ID);
		this.initShaderProgram(VERTEX, FRAGMENT);
	}
	
	public EscapyVolumeRenderer(String VERTEX, String FRAGMENT) {
		super();
		this.initShaderProgram(VERTEX, FRAGMENT);
	}
	
	

	@Override
	public EscapyShaderRender initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.volShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		this.FRAGMENT_NAME = super.removeFRAG(FRAGMENT);
		super.checkStatus(volShader);
		return this;
	}
	

	
	public void renderVolumeLights(Texture color, float x, float y, Texture masked, Texture normal, TransVec dim,
			float amIntensity, float dirIntensity, float shdIntensity, float sprtSize, float lum, float threshold, OrthographicCamera camera) {
		
		this.volShader = initShader(
				color, masked, normal, dim, amIntensity, dirIntensity, shdIntensity, sprtSize, lum, threshold, volShader);
		super.drawTexture(color, camera, x, y);
	}
	
	
	public void renderVolumeLights(Sprite color, Sprite masked, Sprite normal, TransVec dim,
			float amIntensity, float dirIntensity, float shdIntensity, float sprtSize, float lum, float threshold, OrthographicCamera camera) {
		this.volShader = initShader(
				color.getTexture(), masked.getTexture(), normal.getTexture(),
				dim, amIntensity, dirIntensity, shdIntensity, sprtSize, lum, threshold, volShader);
		super.drawSprite(color, camera);
	}
	
	public void renderVolumeLights(TextureRegion color, float x, float y, TextureRegion masked, TextureRegion normal, TransVec dim,
								   float amIntensity, float dirIntensity, float shdIntensity, float sprtSize, float lum, float threshold, OrthographicCamera camera) {
		
		this.volShader = initShader(
				color.getTexture(), masked.getTexture(), normal.getTexture(),
				dim, amIntensity, dirIntensity, shdIntensity, sprtSize, lum, threshold, volShader);
		super.drawTextureRegion(color, camera, x, y, dim.x, dim.y);
	}
	
	
	private ShaderProgram initShader(Texture colorMapTex, Texture maskMapTex, Texture normalMapTex, TransVec dim,
			float amIntensity, float dirIntensity, float shadIntensity, float sprtSize, float lum, float thresh, ShaderProgram shader) {
		shader.begin();
		{
			maskMapTex.bind(2);
			normalMapTex.bind(1);
			colorMapTex.bind(0);
			super.batcher.setShader(shader);

			shader.setUniformi(maskMap, 2);
			shader.setUniformi(normalMap, 1);
			shader.setUniformi(colorMap, 0);

			shader.setUniformf(fieldSize, dim.x, dim.y);
			shader.setUniformf(ambientIntensity, amIntensity);
			shader.setUniformf(directIntensity, dirIntensity);
			shader.setUniformf(shadowIntensity, shadIntensity);
			shader.setUniformf(spriteSize, sprtSize);
			shader.setUniformf(height, lum);
			shader.setUniformf(threshold, thresh);
		}
		shader.end();
		return shader;
	}
	
	@Override
	public String toString() {
		return "EscapyVolumeRenderer"+"_"+this.FRAGMENT_NAME+"_"+super.id;
	}

	@Override
	public EscapyShaderRender setCustomUniforms(boolean uniforms) {
		return null;
	}


}
