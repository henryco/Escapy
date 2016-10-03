package com.game.map.objectsAlt.objects.utils;

/**
 * @author Henry on 03/10/16.
 */
@FunctionalInterface
public interface PositionCorrector {

	float[] calculateCorrection(int frameW, int frameH, float w, float h);
}
