package com.game.render.camera.program2_0.character;

import com.game.render.camera.program2_0.CameraProgramOwner;

/**
 * @author Henry on 30/09/16.
 */
public class FollowCharacterProgram extends StdCharacterProgram {

	public FollowCharacterProgram(CameraProgramOwner owner, int scrW, int scrH) {
		super(owner, scrW, scrH);
	}

	public FollowCharacterProgram(CameraProgramOwner owner, int scrW, int scrH, float il1, float ir1) {
		super(owner, scrW, scrH, il1, ir1);
	}

	@Override
	protected float calcVector(float camPos, float ownPos, int mvNVec, int[][] borders, int signFac, float camSpeed) {
		float diff = ownPos - camPos;
		float dl_max = getMax_dl(ownPos, camPos, borders, signFac);
		float dl = Math.abs(diff);

		return (dl / dl_max) * (camSpeed * getSign(diff));
	}
}
