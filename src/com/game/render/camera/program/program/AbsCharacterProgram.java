package com.game.render.camera.program.program;

import com.game.render.camera.program.CameraProgram;
import com.game.render.camera.program.CameraProgramOwner;

/**
 * @author Henry on 28/09/16.
 */
public abstract class AbsCharacterProgram implements CameraProgram<float[]> {

	protected final int SCR_W, SCR_H;

	protected float cameraSpeed = 10;

	protected CameraProgramOwner owner;

	protected int[][] borderIntervalsOX, borderIntervalsOY;

	protected boolean translateOX, translateOY;


	public AbsCharacterProgram(CameraProgramOwner owner, float scrW, float scrH) {
		this(scrW, scrH);
		setOwner(owner);
	}
	public AbsCharacterProgram(Float scrW, Float scrH) {
		this.SCR_W = (int) scrW.floatValue();
		this.SCR_H = (int) scrH.floatValue();
		this.borderIntervalsOX = new int[0][2];
		this.borderIntervalsOY = new int[0][2];
	}

	@Override
	public CameraProgram setCameraSpeed(float cameraSpeed) {
		this.cameraSpeed = cameraSpeed;
		return this;
	}

	public CameraProgram translateOX(boolean translateOX) {
		this.translateOX = translateOX;
		return this;
	}

	public CameraProgram translateOY(boolean translateOY) {
		this.translateOY = translateOY;
		return this;
	}

	public AbsCharacterProgram addBorderIntervalsOX(float leftInterval, float rightInterval) {
		this.borderIntervalsOX = copyBorderArrays(borderIntervalsOX, interval(leftInterval, SCR_W), interval(rightInterval, SCR_W));

		return this;
	}
	public AbsCharacterProgram addBorderIntervalsOY(float leftInterval, float rightInterval) {
		this.borderIntervalsOY = copyBorderArrays(borderIntervalsOY, interval(leftInterval, SCR_H), interval(rightInterval, SCR_H));
		return this;
	}

	private static int[][] copyBorderArrays(int[][] source, int left, int right) {
		int[][] tmp = new int[source.length + 1][2];
		for (int i = 0; i < source.length; i++) {
			tmp[i][0] = source[i][0];
			tmp[i][1] = source[i][1];
		}
		tmp[tmp.length - 1][0] = left;
		tmp[tmp.length - 1][1] = right;
		return tmp;
	}

	private static int interval(float interval, float relative) {
		return (int) ((relative / 2) * interval);
	}

	@Override
	public int getID(){
		return this.hashCode();
	}

	public CameraProgramOwner getOwner() {
		return owner;
	}
	public void setOwner(CameraProgramOwner owner) {
		this.owner = owner;
	}
}
