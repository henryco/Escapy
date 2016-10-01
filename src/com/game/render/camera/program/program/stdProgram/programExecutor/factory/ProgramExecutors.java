package com.game.render.camera.program.program.stdProgram.programExecutor.factory;

import com.game.render.camera.program.program.stdProgram.programExecutor.executors.FollowCam;
import com.game.render.camera.program.program.stdProgram.programExecutor.ProgramExecutor;
import com.game.render.camera.program.program.stdProgram.programExecutor.executors.FastVectorCam;

/**
 * @author Henry on 01/10/16.
 */
public final class ProgramExecutors {

	public static final ProgramExecutor fastVectorCam = new FastVectorCam();
	public static final ProgramExecutor followCam = new FollowCam();
}
