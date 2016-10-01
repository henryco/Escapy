package com.game.render.camera.program.program.stdProgram;

import com.game.render.camera.program.CameraProgramOwner;
import com.game.render.camera.program.program.AbsCharacterProgram;
import com.game.render.camera.program.program.stdProgram.programExecutor.ProgramExecutor;
import com.game.render.camera.program.program.stdProgram.programExecutor.factory.ProgramExecutors;

/**
 * @author Henry on 28/09/16.
 */
public class StdCameraProgram extends AbsCharacterProgram {

	private ProgramExecutor axisXprogram, axisYprogram;
	public static final ProgramExecutors program = new ProgramExecutors();

	public StdCameraProgram(CameraProgramOwner owner, int scrW, int scrH) {
		super(owner, scrW, scrH);
	}

	public StdCameraProgram(CameraProgramOwner owner, int scrW, int scrH, float il1, float ir1) {
		super(owner, scrW, scrH);
		addBorderIntervalsOX(il1, ir1);
	}
	public StdCameraProgram(CameraProgramOwner owner, int scrW, int scrH, float il1, float ir1, float id1, float iu1) {
		super(owner, scrW, scrH);
		addBorderIntervalsOX(il1, ir1);
		addBorderIntervalsOY(id1, iu1);
	}

	@Override
	public float[] executeCameraProgram(float camx, float camy) {

		float[] transBuff = new float[2];
		float[] moveNVec = super.owner.getCameraVector().getMoveNVector();
		float[] ownPos = super.owner.getOwnerPosition();

		if (translateOX) transBuff[0] = axisXprogram.calcVector(camx, ownPos[0], moveNVec[0], borderIntervalsOX, super.cameraSpeed);
		if (translateOY) transBuff[1] = axisYprogram.calcVector(camy, ownPos[1], moveNVec[1], borderIntervalsOY, super.cameraSpeed);
		return transBuff;
	}

	public AbsCharacterProgram setXProgram(ProgramExecutor program) {
		this.axisXprogram = program;
		translateOX(true);
		return this;
	}

	public AbsCharacterProgram setYProgram(ProgramExecutor program) {
		this.axisYprogram = program;
		translateOY(true);
		return this;
	}

	public AbsCharacterProgram setXYProgram(ProgramExecutor programX, ProgramExecutor programY) {
		this.axisXprogram = programX;
		this.axisYprogram = programY;
		translateOX(true);
		translateOY(true);
		return this;
	}

}
