package com.game.render.camera.holder;

import java.util.ArrayList;
import java.util.List;

import com.game.render.EscapyGdxCamera;
import com.game.render.camera.program.CameraProgram;
import com.game.render.camera.program.CameraProgramOwner;


// TODO: Auto-generated Javadoc
/**
 * The Class CameraProgramHolder.
 */
public class CameraProgramHolder {

	private List<CameraProgramOwner> camProgramHolders;
	private CameraProgramOwner actualProgramHolder = null;
	private float[] voidArray = new float[]{0, 0};
	
	/**
	 * Instantiates a new camera program holder.
	 */
	public CameraProgramHolder() 
	{
		this.camProgramHolders = new ArrayList<>();
		return;
	}
	
	/**
	 * Instantiates a new camera program holder.
	 *
	 * @param camProgram
	 *            the cam program
	 */
	public CameraProgramHolder(CameraProgram<?> camProgram)
	{
		this.camProgramHolders = new ArrayList<>();
		this.camProgramHolders.add(new CameraProgramOwner(camProgram));
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size()-1);
		return;
	}

	/**
	 * Hold camera.
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
	synchronized public float[] holdCamera(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera)
	{
		if (this.actualProgramHolder == null)
			return voidArray;
		return this.actualProgramHolder.executeCameraProgram(sWidth, sHeight, scale_optional, escapyCamera);
	}

	/**
	 * Adds the camera program.
	 *
	 * @param camProgram
	 *            the cam program
	 * @return the int
	 */
	synchronized public int addCameraProgram(CameraProgram<?> camProgram)
	{
		this.camProgramHolders.add(new CameraProgramOwner(camProgram));
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size()-1);
		return actualProgramHolder.getID();
	}
	
	
	/**
	 * Removes the camera program.
	 *
	 * @param programID
	 *            the program ID
	 * @return the camera program holder
	 */
	synchronized public CameraProgramHolder removeCameraProgram(int programID)
	{
		CameraProgramOwner programHolder = this.getCameraProgramOwnerByID(programID);
		if (programHolder != null)
		{
			this.camProgramHolders.remove(programHolder);
			if (this.camProgramHolders.isEmpty())
				this.actualProgramHolder = null;
		}
		return this;
	}
	
	/**
	 * Sets the actual camera program.
	 *
	 * @param programID
	 *            the program ID
	 * @return the camera program holder
	 */
	synchronized public CameraProgramHolder setActualCameraProgram(int programID)
	{
		CameraProgramOwner programHolder = this.getCameraProgramOwnerByID(programID);
		if (programHolder != null)
			this.actualProgramHolder = programHolder;
		return this;
	}
	
	/**
	 * Sets the actual camera program.
	 *
	 * @param camProgram
	 *            the cam program
	 * @return the camera program holder
	 */
	public CameraProgramHolder setActualCameraProgram(CameraProgram<?> camProgram) {
		
		return setActualCameraProgram(new CameraProgramOwner(camProgram).getID());
	}
	
	/**
	 * Removes the camera program.
	 *
	 * @param camProgram
	 *            the cam program
	 * @return the camera program holder
	 */
	public CameraProgramHolder removeCameraProgram(CameraProgram<?> camProgram) {
		
		return removeCameraProgram(new CameraProgramOwner(camProgram).getID());
	}
	
	/**
	 * Gets the camera program owner by ID.
	 *
	 * @param programID
	 *            the program ID
	 * @return the camera program owner by ID
	 */
	synchronized public CameraProgramOwner getCameraProgramOwnerByID(int programID)
	{
		for (CameraProgramOwner programHolder : camProgramHolders)
			if (programHolder.getID() == programID)
				return programHolder;
		return null;
	}
	
}
