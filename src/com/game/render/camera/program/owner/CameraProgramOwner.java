package com.game.render.camera.program.owner;

import com.game.render.EscapyGdxCamera;
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

	/* (non-Javadoc)
	 * @see com.game.render.camera.program.EscapyCameraProgramOwner#getCameraProgram()
	 */
	@Override
	public CameraProgram<?> getCameraProgram() {
		return camProgram;
	}

	/* (non-Javadoc)
	 * @see com.game.render.camera.program.EscapyCameraProgramOwner#getID()
	 */
	@Override
	public int getID() {
		return camOwnerID;
	}

	/* (non-Javadoc)
	 * @see com.game.render.camera.program.EscapyCameraProgramOwner#executeCameraProgram(int, int, float, com.game.render.EscapyGdxCamera)
	 */
	@Override
	public float[] executeCameraProgram(int sWidth, int sHeight, float scale_optional, EscapyGdxCamera escapyCamera) {
		return this.camProgram.programTranslate(sWidth, sHeight, scale_optional, escapyCamera);
	}

}
