package com.game.render.shader.colorize;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.EscapyShaderRender;

public class EscapyColorizeRenderer extends EscapyShaderRender {

	private ShaderProgram colorizeShader;
	private final String TARGETMAP;
	
	public EscapyColorizeRenderer(String TARGETMAP, String VERTEX, String FRAGMENT) {
		super();
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.TARGETMAP = TARGETMAP;
	}
	public EscapyColorizeRenderer(int ID, String TARGETMAP, String VERTEX, String FRAGMENT) {
		super(ID);
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.TARGETMAP = TARGETMAP;
	}
	public EscapyColorizeRenderer(String TARGETMAP) {
		super();
		this.TARGETMAP = TARGETMAP;
	}
	public EscapyColorizeRenderer(int ID, String TARGETMAP) {
		super(ID);
		this.TARGETMAP = TARGETMAP;
	}
	
	public EscapyColorizeRenderer initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.colorizeShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		super.checkStatus(colorizeShader);
		return this;
	}
	
	
	public void renderColorized(Sprite target, OrthographicCamera camera, float r, float g, float b) {
		this.colorizeShader = initShader(target.getTexture(), this.colorizeShader, r, g, b);
		super.drawSprite(target, camera);
	}
	public void renderColorized(Texture target, OrthographicCamera camera, float r, float g, float b,
			float x, float y) {
		this.colorizeShader = initShader(target, this.colorizeShader, r, g, b);
		super.drawTexture(target, camera, x, y);
	}
	public void renderColorized(TextureRegion target, OrthographicCamera camera, float r, float g, float b, 
			float x, float y, float width, float height) {
		this.colorizeShader = initShader(target.getTexture(), this.colorizeShader, r, g, b);
		super.drawTextureRegion(target, camera, x, y, width, height);
	}
	
	
	private ShaderProgram initShader(Texture target, ShaderProgram shader, float r, float g, float b) {
		shader.begin();
		{
			target.bind(0);
			super.batcher.setShader(shader);
			shader.setUniformi(TARGETMAP, 0);
			shader.setUniformf("colorR", r);
			shader.setUniformf("colorG", g);
			shader.setUniformf("colorB", b);
		}
		shader.end();
		
		return shader;
	}
	

	@Override
	public String toString() {
		return "EscapyColorizeRenderer_"+super.id;
	}

}
