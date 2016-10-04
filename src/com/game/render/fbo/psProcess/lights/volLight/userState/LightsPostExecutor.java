package com.game.render.fbo.psProcess.lights.volLight.userState;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.EscapyMapRenderer;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.shader.EscapyStdShaderRenderer;
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
	private EscapyStdShaderRenderer stdRenderer;

	private boolean normalMappingOn, blur;

	private EscapyFBO normalMapFBO;
	private EscapyMapRenderer normalsMapRenderer;

	private float directIntensity, ambientIntesity, shadowIntensity, spriteSize, lumimance;

	{
		this.directIntensity = 0.2f;
		this.ambientIntesity = 0.75f;
		this.shadowIntensity = 8;
		this.spriteSize = 1;
		this.lumimance = 0;

		this.normalMappingOn = true;
		this.blur = false;

		this.volRenderer = new EscapyStdVolumeRenderer();
		this.stdRenderer = new EscapyStdShaderRenderer();
	}

	public LightsPostExecutor(float frameWidth, float frameHeight, EscapyMapRenderer normalsMapRenderer) {
		setFrameDim(new TransVec(frameWidth, frameHeight));
		camera = new EscapyGdxCamera((int) frameWidth, (int) frameHeight);
		normalMapFBO = new StandartFBO(new int[]{0, 0, (int) frameWidth, (int) frameHeight}, "<NORMAL_MAP_FBUFFER>");
		setNormalsMapRenderer(normalsMapRenderer);
	}
	public LightsPostExecutor(float[] dim, EscapyMapRenderer normalsMapRenderer) {
		setFrameDim(new TransVec(dim));
		camera = new EscapyGdxCamera((int) dim[0], (int) dim[1]);
		normalMapFBO = new StandartFBO(new int[]{0, 0, (int) dim[0], (int) dim[1]}, "<NORMAL_MAP_FBUFFER>");
		setNormalsMapRenderer(normalsMapRenderer);
	}
	public LightsPostExecutor(TransVec dim, EscapyMapRenderer normalsMapRenderer) {
		setFrameDim(new TransVec(dim));
		camera = new EscapyGdxCamera((int) dim.x, (int) dim.y);
		normalMapFBO = new StandartFBO(new int[]{0, 0, (int)dim.x, (int)dim.y}, "<NORMAL_MAP_FBUFFER>");
		setNormalsMapRenderer(normalsMapRenderer);
	}


	public void processFunc(EscapyGdxCamera camera) {
		if (normalMappingOn) {
			normalMapFBO.begin().clearFBO(0.502f, 0.502f, 1f, 1f);
			normalsMapRenderer.renderTextureMap(camera);
			normalMapFBO.end();
		}
	}

	public void processLightBuffer(EscapyGdxCamera camera, Sprite colorMap, Sprite normalsMap) {

		stdRenderer.drawSprite(colorMap, camera.getCamera());
		if (normalMappingOn) volRenderer.renderVolumeLights(colorMap, normalsMap, frameDim,
				ambientIntesity, directIntensity, shadowIntensity, spriteSize, lumimance, camera.getCamera());
	}
	public void processLightBuffer(Sprite colorMap, Sprite normalsMap) {
		processLightBuffer(camera, colorMap, normalsMap);
	}
	public void processLightBuffer(Sprite colorMap, EscapyGdxCamera cam) {
		processFunc(cam);
		processLightBuffer(camera, colorMap, normalMapFBO.getSpriteRegion());
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, EscapyGdxCamera camera, Sprite colorMap, Sprite normalsMap) {

		fbo.begin();
		stdRenderer.drawSprite(colorMap, camera.getCamera());
		if (normalMappingOn) volRenderer.renderVolumeLights(colorMap, normalsMap, frameDim,
				ambientIntesity, directIntensity, shadowIntensity, spriteSize, lumimance, camera.getCamera());
		return fbo.end();
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, Sprite colorMap, Sprite normalsMap) {
		return processLightBuffer(fbo, this.camera, colorMap, normalsMap);
	}


	public void setNormalsMapRenderer(EscapyMapRenderer normalsMapRenderer) {
		this.normalsMapRenderer = normalsMapRenderer;
	}
	public LightsPostExecutor setFrameDim(TransVec frameDim) {
		return setFrameDim(frameDim.x, frameDim.y);
	}
	public LightsPostExecutor setFrameDim(float[] frameDim) {
		return setFrameDim(frameDim[0], frameDim[1]);
	}
	public LightsPostExecutor setFrameDim(float width, float height) {
		this.frameDim = new TransVec(width, height);
		System.out.println("NrmlMapSize:\t\t"+width+" : "+height);
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
	public LightsPostExecutor setLumimance(float lumimance) {
		System.out.println("luminance:\t\t\t"+lumimance);
		this.lumimance = lumimance;
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
