package com.game.render.camera.program2_0;

/**
 * @author Henry on 28/09/16.
 */
public interface CameraProgramOwner {

	CameraProgramOwner setOwnerPosition(float ... xy);
	CameraProgramOwner setMoveNVector(int ... xy);
	int[] getMoveNVector();
	float[] getOwnerPosition();
}
