package com.game.map.objects.objects.utils;

/**
 * @author Henry on 03/10/16.
 */
@FunctionalInterface
public interface ZoomCalculator {

	float calculateZoom(int frameW, int frameH, float w, float h, float zoom);
}
