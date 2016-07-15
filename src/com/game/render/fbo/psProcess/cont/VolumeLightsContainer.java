package com.game.render.fbo.psProcess.cont;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.psProcess.lights.AbsLight;
import com.game.render.fbo.psRender.EscapyPostRenderer;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.absContainer.EscapyContainer;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Class VolumeLightsContainer.
 */
public class VolumeLightsContainer implements EscapyPostRenderer, EscapyPostRenderable,
	EscapyContainer<AbsLight> {

	private List<AbsLight> volumeLights, buffer;
	private EscapyGdxCamera postRenderCamera;
	
	private EscapyFBO nrmlFBO;
	
	/**
	 * Instantiates a new volume lights container.
	 */
	public VolumeLightsContainer() {
		
		this.volumeLights = new ArrayList<>();
		this.buffer = new ArrayList<>();
		this.setPostRenderFBO(new StandartMultiFBO());
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
		
		this.volumeLights = new ArrayList<>();
		this.buffer = new ArrayList<>();
		this.setPostRenderCamera(postRenderCamera);
		this.setPostRenderFBO(new StandartMultiFBO());
		return;
	}
	
	/**
	 * Instantiates a new volume lights container.
	 *
	 * @param mutliFBO
	 *            the mutli FBO
	 */
	public VolumeLightsContainer(EscapyFBO mutliFBO) {
		
		this.volumeLights = new ArrayList<>();
		this.buffer = new ArrayList<>();
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		this.setPostRenderFBO(mutliFBO);
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
		
		this.volumeLights = new ArrayList<>();
		this.buffer = new ArrayList<>();
		this.setPostRenderCamera(postRenderCamera);
		this.setPostRenderFBO(mutliFBO);
		return;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#postRender(com.game.render.fbo.EscapyFBO, com.game.utils.translationVec.TransVec)
	 */
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec) {
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
		
		for (AbsLight light : volumeLights) {
			light.getPosition().sub(translationVec.getTransVec());
			//fbo.renderFBO(postRenderCamera, light);
			this.nrmlFBO.renderFBO(postRenderCamera, light);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#addSource(java.lang.Object)
	 */
	@Override
	public int addSource(AbsLight light) {
		this.volumeLights.add(light);
		return volumeLights.get(volumeLights.size() - 1).getId();
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#getSourceByID(int)
	 */
	@Override
	public AbsLight getSourceByID(int ID) {
		for (AbsLight lightBuff : buffer)
			if (lightBuff.getId() == ID)
				return lightBuff;
			
		for (AbsLight light : volumeLights)
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
		for (AbsLight light : volumeLights)
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
	public boolean removeSource(AbsLight light) {
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
	public PostProcessedProxy<AbsLight, EscapyContainer<AbsLight>> getLight(int id) {
		return new PostProcessedProxy<AbsLight, EscapyContainer<AbsLight>>(id, this);
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#setPostRenderCamera(com.game.render.EscapyGdxCamera)
	 */
	@Override
	public EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#setPostRenderFBO(com.game.render.fbo.EscapyFBO)
	 */
	@Override
	public <T extends EscapyFBO> EscapyPostRenderable setPostRenderFBO(T postRednerFBO) {
		this.nrmlFBO = postRednerFBO;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#getPostRenderFBO()
	 */
	@Override
	public EscapyFBO getPostRenderFBO() {
		return this.nrmlFBO;
	}

	

	
	
}
