package com.game.render.camera.program;

import com.game.render.EscapyGdxCamera;

// TODO: Auto-generated Javadoc
/**
 * The Class CameraProgram.
 *
 * @param <T>
 *            the generic type
 */
public abstract class CameraProgram<T> {

	/** The program target. */
	protected T programTarget;

	/**
	 * Instantiates a new camera program.
	 */
	public CameraProgram() {
		this.programTarget = null;
	}

	/**
	 * Instantiates a new camera program.
	 *
	 * @param programTarget
	 *            the program target
	 */
	public CameraProgram(T programTarget) {
		this.programTarget = programTarget;
	}

	/**
	 * Sets the program target.
	 *
	 * @param programTarget
	 *            the program target
	 * @return the camera program
	 */
	public CameraProgram<T> setProgramTarget(T programTarget) {
		this.programTarget = programTarget;
		return this;
	}

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	protected T getTarget() {
		return this.programTarget;
	}

	/**
	 * Program translate.
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
	public abstract float[] programTranslate(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera);

}
