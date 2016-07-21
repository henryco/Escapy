package com.game.render.fbo.psProcess.lights.stdLS.userState;

import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.utils.translationVec.TransVec;

public class SimpleStdLight extends AbsStdLight {

	private static String defTexture = "data\\postProcess\\lightSrc256x256_0.png";
	
	public SimpleStdLight(int id, EscapyMultiFBO target) {
		super(id, target);
	}
	public SimpleStdLight(int id, String texure, EscapyMultiFBO target) {
		super(id, target);
		super.setLightSource(texure);
	}
	public SimpleStdLight(String texure, EscapyMultiFBO target) {
		super(target);
		super.setLightSource(texure);
	}
	public SimpleStdLight(String texure, TransVec pos, EscapyMultiFBO target) {
		super(target);
		super.setLightSource(texure);
		super.setPosition(pos);
	}
	public SimpleStdLight(String texure, float x, float y, EscapyMultiFBO target) {
		super(target);
		super.setLightSource(texure);
		super.setPosition(x, y);
	}
	public SimpleStdLight(String texure, float scale, EscapyMultiFBO target) {
		super(target);
		super.setLightSource(texure);
		super.scale(scale);
	}
	public SimpleStdLight(String texure, float scale, float x, float y, EscapyMultiFBO target) {
		super(target);
		super.setLightSource(texure);
		super.scale(scale);
		super.setPosition(x, y);
	}
	public SimpleStdLight(float size, String texure, EscapyMultiFBO target) {
		super(target);
		super.setLightSource(texure);
		super.setSize(size);
	}
	public SimpleStdLight(float size, String texure, float x, float y, EscapyMultiFBO target) {
		super(target);
		super.setLightSource(texure);
		super.setSize(size);
		super.setPosition(x, y);
	}
	public SimpleStdLight(TransVec pos, EscapyMultiFBO target) {
		super(target);
		super.setPosition(pos);
	}
	public SimpleStdLight(EscapyMultiFBO target) {
		super(target);
	}
	public SimpleStdLight(){
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
