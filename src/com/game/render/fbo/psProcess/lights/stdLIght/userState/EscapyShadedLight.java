package com.game.render.fbo.psProcess.lights.stdLIght.userState;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.shader.blur.userState.EscapyStdBlurRenderer;
import com.game.render.shader.blur.userState.EscapyStdBlurRenderer.EscapyBlur;
import com.game.render.shader.shadow.userState.EscapyStdShadowMapRenderer;
import com.game.render.shader.shadow.userState.EscapyStdShadowRenderer;
import com.game.utils.translationVec.TransVec;

public class EscapyShadedLight extends EscapyStdLight {

	private EscapyStdShadowMapRenderer shadowMapRenderer;
	private EscapyStdShadowRenderer shadowRenderer;
	private EscapyStdBlurRenderer blurRednerer;
	
	private EscapyFBO lightMapFBO, shadowMapFBO, shadowFBO, colorFBO;
	private EscapyGdxCamera lightCam, shadowMapCam, shadowCam;
	private TransVec transPos;
	private TransVec blurDir;
	
	
	public EscapyShadedLight(EscapyFBO lightMap, int accuracy) {
		super(lightMap);
		initBlock((int)(64*Math.pow(2, accuracy)));
	}
	public EscapyShadedLight(EscapyFBO lightMap, int accuracy, String texture) {
		super(lightMap, texture);
		initBlock((int)(64*Math.pow(2, accuracy)));
	}
	public EscapyShadedLight(EscapyFBO lightMap) {
		super(lightMap);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(EscapyFBO lightMap, String texture) {
		super(lightMap, texture);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(float size, String texure, float x, float y) {
		super(size, texure, x, y);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(float size, String texure) {
		super(size, texure);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(int id, EscapyFBO lightMap, int accuracy) {
		super(id, lightMap);
		initBlock((int)(64*Math.pow(2, accuracy)));
	}
	public EscapyShadedLight(int id, EscapyFBO lightMap, int accuracy, String texture) {
		super(id, lightMap, texture);
		initBlock((int)(64*Math.pow(2, accuracy)));
	}
	public EscapyShadedLight(int id, EscapyFBO lightMap) {
		super(id, lightMap);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(int id, EscapyFBO lightMap, String texture) {
		super(id, lightMap, texture);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(int id, String texure) {
		super(id, texure);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(int id) {
		super(id);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(String texure, float scale, float x, float y) {
		super(texure, scale, x, y);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(String texure, float x, float y) {
		super(texure, x, y);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(String texure, float scale) {
		super(texure, scale);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(String texure, TransVec pos) {
		super(texure, pos);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(String texure) {
		super(texure);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight(TransVec pos) {
		super(pos);
		initBlock((int)(64*Math.pow(2, 1)));
	}
	public EscapyShadedLight() {
		super();
		initBlock((int)(64*Math.pow(2, 1)));
	}

	
	private void initBlock(int lacc) {
		
		if (lacc > 2048) lacc = 2048;
		else if (lacc < 64) lacc = 64;
		System.err.println("tex_"+getID()+" : "+super.getLightTexture().getWidth()+" "+super.getLightTexture().getHeight());
		System.err.println("lac_"+getID()+" : "+lacc+" 1");
		
		{
			this.lightMapFBO = new StandartFBO(super.getID(), super.getLightTexture().getWidth(),
				super.getLightTexture().getHeight());
			this.shadowMapFBO = new StandartFBO(super.getID(), lacc, 1);
			this.shadowFBO = new StandartFBO(super.getID(), super.getLightTexture().getWidth(),
				super.getLightTexture().getHeight());
			this.colorFBO = new StandartFBO(super.getID());
		}
		
		{
			this.lightCam = new EscapyGdxCamera(lightMapFBO.getRegWidth(), lightMapFBO.getRegHeight());
			this.shadowMapCam = new EscapyGdxCamera(shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
			this.shadowCam = new EscapyGdxCamera(false, shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
		}
		
		{
			this.transPos = new TransVec();
			this.blurDir = new TransVec(0.9f, 0.9f);
			this.shadowMapRenderer = new EscapyStdShadowMapRenderer(super.getID());
			this.shadowRenderer = new EscapyStdShadowRenderer(super.getID());
			this.blurRednerer = new EscapyStdBlurRenderer(super.getID(), EscapyBlur.GAUSSIAN_13);
		}
		
		super.resolution = new TransVec(super.getLightTexture().getWidth() * scale,
				super.getLightTexture().getHeight() * scale);
	}
	
	/**
	 * Set the shadowcast accuracy. <br>This operation will reinit light instance.
	 * @param acc - accuracy range: min 0, max 5 (kamikaze) 
	 * @return {@link AbsStdLight}
	 */
	public AbsStdLight setAccuracy(int acc) {
		initBlock((int)(64*Math.pow(2, acc)));
		return this;
	}
	
	
	
	@Override
	public AbsStdLight preRender(EscapyGdxCamera escapyCamera) {

		lightCam.setCameraPosition(transPos.
	 			vecfuncv(v -> v.sub(escapyCamera.getShiftVec())).arrfuncf(n -> n / scale));

		Sprite tempSprite = new Sprite(lightMap.getTextureRegion());
		tempSprite.setSize(lightMap.getTextureRegion().getRegionWidth() / scale, 
				lightMap.getTextureRegion().getRegionHeight() / scale);
		
		lightMapFBO.begin().clearFBO(1f,1f,1f,1f);
			stdRenderer.drawSprite(tempSprite, lightCam.getCamera());
		lightMapFBO.end();
		
		shadowMapFBO.begin().wipeFBO();
			shadowMapRenderer.renderShadow(
				lightMapFBO.getTextureRegion(), shadowMapCam.getCamera(), 
				lightMapFBO.getRegWidth(), lightMapFBO.getRegHeight(), 0, 0, 
				shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
		shadowMapFBO.end();
		
		shadowFBO.begin().wipeFBO(); 
			shadowRenderer.renderShadow(
				shadowMapFBO.getTextureRegion(), shadowCam.getCamera(), 
				fbo.getRegWidth(), fbo.getRegHeight(), 0, 0, 
				shadowMapFBO.getRegWidth(), shadowMapFBO.getRegHeight());
		shadowFBO.end();

		
		Sprite shadowSprite = new Sprite(shadowFBO.getTextureRegion());
		shadowSprite.setPosition(lightSprite.getX(), lightSprite.getY());

		this.colorFBO.begin().wipeFBO();
		{
			super.colorizer.renderLightSrc(lightSprite, shadowSprite, 
				escapyCamera.getCamera(), color, lightAngles, 
				resolution, coeff, correct, radius);
			super.colorizer.drawSprite(lightSprite, escapyCamera.getCamera());
		}	this.colorFBO.end();
		
		Sprite endSprite = colorFBO.getSpriteRegion();
		super.fbo.begin().wipeFBO(); 
		{
			this.colorFBO.renderFBO().renderFBO();
			this.blurRednerer.renderBlured(endSprite, escapyCamera.getCamera(),
					colorFBO.getRegWidth(), colorFBO.getRegHeight(), blurDir);
			this.blurRednerer.drawSprite(endSprite, escapyCamera.getCamera());
		}	super.fbo.end();
		
		return this;
	}
	

	
	@Override
	public void stateUpdated(TransVec state) {
		super.stateUpdated(state);
		transPos.setTransVec(super.getPosition());
	}
}
