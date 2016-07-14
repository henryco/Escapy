package com.game.render.camera.program;

import com.game.render.EscapyGdxCamera;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyCameraProgramOwner.
 */
public interface EscapyCameraProgramOwner {

	/**
	 * Gets the camera program.
	 *
	 * @return the camera program
	 */
	public abstract CameraProgram<?> getCameraProgram();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public abstract int getID();

	/**
	 * Execute camera program.
	 *
	 * @param sWidth
	 *            the s width
	 * @param sHeight
	 *            the s height
	 * @param scale_optional
	 *            the scale optional
	 * @param escapyCamera
	 *            the escapy camera
	 * @return the float[]
	 */
	public abstract float[] executeCameraProgram(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera);
}
