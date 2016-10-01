package com.game.render.camera.program.program.stdProgram.programExecutor.executors;

import com.game.render.camera.program.program.stdProgram.programExecutor.ProgramExecutor;

/**
 * @author Henry on 01/10/16.
 */
public class FastVectorCam implements ProgramExecutor {

	@Override
	public float calcVector(float camPos, float ownPos, int mvNVec, int[][] borders, float camSpeed) {
		float diff = ownPos - camPos;
		float vec_ = mvNVec * camSpeed;
		float dl_max = ProgramExecutor.getMax_dl(ownPos, camPos, borders);
		float dl = Math.abs(diff);
		float counterVec_ = (dl / dl_max) * (camSpeed * ProgramExecutor.getSign(diff));

		return vec_ + counterVec_;
	}
}
