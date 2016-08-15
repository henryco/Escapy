package com.game.render.fbo.psProcess.lights.stdLS.userState;

import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.utils.translationVec.TransVec;

public class EscapyStdLight extends AbsStdLight {


	public final static class defTexture {
		public final static defTexture instance = new defTexture();
		public String RND_256 = "data\\postProcess\\lightSrc256x256_1.png";
		public String RND_512 = "data\\postProcess\\lightSrc512x512_1.png";
		public String RND_1024 = "data\\postProcess\\lightSrc1024x1024_1.png";
		//public String CNE_1024 = "data\\postProcess\\lightCone1024x1024_1.png";
		
	}	public static final defTexture texture = defTexture.instance;
	
	public EscapyStdLight(int id) {
		super(id);
		super.setLightSource(getDefaultTexure());
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
		System.out.println(x);
	}
	public EscapyStdLight(TransVec pos) {
		super();
		super.setLightSource(getDefaultTexure());
		super.setPosition(pos);
	}
	public EscapyStdLight(int id, EscapyFBO lightMap){
		super(id, lightMap);
		super.setLightSource(getDefaultTexure());
	}
	public EscapyStdLight(int id, EscapyFBO lightMap, String texture){
		super(id, lightMap);
		super.setLightSource(texture);
	}
	public EscapyStdLight(EscapyFBO lightMap){
		super(lightMap);
		super.setLightSource(getDefaultTexure());
	}
	public EscapyStdLight(EscapyFBO lightMap, String texture){
		super(lightMap);
		super.setLightSource(texture);
	}
	public EscapyStdLight(){
		super();
		super.setLightSource(getDefaultTexure());
	}
	

	@Override
	public String getDefaultTexure() {
		return texture.RND_512;
	}
}
