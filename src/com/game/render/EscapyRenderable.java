package com.game.render;

// TODO: Auto-generated Javadoc

import com.game.render.camera.EscapyGdxCamera;

/**
 * The Interface EscapyRenderable.*/
public interface EscapyRenderable {
	
	/**
	 * Render graphic.
	 *
	 * @param translationVec
	 *            the translation vec
	 * @param escapyCamera
	 *            the escapy camera
	 */

	public void renderGraphic(float[] translationVec, EscapyGdxCamera escapyCamera);
	
}
