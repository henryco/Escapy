package com.game.render.camera.program;

/**
 * @author Henry on 28/09/16.
 */
public interface CameraProgram <T> {

	T executeCameraProgram(float camx, float camy);
	CameraProgram setCameraSpeed(float cameraSpeed);
	int getID();

}
