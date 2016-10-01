package com.game.render.camera.program.program.stdProgram.programExecutor;

/**
 * @author Henry on 01/10/16.
 */
public interface ProgramExecutor {

	float calcVector(float camPos, float ownPos, int mvNVec, int[][] borders, float camSpeed);


	static float getMax_dl(float ownPos, float camPos, int[][] borders){
		return (ownPos - camPos) > 0 ? borders[0][1] : borders[0][0];
	}

	static int getSign(float a) {
		return (a > 0) ? 1 : -1;
	}
}
