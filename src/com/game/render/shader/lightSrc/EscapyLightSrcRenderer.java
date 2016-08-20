package com.game.render.shader.lightSrc;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.EscapyShaderRender;
import com.game.utils.translationVec.TransVec;

public class EscapyLightSrcRenderer extends EscapyShaderRender {

	private ShaderProgram colorizeShader;
	private final String LIGHTMAP;
	
	public EscapyLightSrcRenderer(String LIGHTMAP, String VERTEX, String FRAGMENT) {
		super();
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.LIGHTMAP = LIGHTMAP;
	}
	public EscapyLightSrcRenderer(int ID, String LIGHTMAP, String VERTEX, String FRAGMENT) {
		super(ID);
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.LIGHTMAP = LIGHTMAP;
	}
	public EscapyLightSrcRenderer(String LIGHTMAP) {
		super();
		this.LIGHTMAP = LIGHTMAP;
	}
	public EscapyLightSrcRenderer(int ID, String LIGHTMAP) {
		super(ID);
		this.LIGHTMAP = LIGHTMAP;
	}
	
	@Override
	public EscapyLightSrcRenderer initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.colorizeShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		super.checkStatus(colorizeShader);
		return this;
	}
	
	
	public void renderLightSrc(Sprite target, Sprite lMap, OrthographicCamera camera, 
			Color color, TransVec angles, TransVec fSize, float coeff, 
			float correction, TransVec radius, TransVec umbra) {
		
		this.colorizeShader = initShader(lMap.getTexture(), 
				this.colorizeShader, color, angles, fSize, coeff, correction, radius, umbra);
		super.drawSprite(target, camera);
	}
	
	public void renderLightSrc(Texture target, Texture lMap, OrthographicCamera camera, Color color,
			float x, float y, TransVec angles, TransVec fSize, float coeff, 
			float correction, TransVec radius, TransVec umbra) {
		
		this.colorizeShader = initShader(lMap, this.colorizeShader, color, angles, 
				fSize, coeff, correction, radius, umbra);
		super.drawTexture(target, camera, x, y);
	}
	
	public void renderLightSrc(TextureRegion target,TextureRegion lMap, OrthographicCamera camera, 
			Color color, float x, float y, float width, float height, 
			TransVec angles, TransVec fSize, float coeff,
			float correction, TransVec radius, TransVec umbra) {
		
		this.colorizeShader = initShader(lMap.getTexture(), 
				this.colorizeShader, color, angles, fSize, coeff, correction, radius, umbra);
		super.drawTextureRegion(target, camera, x, y, width, height);
	}
	
	
	protected ShaderProgram initShader(Texture lMap, ShaderProgram shader, 
			Color color, TransVec angles, TransVec fSize, float coeff, 
			float correction, TransVec radius, TransVec umbra) {
		shader.begin();
		{
			lMap.bind(0);
			super.batcher.setShader(shader);
			
			shader.setUniformi(LIGHTMAP, 0);
			shader.setUniformf("u_color", color.r, color.g, color.b);
			shader.setUniformf("u_angles", angles.x, angles.y);
			shader.setUniformf("u_fieldSize", fSize.x, fSize.y);
			shader.setUniformf("u_coeff", coeff);
			shader.setUniformf("u_angCorrect", correction);
			shader.setUniformf("u_radius", radius.x, radius.y);
			shader.setUniformf("u_umbra", umbra.x, umbra.y);
		}
		shader.end();
		return shader;
	}
	

	@Override
	public String toString() {
		return "EscapyColorizeRenderer_"+super.id;
	}

}
