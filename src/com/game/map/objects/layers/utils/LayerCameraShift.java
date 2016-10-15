package com.game.map.objects.layers.utils;

import com.badlogic.gdx.math.Vector3;
import com.game.render.camera.EscapyGdxCamera;

/**
 * @author Henry on 05/10/16.
 */
@FunctionalInterface
public interface LayerCameraShift {

	EscapyGdxCamera shiftCamera(EscapyGdxCamera camera, final float ... pos);

	static float[] getCamPos(EscapyGdxCamera camera){
		Vector3 pos = camera.getCamera().position;
		return new float[]{pos.x, pos.y};
	}
}
