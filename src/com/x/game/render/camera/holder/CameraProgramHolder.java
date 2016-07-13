package com.x.game.render.camera.holder;

import java.util.ArrayList;
import java.util.List;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.camera.program.CameraProgram;
import com.x.game.render.camera.program.CameraProgramOwner;


public class CameraProgramHolder {

	private List<CameraProgramOwner> camProgramHolders;
	private CameraProgramOwner actualProgramHolder = null;
	private float[] voidArray = new float[]{0, 0};
	
	public CameraProgramHolder() 
	{
		this.camProgramHolders = new ArrayList<>();
		return;
	}
	public CameraProgramHolder(CameraProgram<?> camProgram)
	{
		this.camProgramHolders = new ArrayList<>();
		this.camProgramHolders.add(new CameraProgramOwner(camProgram));
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size()-1);
		return;
	}

	synchronized public float[] holdCamera(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera)
	{
		if (this.actualProgramHolder == null)
			return voidArray;
		return this.actualProgramHolder.executeCameraProgram(sWidth, sHeight, scale_optional, escapyCamera);
	}

	synchronized public int addCameraProgram(CameraProgram<?> camProgram)
	{
		this.camProgramHolders.add(new CameraProgramOwner(camProgram));
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size()-1);
		return actualProgramHolder.getID();
	}
	
	
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
	
	synchronized public CameraProgramHolder setActualCameraProgram(int programID)
	{
		CameraProgramOwner programHolder = this.getCameraProgramOwnerByID(programID);
		if (programHolder != null)
			this.actualProgramHolder = programHolder;
		return this;
	}
	
	public CameraProgramHolder setActualCameraProgram(CameraProgram<?> camProgram) {
		
		return setActualCameraProgram(new CameraProgramOwner(camProgram).getID());
	}
	
	public CameraProgramHolder removeCameraProgram(CameraProgram<?> camProgram) {
		
		return removeCameraProgram(new CameraProgramOwner(camProgram).getID());
	}
	
	synchronized public CameraProgramOwner getCameraProgramOwnerByID(int programID)
	{
		for (CameraProgramOwner programHolder : camProgramHolders)
			if (programHolder.getID() == programID)
				return programHolder;
		return null;
	}
	
}
