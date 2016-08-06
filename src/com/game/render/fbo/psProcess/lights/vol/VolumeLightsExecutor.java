package com.game.render.fbo.psProcess.lights.vol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;
import com.game.render.fbo.psProcess.cont.EscapyFBOContainer;
import com.game.render.fbo.psRender.EscapyPostRenderer;
import com.game.render.shader.volumeLight.EscapyVolumeRenderer;
import com.game.render.shader.volumeLight.userState.EscapyStdVolumeRenderer;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Class VolumeLightsExecutor.
 */
public class VolumeLightsExecutor implements EscapyPostRenderer,
	EscapyFBOContainer {

	private EscapyGdxCamera postRenderCamera;
	
	private EscapyMultiFBO nrmlFBO;
	private EscapyFBO lightMapFBO;
	private EscapyFBO lightBuffFBO;
	
	private Vector2 canvasDim;
	
	private float lightIntensity, ambientIntesity;

	private EscapyVolumeRenderer volRenderer;
	
	{
		this.lightIntensity = 0.36f;
		this.ambientIntesity = 0.39f;
		this.volRenderer = new EscapyStdVolumeRenderer();
		this.canvasDim = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/**
	 * Instantiates a new volume lights container.
	 */
	public VolumeLightsExecutor() {
		
		this.setNormalsFBO(new StandartMultiFBO());
		this.setLightMapFBO(new StandartFBO());
		this.setLightBuffFBO(new StandartFBO());
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		return;
	}
	
	public VolumeLightsExecutor(float lightIntensity, float ambientIntesity) {
		
		this.setNormalsFBO(new StandartMultiFBO());
		this.setLightMapFBO(new StandartFBO());
		this.setLightBuffFBO(new StandartFBO());
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		this.setLightIntensity(lightIntensity);
		this.setAmbientIntsity(ambientIntesity);
		return;
	}
	
	/**
	 * Instantiates a new volume lights container.
	 *
	 * @param postRenderCamera
	 *            the post render camera
	 */
	public VolumeLightsExecutor(EscapyGdxCamera postRenderCamera) {
		
		this.setPostRenderCamera(postRenderCamera);
		this.setNormalsFBO(new StandartMultiFBO());
		this.setLightMapFBO(new StandartFBO());
		this.setLightBuffFBO(new StandartFBO());
		return;
	}
	
	/**
	 * Instantiates a new volume lights container.
	 *
	 * @param nrmlMapFBO
	 *            the mutli FBO
	 */
	public VolumeLightsExecutor(EscapyFBO nrmlMapFBO, EscapyFBO lightMapFBO, EscapyFBO lightBuffFBO) {
		
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		this.setNormalsFBO(nrmlMapFBO);
		this.setLightMapFBO(lightMapFBO);
		this.setLightBuffFBO(lightBuffFBO);
		return;
	}
	
	public VolumeLightsExecutor(EscapyFBO nrmlMapFBO, EscapyFBO lightMapFBO, 
		EscapyFBO lightBuffFBO, float lightIntensity, float ambientIntesity) {
		
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		this.setNormalsFBO(nrmlMapFBO);
		this.setLightMapFBO(lightMapFBO);
		this.setLightBuffFBO(lightBuffFBO);
		this.setLightIntensity(lightIntensity);
		this.setAmbientIntsity(ambientIntesity);
		return;
	}
	
	public VolumeLightsExecutor(EscapyFBO nrmlMapFBO) {
		
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		this.setNormalsFBO(nrmlMapFBO);
		this.setLightMapFBO(new StandartFBO());
		this.setLightBuffFBO(new StandartFBO());
		return;
	}
	
	/**
	 * Instantiates a new volume lights container.
	 *
	 * @param postRenderCamera
	 *            the post render camera
	 * @param mutliFBO
	 *            the mutli FBO
	 */
	public VolumeLightsExecutor(EscapyGdxCamera postRenderCamera, EscapyFBO nrmlMapFBO, 
			EscapyFBO lightMapFBO) {
		
		this.setPostRenderCamera(postRenderCamera);
		this.setNormalsFBO(nrmlMapFBO);
		this.setLightMapFBO(lightMapFBO);
		this.setLightBuffFBO(new StandartFBO());
		return;
	}
	
	public VolumeLightsExecutor(EscapyGdxCamera postRenderCamera, EscapyFBO nrmlMapFBO ) {
		
		this.setPostRenderCamera(postRenderCamera);
		this.setNormalsFBO(nrmlMapFBO);
		this.setLightMapFBO(new StandartFBO());
		this.setLightBuffFBO(new StandartFBO());
		return;
	}
	
	
	
	public EscapyFBO postRenderLights(EscapyFBO targetFBO, EscapyFBO nrmlMapFBO,
			EscapyFBO lightMapFBO, EscapyFBO lightsFBO, float lightIntensity, 
			float ambientIntesity) {
		
		targetFBO.begin().renderFBO();
			this.volRenderer.renderVolumeLights(
					0, 0, lightsFBO.getTextureRegion(), nrmlMapFBO.getTextureRegion(), 
					lightMapFBO.getTextureRegion(), this.canvasDim, 
					ambientIntesity, lightIntensity, this.postRenderCamera.getCamera());
		targetFBO.end();
		return targetFBO;
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#postRender(com.game.render.fbo.EscapyFBO, com.game.utils.translationVec.TransVec)
	 */
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec) {
		this.nrmlFBO.mergeBuffer();
		fbo.begin().renderFBO();
			this.postRender(translationVec);
		fbo.end();
		return fbo;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderer#postRender(com.game.utils.translationVec.TransVec)
	 */
	@Override
	public void postRender(TransVec translationVec) {
		
		this.volRenderer.renderVolumeLights(
			0, 0, this.lightBuffFBO.getTextureRegion(), this.nrmlFBO.getTextureRegion(),
			this.lightMapFBO.getTextureRegion(), this.canvasDim, this.ambientIntesity, 
			this.lightIntensity, this.postRenderCamera.getCamera());
	}

	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#setPostRenderCamera(com.game.render.EscapyGdxCamera)
	 */
	@Override
	public EscapyPostRenderer setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}

	public VolumeLightsExecutor setNormalsFBO(EscapyFBO nrmlMapFBO) 
			throws EscapyFBOtypeException {
		if (nrmlMapFBO instanceof EscapyMultiFBO) {
			this.nrmlFBO = (EscapyMultiFBO) nrmlMapFBO;
			return this;
		}	throw new EscapyFBOtypeException();
	}

	public EscapyMultiFBO getNormalsFBO() {
		return this.nrmlFBO;
	}

	@Override
	public EscapyFBO mergeContainedFBO() {
		this.nrmlFBO.mergeBuffer();
		return nrmlFBO;
	}

	@Override
	public EscapyFBO mergeContainedFBO(EscapyGdxCamera camera) {
		this.nrmlFBO.mergeTargetMultiBuffer(camera);
		return nrmlFBO;
	}

	public EscapyFBO getLightMapFBO() {
		return lightMapFBO;
	}

	public void setLightMapFBO(EscapyFBO lightMapFBO) {
		this.lightMapFBO = lightMapFBO;
	}

	public EscapyFBO getLightBuffFBO() {
		return lightBuffFBO;
	}

	public void setLightBuffFBO(EscapyFBO lightBuffFBO) {
		this.lightBuffFBO = lightBuffFBO;
	}

	public float getLightIntensity() {
		return lightIntensity;
	}

	public VolumeLightsExecutor setLightIntensity(float lightIntensity) {
		this.lightIntensity = lightIntensity;
		return this;
	}

	public float getAmbientIntsity() {
		return ambientIntesity;
	}

	public VolumeLightsExecutor setAmbientIntsity(float ambientIntsity) {
		this.ambientIntesity = ambientIntsity;
		return this;
	}



	
	
}
