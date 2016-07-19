package com.game.render.fbo.psProcess.lights.stdLS.userState;

import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;

public class SimpleStdLight extends AbsStdLight {

	private String defTexture = "data\\postProcess\\lightSrc300x300_2.png";
	
	public SimpleStdLight(int id) {
		super(id);
	}
	public SimpleStdLight(int id, String texure) {
		super(id);
		super.setLightSource(texure);
	}
	public SimpleStdLight(String texure, float scale) {
		super();
		super.setLightSource(texure);
		super.scale(scale);
	}
	public SimpleStdLight(float size, String texure) {
		super();
		super.setLightSource(texure);
		super.setSize(size);
	}
	public SimpleStdLight() {
		super();
	}
	

	@Override
	public String getDefaultTexure() {
		return this.defTexture;
	}

	public AbsStdLight setDefTexture(String defTexture) {
		this.defTexture = defTexture;
		return this;
	}
	

	

}
