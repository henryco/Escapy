package com.game.render.shader.colorize;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.game.render.shader.EscapyShaderRender;

public class EscapyColorizeRenderer extends EscapyShaderRender {

	private ShaderProgram colorizeShader;
	private final String TARGETMAP, LIGHTMAP;
	
	public EscapyColorizeRenderer(String TARGETMAP, String LIGHTMAP, String VERTEX, String FRAGMENT) {
		super();
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.TARGETMAP = TARGETMAP;
		this.LIGHTMAP = LIGHTMAP;
	}
	public EscapyColorizeRenderer(int ID, String TARGETMAP, String LIGHTMAP, String VERTEX, String FRAGMENT) {
		super(ID);
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.TARGETMAP = TARGETMAP;
		this.LIGHTMAP = LIGHTMAP;
	}
	public EscapyColorizeRenderer(String TARGETMAP, String LIGHTMAP) {
		super();
		this.TARGETMAP = TARGETMAP;
		this.LIGHTMAP = LIGHTMAP;
	}
	public EscapyColorizeRenderer(int ID, String TARGETMAP, String LIGHTMAP) {
		super(ID);
		this.TARGETMAP = TARGETMAP;
		this.LIGHTMAP = LIGHTMAP;
	}
	
	@Override
	public EscapyColorizeRenderer initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.colorizeShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		super.checkStatus(colorizeShader);
		return this;
	}
	
	
	public void renderColorized(Sprite target, Sprite lMap, OrthographicCamera camera, 
			float r, float g, float b, Vector2 center, Vector2 fSize, float coeff) {
		
		this.colorizeShader = initShader(target.getTexture(), lMap.getTexture(), 
				this.colorizeShader, r, g, b, center, fSize, coeff);
		super.drawSprite(target, camera);
	}
	
	public void renderColorized(Texture target, Texture lMap, OrthographicCamera camera, 
			float r, float g, float b,float x, float y, Vector2 center, Vector2 fSize, float coeff) {
		
		this.colorizeShader = initShader(target, lMap, this.colorizeShader, r, g, b, center, fSize, coeff);
		super.drawTexture(target, camera, x, y);
	}
	
	public void renderColorized(TextureRegion target,TextureRegion lMap, OrthographicCamera camera, 
			float r, float g, float b, float x, float y, float width, 
			float height, Vector2 center, Vector2 fSize, float coeff) {
		
		this.colorizeShader = initShader(target.getTexture(), lMap.getTexture(), 
				this.colorizeShader, r, g, b, center, fSize, coeff);
		super.drawTextureRegion(target, camera, x, y, width, height);
	}
	
	
	protected ShaderProgram initShader(Texture target, Texture lMap, ShaderProgram shader, 
			float r, float g, float b, Vector2 center, Vector2 fSize, float coeff) {
		shader.begin();
		{
			lMap.bind(1);
			target.bind(0);
			super.batcher.setShader(shader);
			
			shader.setUniformi(TARGETMAP, 0);
			shader.setUniformf("colorR", r);
			shader.setUniformf("colorG", g);
			shader.setUniformf("colorB", b);
			
			shader.setUniformi(LIGHTMAP, 1);
			shader.setUniformf("u_lightCenter", center);
			shader.setUniformf("u_fieldSize", fSize);
			shader.setUniformf("u_coeff", coeff);
		}
		shader.end();
		
		return shader;
	}
	

	@Override
	public String toString() {
		return "EscapyColorizeRenderer_"+super.id;
	}

}
