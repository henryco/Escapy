package com.game.render.fbo.psProcess.lights.volLight.userState;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.EscapyGdxCamera;
import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.shader.EscapyStdShaderRenderer;
import com.game.render.shader.volumeLight.EscapyVolumeRenderer;
import com.game.render.shader.volumeLight.userState.EscapyStdVolumeRenderer;
import com.game.screens.userState.EscapyGameScreen;
import com.game.utils.translationVec.TransVec;

import java.util.function.Consumer;

/**
 * @author Henry on 26/09/16.
 */
public class LightsPostExecutor {

	private TransVec frameDim;
	private EscapyGdxCamera camera;
	private EscapyVolumeRenderer volRenderer;
	private EscapyStdShaderRenderer stdRenderer;

	private boolean normalMappingOn = true;

	private float lightIntensity, ambientIntesity;

	{
		this.lightIntensity = 0.2f;
		this.ambientIntesity = 0.75f;
		this.volRenderer = new EscapyStdVolumeRenderer();
		this.stdRenderer = new EscapyStdShaderRenderer();
	}

	public LightsPostExecutor(float frameWidth, float frameHeight) {
		frameDim = new TransVec(frameWidth, frameHeight);
		camera = new EscapyGdxCamera((int) frameWidth, (int) frameHeight);
	}
	public LightsPostExecutor(float[] dim) {
		frameDim = new TransVec(dim);
		camera = new EscapyGdxCamera((int) dim[0], (int) dim[1]);
	}
	public LightsPostExecutor(TransVec dim) {
		frameDim = new TransVec(dim);
		camera = new EscapyGdxCamera((int) dim.x, (int) dim.y);
	}

	public void prepareMaps(EscapyGdxCamera camera, ExtraRenderContainer normalsMapContainer, EscapyFBO nrmlMapFBO,
							ExtraRenderContainer lightMapContainer, EscapyFBO lightMapFBO) {

	}


	@SafeVarargs
	public final void containerFunc(EscapyGameScreen instance, Consumer<EscapyGameScreen>... func) {
		if (normalMappingOn) for (Consumer<EscapyGameScreen> f : func) f.accept(instance);
	}

	public void processLightBuffer(EscapyGdxCamera camera, Sprite colorMap, Sprite lightMap, Sprite normalsMap) {

		if (normalMappingOn) volRenderer.renderVolumeLights(colorMap, normalsMap, lightMap, frameDim, ambientIntesity, lightIntensity, camera.getCamera());
		else stdRenderer.drawSprite(colorMap, camera.getCamera());
	}
	public void processLightBuffer(Sprite colorMap, Sprite lightMap, Sprite normalsMap) {
		processLightBuffer(camera, colorMap, lightMap, normalsMap);
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, EscapyGdxCamera camera, Sprite colorMap, Sprite lightMap, Sprite normalsMap) {

		fbo.begin();
		if (normalMappingOn) volRenderer.renderVolumeLights(colorMap, normalsMap, lightMap, frameDim, ambientIntesity, lightIntensity, camera.getCamera());
		else stdRenderer.drawSprite(colorMap, camera.getCamera());
		return fbo.end();
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, Sprite colorMap, Sprite lightMap, Sprite normalsMap) {
		processLightBuffer(fbo, this.camera, colorMap, lightMap, normalsMap);
		return fbo;
	}



	public boolean isNormalMappingOn() {
		return normalMappingOn;
	}
	public LightsPostExecutor setNormalMappingOn(boolean normalMappingOn) {
		this.normalMappingOn = normalMappingOn;
		return this;
	}
	public LightsPostExecutor setFrameDim(TransVec frameDim) {
		this.frameDim = new TransVec(frameDim);
		return this;
	}
	public LightsPostExecutor setFrameDim(float[] frameDim) {
		this.frameDim = new TransVec(frameDim);
		return this;
	}
	public LightsPostExecutor setFrameDim(float width, float height) {
		this.frameDim = new TransVec(width, height);
		return this;
	}
	public LightsPostExecutor setLightIntensity(float lightIntensity) {
		this.lightIntensity = lightIntensity;
		return this;
	}
	public LightsPostExecutor setAmbientIntesity(float ambientIntesity) {
		this.ambientIntesity = ambientIntesity;
		return this;
	}
	public float getLightIntensity() {
		return lightIntensity;
	}
	public float getAmbientIntesity() {
		return ambientIntesity;
	}

	@Override
	public String toString(){
		return "LightPostExecutor_"+Integer.toString(this.hashCode());
	}
}
