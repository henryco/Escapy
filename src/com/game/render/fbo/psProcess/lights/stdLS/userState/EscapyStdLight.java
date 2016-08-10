package com.game.render.fbo.psProcess.lights.stdLS.userState;

import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.utils.translationVec.TransVec;

public class EscapyStdLight extends AbsStdLight {

	private static final String defTexture = "data\\postProcess\\lightSrc256x256_1.png";
	
	public EscapyStdLight(int id) {
		super(id);
	}
	public EscapyStdLight(int id, String texure) {
		super(id);
		super.setLightSource(texure);
	}
	public EscapyStdLight(String texure) {
		super();
		super.setLightSource(texure);
	}
	public EscapyStdLight(String texure, TransVec pos) {
		super();
		super.setLightSource(texure);
		super.setPosition(pos);
	}
	public EscapyStdLight(String texure, float x, float y) {
		super();
		super.setLightSource(texure);
		super.setPosition(x, y);
	}
	public EscapyStdLight(String texure, float scale) {
		super();
		super.setLightSource(texure);
		super.scale(scale);
	}
	public EscapyStdLight(String texure, float scale, float x, float y) {
		super();
		super.setLightSource(texure);
		super.scale(scale);
		super.setPosition(x, y);
	}
	public EscapyStdLight(float size, String texure) {
		super();
		super.setLightSource(texure);
		super.setSize(size);
	}
	public EscapyStdLight(float size, String texure, float x, float y) {
		super();
		super.setLightSource(texure);
		super.setSize(size);
		super.setPosition(x, y);
	}
	public EscapyStdLight(TransVec pos) {
		super();
		super.setPosition(pos);
	}
	public EscapyStdLight(int id, EscapyFBO lightMap){
		super(id, lightMap);
	}
	public EscapyStdLight(EscapyFBO lightMap){
		super(lightMap);
	}
	public EscapyStdLight(){
		super();
	}
	

	@Override
	public String getDefaultTexure() {
		return defTexture;
	}
}
