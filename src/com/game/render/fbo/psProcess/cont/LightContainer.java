package com.game.render.fbo.psProcess.cont;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;
import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.render.fbo.psProcess.program.userState.FBOColorDodgeProgram;
import com.game.render.fbo.psProcess.program.userState.FBOLinearDodgeProgram;
import com.game.render.fbo.psProcess.program.userState.FBOSoftLightProgram;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.absContainer.EscapyAbsContainer;
import com.game.utils.translationVec.TransVec;

public class LightContainer extends EscapyAbsContainer<AbsStdLight> implements EscapyPostRenderable {

	private EscapyGdxCamera postRenderCamera;
	private EscapyMultiFBO lightFBO;
	
	public LightContainer() {
		
	}
	public LightContainer(EscapyFBO mutliFBO) {
		this.setPostRenderFBO(mutliFBO);
	}
	public LightContainer(EscapyFBO mutliFBO, EscapyGdxCamera postRenderCamera) {
		this.setPostRenderFBO(mutliFBO);
		this.setPostRenderCamera(postRenderCamera);
	}

	public LightContainer mergeContainedFBO() {
		return this.mergeContainedFBO(postRenderCamera);
	}
	public LightContainer mergeContainedFBO(EscapyGdxCamera camera) {
		super.targetsList.forEach(light -> light.preRender(camera));
		this.lightFBO.begin();
		super.targetsList.forEach(light -> light.renderGraphic(null, camera));
		this.lightFBO.end().mergeBuffer();
		return this;
	}
	
	public LightContainer mergeContainedFBO(TransVec translationVec) {
		return this.mergeContainedFBO(translationVec, postRenderCamera);
	}
	
	public LightContainer mergeContainedFBO(TransVec translationVec, EscapyGdxCamera camera) {
		super.targetsList.forEach(light -> light.preRender(camera));
		this.lightFBO.begin();
			super.targetsList.forEach(light -> light.renderGraphic(translationVec.getTransVecArray(), camera));
		this.lightFBO.end().mergeBuffer();
		return this;
	}
	
	@Override
	protected void initContainer() {
		super.initContainer();
		this.postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		this.lightFBO = new StandartMultiFBO();
	}
	
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec) {
		fbo.begin();
			this.postRender(translationVec);
		fbo.end();
		return fbo;
	}

	@Override
	public void postRender(TransVec translationVec) {
		super.targetsList.forEach(light -> 
			this.lightFBO.renderFBO(postRenderCamera, light));
	}

	@Override
	public <T extends EscapyFBO> EscapyPostRenderable setPostRenderFBO(T postRednerFBO) throws EscapyFBOtypeException {
		if (postRednerFBO instanceof EscapyMultiFBO) {
			this.lightFBO = (EscapyMultiFBO) postRednerFBO;
			//this.lightFBO.setRenderProgram(new FBOColorDodgeProgram(lightFBO));
			this.lightFBO.setRenderProgram(new FBOSoftLightProgram(lightFBO));
			//this.lightFBO.setRenderProgram(new FBOLinearDodgeProgram(lightFBO));
		}
		else throw new EscapyFBOtypeException();
		return this;
	}

	@Override
	public EscapyFBO getPostRenderFBO() {
		return this.lightFBO;
	}

	@Override
	public EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}

	


	

}
