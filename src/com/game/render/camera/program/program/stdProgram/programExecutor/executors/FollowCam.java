package com.game.render.camera.program.program.stdProgram.programExecutor.executors;

import com.game.render.camera.program.program.stdProgram.programExecutor.ProgramExecutor;

/**
 * @author Henry on 01/10/16.
 */
public class FollowCam implements ProgramExecutor {

	@Override
	public float calcVector(float camPos, float ownPos, int mvNVec, int[][] borders, float camSpeed) {
		float diff = ownPos - camPos;
		float dl_max = ProgramExecutor.getMax_dl(ownPos, camPos, borders);
		float dl = Math.abs(diff);

		return (dl / dl_max) * (camSpeed * ProgramExecutor.getSign(diff));
	}
}
