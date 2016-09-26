package com.game.render.fbo.psProcess.lights.volLight.userState;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.shader.volumeLight.EscapyVolumeRenderer;
import com.game.render.shader.volumeLight.userState.EscapyStdVolumeRenderer;
import com.game.utils.translationVec.TransVec;

/**
 * @author Henry on 26/09/16.
 */
public class LightPostExecutor {

	private TransVec frameDim;
	private EscapyVolumeRenderer volRenderer;
	private EscapyGdxCamera camera;

	private float lightIntensity, ambientIntesity;

	{
		this.lightIntensity = 0.2f;
		this.ambientIntesity = 0.75f;
		this.volRenderer = new EscapyStdVolumeRenderer();
	}

	public LightPostExecutor(float frameWidth, float frameHeight) {
		frameDim = new TransVec(frameWidth, frameHeight);
		camera = new EscapyGdxCamera((int) frameWidth, (int) frameHeight);
	}
	public LightPostExecutor(float[] dim) {
		frameDim = new TransVec(dim);
		camera = new EscapyGdxCamera((int) dim[0], (int) dim[1]);
	}
	public LightPostExecutor(TransVec dim) {
		frameDim = new TransVec(dim);
		camera = new EscapyGdxCamera((int) dim.x, (int) dim.y);
	}


	public void processLightBuffer(EscapyGdxCamera camera, Sprite colorMap, Sprite lightMap, Sprite normalsMap) {
		this.volRenderer.renderVolumeLights(0, 0, colorMap, normalsMap, lightMap, frameDim, ambientIntesity, lightIntensity, camera.getCamera());

	}
	public void processLightBuffer(Sprite colorMap, Sprite lightMap, Sprite normalsMap) {
		processLightBuffer(camera, colorMap, lightMap, normalsMap);
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, EscapyGdxCamera camera, Sprite colorMap, Sprite lightMap, Sprite normalsMap) {
		fbo.begin();
		processLightBuffer(camera, colorMap, lightMap, normalsMap);
		return fbo.end();
	}
	public EscapyFBO processLightBuffer(EscapyFBO fbo, Sprite colorMap, Sprite lightMap, Sprite normalsMap) {
		processLightBuffer(fbo, this.camera, colorMap, lightMap, normalsMap);
		return fbo;
	}







	public LightPostExecutor setFrameDim(TransVec frameDim) {
		this.frameDim = new TransVec(frameDim);
		return this;
	}
	public LightPostExecutor setFrameDim(float[] frameDim) {
		this.frameDim = new TransVec(frameDim);
		return this;
	}
	public LightPostExecutor setFrameDim(float width, float height) {
		this.frameDim = new TransVec(width, height);
		return this;
	}
	public LightPostExecutor setLightIntensity(float lightIntensity) {
		this.lightIntensity = lightIntensity;
		return this;
	}
	public LightPostExecutor setAmbientIntesity(float ambientIntesity) {
		this.ambientIntesity = ambientIntesity;
		return this;
	}
	public float getLightIntensity() {
		return lightIntensity;
	}
	public float getAmbientIntesity() {
		return ambientIntesity;
	}
}
