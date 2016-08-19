package com.game.render.fbo.psProcess.cont;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.fbo.psProcess.program.FBOStdBlendProgramFactory;
import com.game.render.fbo.psRender.EscapyPostExec;
import com.game.render.fbo.psRender.EscapyPostIterative;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.render.shader.blend.EscapyBlendRenderer;
import com.game.render.shader.blur.userState.EscapyStdBlurRenderer;
import com.game.render.shader.blur.userState.EscapyStdBlurRenderer.EscapyBlur;
import com.game.utils.absContainer.EscapyAbsContainer;
import com.game.utils.translationVec.TransVec;

public class LightContainer extends EscapyAbsContainer<AbsStdLight>
	implements EscapyPostExec <EscapyMultiFBO>, EscapyPostIterative, EscapyFBOContainer {

	
	public final static class light extends FBOStdBlendProgramFactory {};
	
	private EscapyGdxCamera postRenderCamera;
	private EscapyMultiFBO lightFBO;
	private EscapyFBO ortoFBO, blurFBO;
	
	private final static String VERTEX ="shaders\\blend\\colorMix\\colorMix.vert";
	private final static String FRAGMENT ="shaders\\blend\\colorMix\\colorMix.frag";
	
	private EscapyBlendRenderer blender;
	private EscapyStdBlurRenderer blurRednerer;
	
	private boolean isBlured;
	
	public LightContainer() { 
	}
	public LightContainer(EscapyFBO mutliFBO) {
		this.setPostRenderFBO(mutliFBO);
		this.setRenderProgram(FBOStdBlendProgramFactory.softLight(lightFBO));
	}
	public LightContainer(EscapyFBO mutliFBO, boolean blur) {
		this.setPostRenderFBO(mutliFBO);
		this.setRenderProgram(FBOStdBlendProgramFactory.softLight(lightFBO));
		this.setBlur(blur);
	}
	public LightContainer(EscapyFBO mutliFBO, FBORenderProgram<EscapyMultiFBO> program) {
		this.setPostRenderFBO(mutliFBO);
		this.setRenderProgram(program);
	}
	public LightContainer(EscapyFBO mutliFBO, FBORenderProgram<EscapyMultiFBO> program, boolean blur) {
		this.setPostRenderFBO(mutliFBO);
		this.setRenderProgram(program);
		this.setBlur(blur);
	}
	public LightContainer(EscapyFBO mutliFBO, EscapyGdxCamera postRenderCamera) {
		this.setPostRenderFBO(mutliFBO);
		this.setPostRenderCamera(postRenderCamera);
		this.setRenderProgram(FBOStdBlendProgramFactory.softLight(lightFBO));
	}
	public LightContainer(EscapyFBO mutliFBO, EscapyGdxCamera postRenderCamera, boolean blur) {
		this.setPostRenderFBO(mutliFBO);
		this.setPostRenderCamera(postRenderCamera);
		this.setRenderProgram(FBOStdBlendProgramFactory.softLight(lightFBO));
		this.setBlur(blur);
	}
	public LightContainer(EscapyFBO mutliFBO, EscapyGdxCamera postRenderCamera, 
			FBORenderProgram<EscapyMultiFBO> program) {
		this.setPostRenderFBO(mutliFBO);
		this.setPostRenderCamera(postRenderCamera);
		this.setRenderProgram(program);
	}
	public LightContainer(EscapyFBO mutliFBO, EscapyGdxCamera postRenderCamera, 
			FBORenderProgram<EscapyMultiFBO> program, boolean blur) {
		this.setPostRenderFBO(mutliFBO);
		this.setPostRenderCamera(postRenderCamera);
		this.setRenderProgram(program);
		this.setBlur(blur);
	}
	
	@Override
	protected void initContainer() {
		super.initContainer();
		this.postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		this.lightFBO = new StandartMultiFBO();
		this.blender = new EscapyBlendRenderer(VERTEX, FRAGMENT, "targetMap", "blendMap");
		this.blurRednerer = new EscapyStdBlurRenderer(EscapyBlur.GAUSSIAN_13, 1f, 1f);
		this.ortoFBO = new StandartFBO();
		this.blurFBO = new StandartFBO();
		this.setBlur(false);
	}
	
	
	
	
	@Override
	public EscapyFBO mergeContainedFBO() {	
		return this.mergeContainedFBO(this.postRenderCamera);
	}
	public EscapyFBO mergeContainedFBO(int iterations) {	
		return this.mergeContainedFBO(this.postRenderCamera, iterations);
	}
	
	@Override
	public EscapyFBO mergeContainedFBO(EscapyGdxCamera camera) {
		
		EscapyFBO temp = renderContainedFBO(camera);
		lightFBO.forceRenderToFBO(temp).endMergedBuffer();
		return lightFBO;
	}
	public EscapyFBO mergeContainedFBO(EscapyGdxCamera camera, int iterations) {
		
		EscapyFBO temp = renderContainedFBO(camera);
		lightFBO.begin();
		while (iterations > 0) {
			lightFBO.renderToFBO(temp);
			iterations -= 1;
		}
		lightFBO.endMergedBuffer();
		return lightFBO;
	}
	
	public EscapyFBO renderContainedFBO(EscapyGdxCamera camera) {
		
		super.targetsList.forEach( light -> light.preRender(camera));

		this.blurFBO.begin().wipeFBO();	
		this.targetsList.forEach( light -> {
		
			this.blender.renderBlended(
					blurFBO.getTextureRegion(), 
					light.getFBO().getTextureRegion(),0,0, 
					Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 
					postRenderCamera.getCamera());
			this.blurFBO.renderFBO();
		});
		this.blurFBO.end();
		
		if (!isBlured)
			return this.blurFBO;

		this.ortoFBO.begin().wipeFBO();
			this.blurRednerer.renderBlured(blurFBO.getSpriteRegion(),
					postRenderCamera.getCamera(), blurFBO.getRegWidth(), 
					blurFBO.getRegHeight(), 1, 0);
			this.blurRednerer.renderBlured(blurFBO.getSpriteRegion(),
					postRenderCamera.getCamera(), blurFBO.getRegWidth(), 
					blurFBO.getRegHeight(), 0, 1);
			this.blurRednerer.renderBlured(blurFBO.getSpriteRegion(),
					postRenderCamera.getCamera(), blurFBO.getRegWidth(), 
					blurFBO.getRegHeight(), -1, 0);
			this.blurRednerer.renderBlured(blurFBO.getSpriteRegion(),
					postRenderCamera.getCamera(), blurFBO.getRegWidth(), 
					blurFBO.getRegHeight(), 0, -1);
		return this.ortoFBO.end();
	}
	
	
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec, int times) {
		fbo.begin();
		while (times > 0) {
			this.postRender(translationVec);
			times -=1;
		}	fbo.end().mergeBuffer();
		return fbo;
	}
	
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec) {

		return this.postRender(fbo, translationVec, 1);
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
	
	public float getAmbientIntensity() {
		return this.lightFBO.getRenderProgram().getAmbientIntensity();
	}
	
	public float getLightIntensity() {
		return this.lightFBO.getRenderProgram().getLightIntensity();
	}
	
	public LightContainer setAmbientIntesity(float amb) {
		this.lightFBO.getRenderProgram().setAmbientIntensity(amb);
		System.out.println("amb: "+this.lightFBO.getRenderProgram().getAmbientIntensity()+" "
					+ "::: "+" inten: "+this.lightFBO.getRenderProgram().getLightIntensity());
		return this;
	}
	public LightContainer setLightIntensity(float lgt) {
		this.lightFBO.getRenderProgram().setLightIntensity(lgt);
		System.out.println("amb: "+this.lightFBO.getRenderProgram().getAmbientIntensity()+" "
				+ "::: "+" inten: "+this.lightFBO.getRenderProgram().getLightIntensity());
		return this;
	}
	public LightContainer setBlur(boolean blur) {
		this.isBlured = blur;
		return this;
	}


	

}
