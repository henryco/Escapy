package com.game.render.fbo.psProcess.lights.stdLIght.userState;

import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightSrcFactory;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.utils.translationVec.TransVec;

public class EscapyStdLight extends AbsStdLight {

		
	public EscapyStdLight(int id) {
		super(id);
		super.setLightSource(getDefaultLight());
	}
	public EscapyStdLight(int id, EscapyLightType lightType) {
		super(id);
		super.setLightSource(lightType);
	}
	public EscapyStdLight(EscapyLightType lightType) {
		super();
		super.setLightSource(lightType);
	}
	public EscapyStdLight(EscapyLightType lightType, TransVec pos) {
		super();
		super.setLightSource(lightType);
		super.setPosition(pos);
	}
	public EscapyStdLight(EscapyLightType lightType, float x, float y) {
		super();
		super.setLightSource(lightType);
		super.setPosition(x, y);
	}
	public EscapyStdLight(EscapyLightType lightType, float scale, float x, float y) {
		super();
		super.setLightSource(lightType);

		super.setPosition(x, y);
	}
	public EscapyStdLight(TransVec pos) {
		super();
		super.setLightSource(getDefaultLight());
		super.setPosition(pos);
	}
	public EscapyStdLight(){
		super();
		super.setLightSource(getDefaultLight());
	}
	

	@Override
	public EscapyLightType getDefaultLight() {
		return EscapyLightSrcFactory.RND_512();
	}

}
