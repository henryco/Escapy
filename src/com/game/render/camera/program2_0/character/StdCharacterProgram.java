package com.game.render.camera.program2_0.character;

import com.game.render.camera.program2_0.CameraProgramOwner;

/**
 * @author Henry on 28/09/16.
 */
public class StdCharacterProgram extends AbsCharacterProgram {

	public StdCharacterProgram(CameraProgramOwner owner, int scrW, int scrH) {
		super(owner, scrW, scrH);
	}

	public StdCharacterProgram(CameraProgramOwner owner, int scrW, int scrH, float il1, float ir1) {
		super(owner, scrW, scrH);
		addBorderIntervalsOX(il1, ir1);
		addBorderIntervalsOY(0,0);
	}

	@Override
	public float[] executeCameraProgram(float camx, float camy) {

		float[] transBuff = new float[2];
		int[] moveNVec = super.owner.getMoveNVector();
		float[] ownPos = super.owner.getOwnerPosition();

		transBuff[0] = calcVector(camx, ownPos[0], moveNVec[0], borderIntervalsOX, 1, super.cameraSpeed);
		//transBuff[1] = calcVector(camy, ownPos[1], moveNVec[1], borderIntervalsOY, -1, super.cameraSpeed);
		return transBuff;
	}

	protected float calcVector(float camPos, float ownPos, int mvNVec, int[][] borders, int signFac, float camSpeed) {
		float diff = ownPos - camPos;
		float vec_ = mvNVec * camSpeed;
		float dl_max = getMax_dl(ownPos, camPos, borders, signFac);
		float dl = Math.abs(diff);
		float counterVec_ = (dl / dl_max) * (camSpeed * getSign(diff));

		return vec_ + counterVec_;
	}

}
