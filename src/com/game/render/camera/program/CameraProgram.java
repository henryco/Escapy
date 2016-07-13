package com.game.render.camera.program;

import com.game.render.EscapyGdxCamera;

public abstract class CameraProgram<T> {

	protected T programTarget;

	public CameraProgram() {
		this.programTarget = null;
	}

	public CameraProgram(T programTarget) {
		this.programTarget = programTarget;
	}

	public CameraProgram<T> setProgramTarget(T programTarget) {
		this.programTarget = programTarget;
		return this;
	}

	protected T getTarget() {
		return this.programTarget;
	}

	public abstract float[] programTranslate(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera);

}
