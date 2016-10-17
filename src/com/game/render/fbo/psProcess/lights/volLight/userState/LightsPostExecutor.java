package com.game.render.fbo.psProcess.lights.volLight.userState;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.EscapyMapRenderer;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.program.shader.blend.EscapyBlendRenderer;
import com.game.render.program.shader.blend.ShaderBlendProgram;
import com.game.render.shader.volumeLight.EscapyVolumeRenderer;
import com.game.render.shader.volumeLight.userState.EscapyStdVolumeRenderer;
import com.game.utils.translationVec.TransVec;

/**
 * @author Henry on 26/09/16.
 */
public class LightsPostExecutor {

	private TransVec frameDim;
	private EscapyGdxCamera camera;
	private EscapyVolumeRenderer volRenderer;
	private EscapyBlendRenderer maxRenderer;

	private boolean normalMappingOn, blur;

	private EscapyFBO normalMapFBO;
	private EscapyMapRenderer normalsMapRenderer;

	private float directIntensity, ambientIntesity, shadowIntensity, spriteSize, height, threshold;

	{
		this.directIntensity = 0.2f;
		this.ambientIntesity = 0.75f;
		this.shadowIntensity = 8;
		this.spriteSize = 1;
		this.threshold = 0;
		this.height = 0;

		this.normalMappingOn = false;
		this.blur = false;

		this.volRenderer = new EscapyStdVolumeRenderer();
		this.maxRenderer = ShaderBlendProgram.blendProgram("max");
		maxRenderer.floatUniforms.addUniform("threshold", 0f);
	}

	public LightsPostExecutor(float frameWidth, float frameHeight, EscapyMapRenderer ... normalsMapRenderer) {

		setFrameDim(new TransVec(frameWidth, frameHeight));
		camera = new EscapyGdxCamera((int) frameWidth, (int) frameHeight);
		if (normalsMapRenderer != null && normalsMapRenderer.length > 0)
			setNormalsMapRenderer(normalsMapRenderer[0]);
	}
	public LightsPostExecutor(float[] dim, EscapyMapRenderer ... normalsMapRenderer) {

		setFrameDim(new TransVec(dim));
		camera = new EscapyGdxCamera((int) dim[0], (int) dim[1]);
		if (normalsMapRenderer != null && normalsMapRenderer.length > 0)
			setNormalsMapRenderer(normalsMapRenderer[0]);
	}
	public LightsPostExecutor(TransVec dim, EscapyMapRenderer ... normalsMapRenderer) {

		setFrameDim(new TransVec(dim));
		camera = new EscapyGdxCamera((int) dim.x, (int) dim.y);
		if (normalsMapRenderer != null && normalsMapRenderer.length > 0)
			setNormalsMapRenderer(normalsMapRenderer[0]);
	}


	public void processFunc(EscapyGdxCamera camera) {
		if (normalMappingOn) {
			normalMapFBO.begin().clearFBO(0.502f, 0.502f, 1f, 1f);
			normalsMapRenderer.renderTextureMap(camera);
			normalMapFBO.end();
		}
	}

	public void processLightBuffer(EscapyGdxCamera camera, Sprite colorMap, Sprite maskMap, Sprite normalsMap) {

		if (normalMappingOn) volRenderer.renderVolumeLights(colorMap, maskMap, normalsMap, frameDim,
				ambientIntesity, directIntensity, shadowIntensity, spriteSize, height, threshold, camera.getCamera());
		else maxRenderer.renderBlended(colorMap, maskMap, camera.getCamera());
	}
	public void processLightBuffer(Sprite colorMap, Sprite maskMap, Sprite normalsMap) {
		processLightBuffer(camera, colorMap, maskMap, normalsMap);
	}
	public void processLightBuffer(Sprite colorMap, Sprite maskMap, EscapyGdxCamera cam) {
		processFunc(cam);
		if (normalMappingOn) volRenderer.renderVolumeLights(colorMap, maskMap, normalMapFBO.getSpriteRegion(), frameDim,
				ambientIntesity, directIntensity, shadowIntensity, spriteSize, height, threshold, camera.getCamera());
		else maxRenderer.renderBlended(colorMap, maskMap, camera.getCamera());
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, EscapyGdxCamera camera, Sprite colorMap, Sprite maskMap, Sprite normalsMap) {

		fbo.begin();
		if (normalMappingOn) volRenderer.renderVolumeLights(colorMap, maskMap, normalsMap, frameDim,
				ambientIntesity, directIntensity, shadowIntensity, spriteSize, height, threshold, camera.getCamera());
		else maxRenderer.renderBlended(colorMap, maskMap, camera.getCamera());
		return fbo.end();
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, Sprite colorMap, Sprite maskMap, Sprite normalsMap) {
		return processLightBuffer(fbo, this.camera, colorMap, maskMap, normalsMap);
	}


	public void setNormalsMapRenderer(EscapyMapRenderer normalsMapRenderer) {
		this.normalsMapRenderer = normalsMapRenderer;
		normalMapFBO = new StandartFBO(new int[]{0, 0, (int)frameDim.x, (int)frameDim.y}, "<NORMAL_MAP_FBUFFER>");
	}
	public LightsPostExecutor setFrameDim(TransVec frameDim) {
		return setFrameDim(frameDim.x, frameDim.y);
	}
	public LightsPostExecutor setFrameDim(float[] frameDim) {
		return setFrameDim(frameDim[0], frameDim[1]);
	}
	public LightsPostExecutor setFrameDim(float width, float height) {
		this.frameDim = new TransVec(width, height);
		System.out.println("PostExecDim:\t\t"+width+" : "+height);
		return this;
	}
	public LightsPostExecutor setDirectIntensity(float directIntensity) {
		this.directIntensity = directIntensity;
		System.out.println("directIntensity:\t"+directIntensity);
		return this;
	}
	public LightsPostExecutor setAmbientIntesity(float ambientIntesity) {
		this.ambientIntesity = ambientIntesity;
		System.out.println("ambientIntesity:\t"+ambientIntesity);
		return this;
	}
	public LightsPostExecutor setShadowIntensity(float shadowIntensity) {
		this.shadowIntensity = shadowIntensity;
		System.out.println("shadowIntensity:\t"+shadowIntensity);
		return this;
	}
	public LightsPostExecutor setBlur(boolean blur) {
		this.blur = blur;
		System.out.println("blur:\t\t\t\t"+blur);
		return this;
	}
	public LightsPostExecutor setNormalMappingOn(boolean normalMappingOn) {
		this.normalMappingOn = normalMappingOn;
		System.out.println("Normal mapping:\t\t"+normalMappingOn);
		return this;
	}
	public LightsPostExecutor setSpriteSize(float spriteSize) {
		this.spriteSize = spriteSize;
		System.out.println("nrmlSpiteSize:\t\t"+spriteSize);
		return this;
	}
	public LightsPostExecutor setHeight(float height) {
		System.out.println("height:\t\t\t\t"+ height);
		this.height = height;
		return this;
	}
	public LightsPostExecutor setThreshold(float threshold) {
		System.out.println("threshold:\t\t\t"+ threshold);
		this.threshold = threshold;
		maxRenderer.floatUniforms.set("threshold", threshold);
		return this;
	}
	public LightsPostExecutor setUniformsEnable(boolean enable) {
		System.out.println("uniforms:\t\t\t"+ enable);
		maxRenderer.setCustomUniforms(enable);
		return this;
	}

	public float getDirectIntensity() {
		return directIntensity;
	}
	public float getAmbientIntesity() {
		return ambientIntesity;
	}
	public float getShadowIntensity() {
		return shadowIntensity;
	}
	public float getSpriteSize() {
		return spriteSize;
	}
	public boolean isNormalMappingOn() {
		return normalMappingOn;
	}
	public boolean isBlur() {
		return blur;
	}


	@Override
	public String toString(){
		return "LightPostExecutor_"+Integer.toString(this.hashCode());
	}
}
