package com.game.render.shader.volumeLight;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.game.render.shader.EscapyShaderRender;

public class EscapyVolumeRenderer extends EscapyShaderRender {

	private ShaderProgram volShader;
	private final String colorMap, lightMap, normalMap;
	private String FRAGMENT_NAME = "_";
	
	public EscapyVolumeRenderer(int ID, String VERTEX, String FRAGMENT,
			String colorMap, String lightMap, String normalMap) {
		super(ID);
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.colorMap = colorMap;
		this.lightMap = lightMap;
		this.normalMap = normalMap;
	}
	
	public EscapyVolumeRenderer(String VERTEX, String FRAGMENT,
			String colorMap, String lightMap, String normalMap) {
		super();
		this.initShaderProgram(VERTEX, FRAGMENT);
		this.colorMap = colorMap;
		this.lightMap = lightMap;
		this.normalMap = normalMap;
	}
	
	public EscapyVolumeRenderer(int ID, String colorMap, String lightMap, String normalMap) {
		super(ID);
		this.volShader = SpriteBatch.createDefaultShader();
		this.colorMap = colorMap;
		this.lightMap = lightMap;
		this.normalMap = normalMap;
	}
	
	public EscapyVolumeRenderer(String colorMap, String lightMap, String normalMap) {
		super();
		this.volShader = SpriteBatch.createDefaultShader();
		this.colorMap = colorMap;
		this.lightMap = lightMap;
		this.normalMap = normalMap;
	}
	

	@Override
	public EscapyShaderRender initShaderProgram(String VERTEX, String FRAGMENT) {
		ShaderProgram.pedantic = false;
		this.volShader = new ShaderProgram(new FileHandle(VERTEX), new FileHandle(FRAGMENT));
		this.FRAGMENT_NAME = super.removeFRAG(FRAGMENT);
		super.checkStatus(volShader);
		return this;
	}
	
	
	
	
	
	
	@Override
	public String toString() {
		return "EscapyVolumeRenderer"+"_"+this.FRAGMENT_NAME+"_"+super.id;
	}




}
