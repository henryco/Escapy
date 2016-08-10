package com.game.render.fbo.psProcess.lights.stdLS.userState;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.utils.translationVec.TransVec;

public class EscapyShadedLight extends EscapyStdLight {

	
	public EscapyShadedLight(EscapyFBO lightMap) {
		super(lightMap);
	}
	public EscapyShadedLight(float size, String texure, float x, float y) {
		super(size, texure, x, y);
	}
	public EscapyShadedLight(float size, String texure) {
		super(size, texure);
	}
	public EscapyShadedLight(int id, EscapyFBO lightMap) {
		super(id, lightMap);
	}
	public EscapyShadedLight(int id, String texure) {
		super(id, texure);
	}
	public EscapyShadedLight(int id) {
		super(id);
	}
	public EscapyShadedLight(String texure, float scale, float x, float y) {
		super(texure, scale, x, y);
	}
	public EscapyShadedLight(String texure, float x, float y) {
		super(texure, x, y);
	}
	public EscapyShadedLight(String texure, float scale) {
		super(texure, scale);
	}
	public EscapyShadedLight(String texure, TransVec pos) {
		super(texure, pos);
	}
	public EscapyShadedLight(String texure) {
		super(texure);
	}
	public EscapyShadedLight(TransVec pos) {
		super(pos);
	}
	public EscapyShadedLight() {
		super();
	}

	public AbsStdLight preRender(EscapyGdxCamera escapyCamera) {
		
		fbo.begin().wipeFBO();
		colorizer.drawSprite(lightSprite, escapyCamera.getCamera());

		colorizer.renderColorized(lightSprite, lightMap.getSpriteRegion(), 
			escapyCamera.getCamera(), color.r, color.g, color.b, 
			getPositionVec(), resolution, 10);
		
		colorizer.renderColorized(lightSprite, lightMap.getSpriteRegion(), 
			escapyCamera.getCamera(), color.r, color.g, color.b, 
			getPositionVec(), resolution, 10);
	
		fbo.renderFBO();
		fbo.end();
		return this;
	}

	public AbsStdLight preRender() {
		return this.preRender(cam);
	}

}
