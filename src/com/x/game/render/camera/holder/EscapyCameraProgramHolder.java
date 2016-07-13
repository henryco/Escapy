package com.x.game.render.camera.holder;

import com.x.game.render.EscapyGdxCamera;

public interface EscapyCameraProgramHolder {

	public abstract float[] executeCameraProgram(int sWidth, int sHeight, float scale_optional,
			EscapyGdxCamera escapyCamera);

	public abstract int getID();
}
