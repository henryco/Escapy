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
	private float[] minTranslation = new float[2];
	private float[] correction = new float[2];

	public StdCameraProgram(CameraProgramOwner owner, float scrW, float scrH) {
		this(scrW, scrH);
		super.setOwner(owner);
	}
	public StdCameraProgram(CameraProgramOwner owner, float scrW, float scrH, float il1, float ir1) {
		this(scrW, scrH, il1, ir1);
		super.setOwner(owner);
	}
	public StdCameraProgram(CameraProgramOwner owner, float scrW, float scrH, float il1, float ir1, float id1, float iu1) {
		this(scrW, scrH, il1, ir1, id1, iu1);
		super.setOwner(owner);
	}
	public StdCameraProgram(float scrW, float scrH) {
		super(scrW, scrH);
		System.out.println("INSTANCE::STD::CAMERA "+scrW+" : "+scrH);
	}
	public StdCameraProgram(float scrW, float scrH, float il1, float ir1) {
		super(scrW, scrH);
		addBorderIntervalsOX(il1, ir1);
		System.out.println("INSTANCE::STD::CAMERA "+scrW+" : "+scrH+" : "+il1+" : "+ir1);
	}
	public StdCameraProgram(float scrW, float scrH, float il1, float ir1, float id1, float iu1) {
		super(scrW, scrH);
		addBorderIntervalsOX(il1, ir1);
		addBorderIntervalsOY(id1, iu1);
		System.out.println("INSTANCE::STD::CAMERA "+scrW+" : "+scrH+" : "+il1+" : "+ir1+" : "+id1+" : "+iu1);
	}

	@Override
	public float[] executeCameraProgram(float camx, float camy) {

		float[] transBuff = new float[2];
		float[] moveNVec = super.owner.getCameraVector().getMoveNVector();
		float[] ownPos = super.owner.getOwnerPosition();

		ownPos[0] += correction[0];
		ownPos[1] += correction[1];

		float xt = 0;
		float yt = 0;

		if (translateOX) xt = axisXprogram.calcVector(camx, ownPos[0], moveNVec[0], borderIntervalsOX, super.cameraSpeed);
		if (translateOY) yt = axisYprogram.calcVector(camy, ownPos[1], moveNVec[1], borderIntervalsOY, super.cameraSpeed);

		if (Math.abs(xt) >= minTranslation[0]) transBuff[0] = xt;
		if (Math.abs(yt) >= minTranslation[1]) transBuff[1] = yt;

		return transBuff;
	}

	public StdCameraProgram setXProgram(ProgramExecutor program) {
		this.axisXprogram = program;
		translateOX(true);
		return this;
	}

	public StdCameraProgram setYProgram(ProgramExecutor program) {
		this.axisYprogram = program;
		translateOY(true);
		return this;
	}

	public StdCameraProgram setXYProgram(ProgramExecutor programX, ProgramExecutor programY) {
		this.axisXprogram = programX;
		this.axisYprogram = programY;
		translateOX(true);
		translateOY(true);
		return this;
	}

	public StdCameraProgram setMinTranslations(float x, float y) {
		minTranslation = new float[]{x, y};
		return this;
	}

	public StdCameraProgram setCorrection(float p_x, float py) {
		correction = new float[]{p_x, py};
		return this;
	}
}
