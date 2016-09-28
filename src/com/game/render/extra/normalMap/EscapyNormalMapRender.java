package com.game.render.extra.normalMap;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.EscapyRenderable;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyNormalMapRender.
 */
public interface EscapyNormalMapRender extends EscapyRenderable {

	/**
	 * Render normals.
	 *
	 * @param translationMatrix
	 *            the translation matrix
	 * @param escapyCamera
	 *            the escapy camera
	 */
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera);
	
}
