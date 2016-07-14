package com.game.render.camera.holder;

import com.game.render.EscapyGdxCamera;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyCameraProgramHolder.
 */
public interface EscapyCameraProgramHolder {

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

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public abstract int getID();
}
