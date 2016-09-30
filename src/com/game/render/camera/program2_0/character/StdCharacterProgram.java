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

		System.out.println(" ");
		System.out.println("CMX: "+ camx);
		System.out.println("CMY: "+ camy);
		System.out.println("NVX: "+moveNVec[0]);
		System.out.println("NVY: "+moveNVec[1]);
		System.out.println("OWX: "+ownPos[0]);
		System.out.println("OWY: "+ownPos[1]);

		transBuff[0] = calcVector(camx, ownPos[0], moveNVec[0], borderIntervalsOX, 1, super.cameraSpeed);
		//transBuff[1] = calcVector(camy, ownPos[1], moveNVec[1], borderIntervalsOY, 1, super.cameraSpeed);
		return transBuff;
	}

	private static float calcVector(float camPos, float ownPos, int mvNVec, int[][] borders, int signFac, float camSpeed) {
		float vec_ = mvNVec * camSpeed;
		float dl_max = getMax_dl(ownPos, camPos, borders, signFac);
		float dl = Math.abs(ownPos - camPos);
		float tmp = (dl / dl_max);
		float counterVec_ = (dl / dl_max) * (-1) * vec_;

		System.out.println("vec_: "+vec_);
		System.out.println("dl_max: "+dl_max);
		System.out.println("dl: "+dl);
		System.out.println("tm: "+tmp);
		System.out.println("counterVec_: "+counterVec_);
		float ou = vec_ + counterVec_;
		System.out.println("OUT: "+ou);
		return vec_ + counterVec_;
	}

	private static float getMax_dl(float ownPos, float camPos, int[][] borders, int fac){
		return (ownPos - camPos) > 0 ? borders[0][1]*fac : borders[0][0]*fac;
	}


}
