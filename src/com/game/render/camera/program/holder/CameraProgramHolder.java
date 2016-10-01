package com.game.render.camera.program.holder;

import java.util.ArrayList;
import java.util.List;
import com.game.render.camera.program.CameraProgram;


// TODO: Auto-generated Javadoc
/**
 * The Class CameraProgramHolder.
 */
public class CameraProgramHolder {

	private List<CameraProgram<float[]>> camProgramHolders;
	private CameraProgram<float[]> actualProgramHolder;

	private float[] voidArray = new float[]{0, 0};
	
	/**
	 * Instantiates a new camera program holder.
	 */
	public CameraProgramHolder() {
		this.camProgramHolders = new ArrayList<>();
		return;
	}
	

	public CameraProgramHolder(CameraProgram<float[]> camProgram) {
		this.camProgramHolders = new ArrayList<>();
		this.camProgramHolders.add(camProgram);
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size() - 1);
		return;
	}

	synchronized public float[] holdCamera(float camXpos, float camYpos) {
		if (this.actualProgramHolder == null)
			return voidArray;
		return actualProgramHolder.executeCameraProgram(camXpos, camYpos);
	}


	synchronized public int addCameraProgram(CameraProgram<float[]> camProgram) {
		this.camProgramHolders.add(camProgram);
		this.actualProgramHolder = camProgramHolders.get(camProgramHolders.size() - 1);
		return actualProgramHolder.getID();
	}
	
	

	synchronized public CameraProgramHolder removeCameraProgram(int programID) {
		CameraProgram programHolder = this.getCameraProgramOwnerByID(programID);
		if (programHolder != null)
		{
			this.camProgramHolders.remove(programHolder);
			if (this.camProgramHolders.isEmpty())
				this.actualProgramHolder = null;
		}
		return this;
	}
	

	synchronized public CameraProgramHolder setActualCameraProgram(int programID) {
		CameraProgram programHolder = this.getCameraProgramOwnerByID(programID);
		if (programHolder != null)
			this.actualProgramHolder = programHolder;
		return this;
	}
	

	public CameraProgramHolder setActualCameraProgram(CameraProgram<float[]> camProgram) {
		return setActualCameraProgram(camProgram.getID());
	}

	public CameraProgramHolder removeCameraProgram(CameraProgram<float[]> camProgram) {
		return removeCameraProgram(camProgram.getID());
	}

	synchronized public CameraProgram getCameraProgramOwnerByID(int programID)
	{
		for (CameraProgram programHolder : camProgramHolders)
			if (programHolder.getID() == programID)
				return programHolder;
		return null;
	}

}
