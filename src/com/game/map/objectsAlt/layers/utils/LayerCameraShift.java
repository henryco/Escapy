package com.game.map.objectsAlt.layers.utils;

import com.game.render.camera.EscapyGdxCamera;

/**
 * @author Henry on 05/10/16.
 */
@FunctionalInterface
public interface LayerCameraShift {

	EscapyGdxCamera shiftCamera(EscapyGdxCamera camera, final float ... pos);

}
