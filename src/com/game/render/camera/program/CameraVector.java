package com.game.render.camera.program;

/**
 * @author Henry on 01/10/16.
 */
public class CameraVector {
	private float[] mvNVector = new float[2];

	public CameraVector setMoveNVector(float ... xy){
		mvNVector[0] = xy[0];
		mvNVector[1] = xy[1];
		return this;
	}
	public float[] getMoveNVector(){
		return mvNVector;
	}
}
