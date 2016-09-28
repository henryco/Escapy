package com.game.render.camera.program.owner;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.camera.program.CameraProgram;

// TODO: Auto-generated Javadoc
/**
 * The Class CameraProgramOwner.
 */
public class CameraProgramOwner implements EscapyCameraProgramOwner {

	/** The cam owner ID. */
	protected int camOwnerID;
	
	/** The cam program. */
	protected CameraProgram<?> camProgram;

	/**
	 * Instantiates a new camera program owner.
	 */
	public CameraProgramOwner() {

	}

	/**
	 * Instantiates a new camera program owner.
	 *
	 * @param camProgram
	 *            the cam program
	 */
	public CameraProgramOwner(CameraProgram<?> camProgram) {
		this.camProgram = camProgram;
		this.camOwnerID = camProgram.hashCode();
	}

	/**
	 * Instantiates a new camera program owner.
	 *
	 * @param camProgram
	 *            the cam program
	 * @param ownerID
	 *            the owner ID
	 */
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
