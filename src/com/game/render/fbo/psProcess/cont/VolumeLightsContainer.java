package com.game.render.fbo.psProcess.cont;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;
import com.game.render.fbo.psProcess.lights.vol.AbsVolLight;
import com.game.render.fbo.psRender.EscapyPostRenderer;
import com.game.utils.absContainer.EscapyContainer;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Class VolumeLightsContainer.
 */
public class VolumeLightsContainer implements EscapyPostRenderer,
	EscapyContainer<AbsVolLight>, EscapyFBOContainer {

	private List<AbsVolLight> volumeLights, buffer;
	private EscapyGdxCamera postRenderCamera;
	
	private EscapyMultiFBO nrmlFBO;
	
	/**
	 * Instantiates a new volume lights container.
	 */
	public VolumeLightsContainer() {
		
		this.initContainer();
		this.setNormalsFBO(new StandartMultiFBO());
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		return;
	}
	
	/**
	 * Instantiates a new volume lights container.
	 *
	 * @param postRenderCamera
	 *            the post render camera
	 */
	public VolumeLightsContainer(EscapyGdxCamera postRenderCamera) {
		
		this.initContainer();
		this.setPostRenderCamera(postRenderCamera);
		this.setNormalsFBO(new StandartMultiFBO());
		return;
	}
	
	/**
	 * Instantiates a new volume lights container.
	 *
	 * @param mutliFBO
	 *            the mutli FBO
	 */
	public VolumeLightsContainer(EscapyFBO mutliFBO) {
		
		this.initContainer();
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		this.setNormalsFBO(mutliFBO);
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
	public VolumeLightsContainer(EscapyGdxCamera postRenderCamera, EscapyFBO mutliFBO) {
		
		this.initContainer();
		this.setPostRenderCamera(postRenderCamera);
		this.setNormalsFBO(mutliFBO);
		return;
	}
	
	private void initContainer() {
		this.volumeLights = new ArrayList<>();
		this.buffer = new ArrayList<>();
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#postRender(com.game.render.fbo.EscapyFBO, com.game.utils.translationVec.TransVec)
	 */
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec) {
		this.nrmlFBO.mergeBuffer();
		fbo.begin();
			this.postRender(translationVec);
		fbo.end();
		return fbo;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderer#postRender(com.game.utils.translationVec.TransVec)
	 */
	@Override
	public void postRender(TransVec translationVec) {
		
		volumeLights.forEach((light) -> {
			light.getPosition().sub(translationVec.getTransVec());
			this.nrmlFBO.renderFBO(postRenderCamera, light);
		});
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#addSource(java.lang.Object)
	 */
	@Override
	public int addSource(AbsVolLight light) {
		this.volumeLights.add(light);
		return volumeLights.get(volumeLights.size() - 1).getId();
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#getSourceByID(int)
	 */
	@Override
	public AbsVolLight getSourceByID(int ID) {
		for (AbsVolLight lightBuff : buffer)
			if (lightBuff.getId() == ID)
				return lightBuff;
		
		for (AbsVolLight light : volumeLights)
			if (light.getId() == ID) {
				buffer.add(light);
				return light;
			}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#removeSourceByID(int)
	 */
	@Override
	public boolean removeSourceByID(int ID) {
		for (AbsVolLight light : volumeLights)
			if (light.getId() == ID) {
				buffer.remove(light);
				return volumeLights.remove(light);
			}
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#removeSource(java.lang.Object)
	 */
	@Override
	public boolean removeSource(AbsVolLight light) {
		buffer.remove(light);
		return volumeLights.remove(light);
	}
	
	
	/**
	 * Gets the light.
	 *
	 * @param id
	 *            the id
	 * @return the light
	 */
	public PostProcessedProxy<AbsVolLight, EscapyContainer<AbsVolLight>> getLight(int id) {
		return new PostProcessedProxy<AbsVolLight, EscapyContainer<AbsVolLight>>(id, this);
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#setPostRenderCamera(com.game.render.EscapyGdxCamera)
	 */
	@Override
	public EscapyPostRenderer setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}

	public VolumeLightsContainer setNormalsFBO(EscapyFBO nrmlMapFBO) 
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
	public VolumeLightsContainer mergeContainedFBO() {
		this.nrmlFBO.mergeBuffer();
		return this;
	}

	@Override
	public VolumeLightsContainer mergeContainedFBO(EscapyGdxCamera camera) {
		this.nrmlFBO.mergeTargetMultiBuffer(camera);
		return this;
	}



	
	
}
