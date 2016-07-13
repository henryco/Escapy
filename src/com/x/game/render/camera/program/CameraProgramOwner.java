package com.x.game.render.camera.program;

import com.x.game.render.EscapyGdxCamera;

public class CameraProgramOwner implements EscapyCameraProgramOwner {

	protected int camOwnerID;
	protected CameraProgram<?> camProgram;

	public CameraProgramOwner() {

	}

	public CameraProgramOwner(CameraProgram<?> camProgram) {
		this.camProgram = camProgram;
		this.camOwnerID = camProgram.hashCode();
	}

	public CameraProgramOwner(CameraProgram<?> camProgram, int ownerID) {
		this.camProgram = camProgram;
		this.camOwnerID = ownerID;
	}

	@Override
	public CameraProgram<?> getCameraProgram() {
		return camProgram;
	}

	@Override
	public int getID() {
		return camOwnerID;
	}

	@Override
	public float[] executeCameraProgram(int sWidth, int sHeight, float scale_optional, EscapyGdxCamera escapyCamera) {
		return this.camProgram.programTranslate(sWidth, sHeight, scale_optional, escapyCamera);
	}

}
