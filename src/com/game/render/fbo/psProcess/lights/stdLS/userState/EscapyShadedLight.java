package com.game.render.fbo.psProcess.lights.stdLS.userState;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.render.shader.shadow.userState.EscapyStdShadowRenderer;
import com.game.utils.translationVec.TransVec;

public class EscapyShadedLight extends EscapyStdLight {

	private EscapyStdShadowRenderer shadowRenderer;
	private EscapyFBO lightMapFBO, shadowMapFBO;
	private EscapyGdxCamera lightCam, shadowCam;
	private TransVec transPos;
	
	
	public EscapyShadedLight(EscapyFBO lightMap) {
		super(lightMap);
		initBlock();
	}
	public EscapyShadedLight(float size, String texure, float x, float y) {
		super(size, texure, x, y);
		initBlock();
	}
	public EscapyShadedLight(float size, String texure) {
		super(size, texure);
		initBlock();
	}
	public EscapyShadedLight(int id, EscapyFBO lightMap) {
		super(id, lightMap);
		initBlock();
	}
	public EscapyShadedLight(int id, String texure) {
		super(id, texure);
		initBlock();
	}
	public EscapyShadedLight(int id) {
		super(id);
		initBlock();
	}
	public EscapyShadedLight(String texure, float scale, float x, float y) {
		super(texure, scale, x, y);
		initBlock();
	}
	public EscapyShadedLight(String texure, float x, float y) {
		super(texure, x, y);
		initBlock();
	}
	public EscapyShadedLight(String texure, float scale) {
		super(texure, scale);
		initBlock();
	}
	public EscapyShadedLight(String texure, TransVec pos) {
		super(texure, pos);
		initBlock();
	}
	public EscapyShadedLight(String texure) {
		super(texure);
		initBlock();
	}
	public EscapyShadedLight(TransVec pos) {
		super(pos);
		initBlock();
	}
	public EscapyShadedLight() {
		super();
		initBlock();
	}

	private void initBlock() {
		
		this.lightMapFBO = new StandartFBO(super.getID(), super.getLightTexture().getWidth(),
				super.getLightTexture().getHeight());
		this.shadowMapFBO = new StandartFBO(super.getID(), lightMapFBO.getRegWidth(), 1);
		this.lightCam = new EscapyGdxCamera(lightMapFBO.getRegWidth(), lightMapFBO.getRegHeight());
		this.shadowCam = new EscapyGdxCamera(shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
		
		this.transPos = new TransVec();
		this.shadowRenderer = new EscapyStdShadowRenderer(super.getID());
	}
	
	@Override
	public AbsStdLight preRender(EscapyGdxCamera escapyCamera) {
	
		transPos.sub(escapyCamera.getTranslationVector());
		lightCam.setCameraPosition(transPos.arrfunc(n -> n / scale));
		Sprite tempSprite = new Sprite(lightMap.getTextureRegion());
		tempSprite.setSize(lightMap.getTextureRegion().getRegionWidth() / scale, 
				lightMap.getTextureRegion().getRegionHeight() / scale);
	
		lightMapFBO.begin().wipeFBO();
		colorizer.drawSprite(tempSprite, lightCam.getCamera());
		lightMapFBO.end();
		lightMapFBO.renderFBO();
		
		shadowMapFBO.begin().wipeFBO();//.clearFBO(1f, 1f, 1f, 1f);
		shadowRenderer.renderShadow(lightMapFBO.getTextureRegion(), shadowCam.getCamera(),
				shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight(),0, 0, 
				shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
		
		shadowMapFBO.end();
		shadowMapFBO.renderFBO();
		/*		
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
		//*/		

		
		
		return this;
	}

	@Override
	public void stateUpdated(TransVec state) {
		super.stateUpdated(state);
		transPos.x = super.getPosition().x;
		transPos.y = super.getPosition().y;
	}
}
