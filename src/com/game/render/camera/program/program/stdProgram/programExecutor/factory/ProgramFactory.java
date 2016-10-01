package com.game.render.camera.program.program.stdProgram.programExecutor.factory;

import com.game.render.camera.program.program.stdProgram.programExecutor.executors.FastVectorCam;
import com.game.render.camera.program.program.stdProgram.programExecutor.executors.FollowCam;
import com.game.render.camera.program.program.stdProgram.programExecutor.ProgramExecutor;

/**
 * @author Henry on 01/10/16.
 */
public final class ProgramFactory {

	public static ProgramExecutor fastVectorCam(){
		return new FastVectorCam();
	}
	public static ProgramExecutor followCam(){
		return new FollowCam();
	}
}
