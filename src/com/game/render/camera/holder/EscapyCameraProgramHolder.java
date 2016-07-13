package com.game.render.camera.holder;

import com.game.render.EscapyGdxCamera;

public interface EscapyCameraProgramHolder {

	public abstract float[] executeCameraProgram(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera);

	public abstract int getID();
}
