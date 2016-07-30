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
	
	private float lightIntensity, ambientIntsity;

	private EscapyVolumeRenderer volRenderer;
	
	{
		this.lightIntensity = 0.35f;
		this.ambientIntsity = 0.35f;
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
			this.lightMapFBO.getTextureRegion(), this.canvasDim, this.ambientIntsity, 
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
	public VolumeLightsExecutor mergeContainedFBO() {
		this.nrmlFBO.mergeBuffer();
		return this;
	}

	@Override
	public VolumeLightsExecutor mergeContainedFBO(EscapyGdxCamera camera) {
		this.nrmlFBO.mergeTargetMultiBuffer(camera);
		return this;
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

	public void setLightIntensity(float lightIntensity) {
		this.lightIntensity = lightIntensity;
	}

	public float getAmbientIntsity() {
		return ambientIntsity;
	}

	public void setAmbientIntsity(float ambientIntsity) {
		this.ambientIntsity = ambientIntsity;
	}



	
	
}
