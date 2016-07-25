package com.game.render.fbo.psProcess.cont;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;
import com.game.render.fbo.psProcess.lights.stdLS.AbsStdLight;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.fbo.psProcess.program.FBOStdBlendProgramFactory;
import com.game.render.fbo.psRender.EscapyPostExec;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.absContainer.EscapyAbsContainer;
import com.game.utils.translationVec.TransVec;

public class LightContainer extends EscapyAbsContainer<AbsStdLight>
	implements EscapyPostExec <EscapyMultiFBO> {

	private EscapyGdxCamera postRenderCamera;
	private EscapyMultiFBO lightFBO;
	private EscapyFBO ortoFBO;
	
	public LightContainer() {
		
	}
	public LightContainer(EscapyFBO mutliFBO) {
		this.setPostRenderFBO(mutliFBO);
		this.setRenderProgram(FBOStdBlendProgramFactory.softLight(lightFBO));
	}
	public LightContainer(EscapyFBO mutliFBO, FBORenderProgram<EscapyMultiFBO> program) {
		this.setPostRenderFBO(mutliFBO);
		this.setRenderProgram(program);
	}
	public LightContainer(EscapyFBO mutliFBO, EscapyGdxCamera postRenderCamera) {
		this.setPostRenderFBO(mutliFBO);
		this.setPostRenderCamera(postRenderCamera);
		this.setRenderProgram(FBOStdBlendProgramFactory.softLight(lightFBO));
	}
	public LightContainer(EscapyFBO mutliFBO, EscapyGdxCamera postRenderCamera, 
			FBORenderProgram<EscapyMultiFBO> program) {
		this.setPostRenderFBO(mutliFBO);
		this.setPostRenderCamera(postRenderCamera);
		this.setRenderProgram(program);
	}
	
	
	@Override
	protected void initContainer() {
		super.initContainer();
		this.postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		this.lightFBO = new StandartMultiFBO();
		this.ortoFBO = new StandartFBO();
	}
	
	
	public LightContainer mergeContainedFBO() {
		return this.mergeContainedFBO(postRenderCamera);
	}
	public LightContainer mergeContainedFBO(EscapyGdxCamera camera) {
		
		 	this.lightFBO.begin();
			super.targetsList.forEach(light -> {
				light.renderAlternative(camera, lightFBO);
			});
			
			this.lightFBO.end().mergeBuffer();
		
		return this;
	}
	public LightContainer mergeContainedFBO(TransVec translationVec) {
		return this.mergeContainedFBO(translationVec, postRenderCamera);
	}
	
	public LightContainer mergeContainedFBO(TransVec translationVec, EscapyGdxCamera camera) {
		this.lightFBO.begin();
			super.targetsList.forEach(light -> light.renderGraphic(translationVec.getTransVecArray(), camera));
		this.lightFBO.end().mergeBuffer();
		return this;
	}
	

	public LightContainer renderMerged(EscapyGdxCamera camera) {

		ortoFBO.begin().wipeFBO();
		super.targetsList.forEach(light -> light.renderGraphic(null, camera));
		ortoFBO.end();
		
		return this;
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
		this.lightFBO.renderFBO(postRenderCamera);
	}

	@Override
	public <T extends EscapyFBO> EscapyPostRenderable setPostRenderFBO(T postRednerFBO) throws EscapyFBOtypeException {
		if (postRednerFBO instanceof EscapyMultiFBO) 
			this.lightFBO = (EscapyMultiFBO) postRednerFBO;
		else throw new EscapyFBOtypeException();
		return this;
	}

	@Override
	public EscapyMultiFBO getPostRenderFBO() {
		return this.lightFBO;
	}

	@Override
	public EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}
	@Override
	public EscapyPostRenderable setRenderProgram(FBORenderProgram<EscapyMultiFBO> program) {
		if (program.getFBOTarget() != this.lightFBO)
			program.setFBOTarget(lightFBO);
		this.lightFBO.setRenderProgram(program);
		return this;
	}
	

	


	

}
