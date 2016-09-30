package com.game.render.camera.program.holder;

import java.util.ArrayList;
import java.util.List;

import com.game.render.camera.EscapyGdxCamera;
//import com.game.render.camera.program.CameraProgram;
//import com.game.render.camera.program.owner.CameraProgramOwner;
import com.game.render.camera.program.CameraProgram;


// TODO: Auto-generated Javadoc
/**
 * The Class CameraProgramHolder.
 */
public class CameraProgramHolder {

	//private List<CameraProgramOwner> camProgramHolders;
	//private CameraProgramOwner actualProgramHolder = null;

	private List<CameraProgram<float[]>> camProgramHolders;
	private CameraProgram<float[]> actualProgramHolder;

	private float[] voidArray = new float[]{0, 0};
	
	/**
	 * Instantiates a new camera program holder.
	 */
	public CameraProgramHolder() 
	{
		this.camProgramHolders = new ArrayList<>();
		return;
	}
	

	public CameraProgramHolder(CameraProgram<float[]> camProgram)
	{
		/*
		this.camProgramHolders = new ArrayList<>();
		this.camProgramHolders.add(new CameraProgramOwner(camProgram));
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size()-1);
		*/
		this.camProgramHolders = new ArrayList<>();
		this.camProgramHolders.add(camProgram);
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size() - 1);
		return;
	}

	synchronized public float[] holdCamera(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera)
	{
		if (this.actualProgramHolder == null)
			return voidArray;
		//return this.actualProgramHolder.executeCameraProgram(sWidth, sHeight, scale_optional, escapyCamera);
		return actualProgramHolder.executeCameraProgram(escapyCamera.getCamera().position.x, escapyCamera.getCamera().position.y);
	}


	synchronized public int addCameraProgram(CameraProgram<float[]> camProgram)
	{
		/*
		this.camProgramHolders.add(new CameraProgramOwner(camProgram));
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size()-1);
		return actualProgramHolder.getID();
		*/
		this.camProgramHolders.add(camProgram);
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size() - 1);
		return actualProgramHolder.getID();
	}
	
	

	synchronized public CameraProgramHolder removeCameraProgram(int programID)
	{
		//CameraProgramOwner programHolder = this.getCameraProgramOwnerByID(programID);
		CameraProgram programHolder = this.getCameraProgramOwnerByID(programID);
		if (programHolder != null)
		{
			this.camProgramHolders.remove(programHolder);
			if (this.camProgramHolders.isEmpty())
				this.actualProgramHolder = null;
		}
		return this;
	}
	

	synchronized public CameraProgramHolder setActualCameraProgram(int programID)
	{
		//CameraProgramOwner programHolder = this.getCameraProgramOwnerByID(programID);
		CameraProgram programHolder = this.getCameraProgramOwnerByID(programID);
		if (programHolder != null)
			this.actualProgramHolder = programHolder;
		return this;
	}
	

	public CameraProgramHolder setActualCameraProgram(CameraProgram<float[]> camProgram) {

		return setActualCameraProgram(camProgram.getID());
		//return setActualCameraProgram(new CameraProgramOwner(camProgram).getID());
	}

	public CameraProgramHolder removeCameraProgram(CameraProgram<float[]> camProgram) {
		
	//	return removeCameraProgram(new CameraProgramOwner(camProgram).getID());
		return removeCameraProgram(camProgram.getID());
	}

	synchronized public CameraProgram getCameraProgramOwnerByID(int programID)
	{
		for (CameraProgram programHolder : camProgramHolders)
			if (programHolder.getID() == programID)
				return programHolder;
		return null;
	}

	/*
	synchronized public CameraProgramOwner getCameraProgramOwnerByID(int programID)
	{
		for (CameraProgramOwner programHolder : camProgramHolders)
			if (programHolder.getID() == programID)
				return programHolder;
		return null;
	}
	*/
}
