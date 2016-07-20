package com.game.render.fbo.psProcess.lights.stdLS.userState;

import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.utils.translationVec.TransVec;

public class SimpleStdLight extends AbsStdLight {

	private static String defTexture = "data\\postProcess\\lightSrc300x300_0.png";
	
	public SimpleStdLight(int id) {
		super(id);
	}
	public SimpleStdLight(int id, String texure) {
		super(id);
		super.setLightSource(texure);
	}
	public SimpleStdLight(String texure) {
		super();
		super.setLightSource(texure);
	}
	public SimpleStdLight(String texure, TransVec pos) {
		super();
		super.setLightSource(texure);
		super.setPosition(pos);
	}
	public SimpleStdLight(String texure, float x, float y) {
		super();
		super.setLightSource(texure);
		super.setPosition(x, y);
	}
	public SimpleStdLight(String texure, float scale) {
		super();
		super.setLightSource(texure);
		super.scale(scale);
	}
	public SimpleStdLight(String texure, float scale, float x, float y) {
		super();
		super.setLightSource(texure);
		super.scale(scale);
		super.setPosition(x, y);
	}
	public SimpleStdLight(float size, String texure) {
		super();
		super.setLightSource(texure);
		super.setSize(size);
	}
	public SimpleStdLight(float size, String texure, float x, float y) {
		super();
		super.setLightSource(texure);
		super.setSize(size);
		super.setPosition(x, y);
	}
	public SimpleStdLight(TransVec pos) {
		super();
		super.setPosition(pos);
	}
	public SimpleStdLight() {
		super();
	}
	

	@Override
	public String getDefaultTexure() {
		return defTexture;
	}

	public static void setDefTexture(String texture) {
		defTexture = texture;
	}
	
	

}
