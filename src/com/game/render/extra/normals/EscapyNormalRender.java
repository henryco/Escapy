package com.game.render.extra.normals;

import com.game.render.EscapyGdxCamera;
import com.game.render.EscapyRenderable;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyNormalRender.
 */
public interface EscapyNormalRender extends EscapyRenderable {

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
