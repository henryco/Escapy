package com.game.render.camera.program;

/**
 * @author Henry on 28/09/16.
 */
public interface CameraProgramOwner {

	CameraProgramOwner setOwnerPosition(float ... xy);
	CameraProgramOwner setMoveNVector(int ... xy);
	int[] getMoveNVector();
	float[] getOwnerPosition();
}
