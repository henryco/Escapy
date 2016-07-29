package com.game.render.shader.volumeLight;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.game.render.shader.EscapyShaderRender;

public class EscapyVolumeRenderer extends EscapyShaderRender {

	private ShaderProgram volShader;
	private static final String colorMap = "colorMap", lightMap = "lightMap", normalMap = "normalMap";
	private static final String fieldSize = "fieldSize", ambientIntensity = "ambientIntensity";
	private static final String lightIntensity = "lightIntensity";
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
	
	
	public void renderVolumeLights(float x, float y, Texture cMap, Texture nMap, Texture lMap, Vector2 dim, 
			float amIntensity, float ligIntensity, OrthographicCamera camera) {
		this.volShader = initShader(cMap, nMap, lMap, dim, amIntensity, ligIntensity, volShader);
		super.drawTexture(cMap, camera, x, y);
	}
	
	
	public void renderVolumeLights(Sprite cMap, Sprite nMap, Sprite lMap, Vector2 dim, 
			float amIntensity, float ligIntensity, OrthographicCamera camera) {
		this.volShader = initShader(cMap.getTexture(), nMap.getTexture(), lMap.getTexture(), 
				dim, amIntensity, ligIntensity, volShader);
		super.drawSprite(cMap, camera);
	}
	
	public void renderVolumeLights(float x, float y, TextureRegion cMap, TextureRegion nMap, TextureRegion lMap, 
			Vector2 dim, float amIntensity, float ligIntensity, OrthographicCamera camera) {
		
		this.volShader = initShader(cMap.getTexture(), nMap.getTexture(), lMap.getTexture(), 
				dim, amIntensity, ligIntensity, volShader);
		super.drawTextureRegion(cMap, camera, x, y, dim.x, dim.y);
	}
	
	private ShaderProgram initShader(Texture cMap, Texture nMap, Texture lMap, Vector2 dim, 
			float amIntensity, float ligIntensity, ShaderProgram shader) {
		shader.begin();
		{
			lMap.bind(2);
			nMap.bind(1);
			cMap.bind(0);
			super.batcher.setShader(shader);
			shader.setUniformi(lightMap, 2);
			shader.setUniformi(normalMap, 1);
			shader.setUniformi(colorMap, 0);
			shader.setUniformf(fieldSize, dim);
			shader.setUniformf(ambientIntensity, amIntensity);
			shader.setUniformf(lightIntensity, ligIntensity);	
		}
		shader.end();
		return shader;
	}
	
	@Override
	public String toString() {
		return "EscapyVolumeRenderer"+"_"+this.FRAGMENT_NAME+"_"+super.id;
	}




}
