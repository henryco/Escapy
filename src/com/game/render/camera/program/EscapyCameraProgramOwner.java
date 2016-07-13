package com.game.render.camera.program;

import com.game.render.EscapyGdxCamera;

public interface EscapyCameraProgramOwner {

	public abstract CameraProgram<?> getCameraProgram();

	public abstract int getID();

	public abstract float[] executeCameraProgram(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera);
}
