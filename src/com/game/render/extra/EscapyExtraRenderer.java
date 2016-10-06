package com.game.render.extra;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.EscapyRenderable;
import com.game.utils.absContainer.EscapyContainerable;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyExtraRenderer.
 *
 * @param <T>
 *            the generic type
 */
public interface EscapyExtraRenderer <T extends EscapyRenderable> 
	extends EscapyContainerable {

	/**
	 * Extra render.
	 *
	 * @param translationVec
	 *            the translation vec
	 * @param escapyCamera
	 *            the escapy camera
	 */
	void extraRender(float[] translationVec, EscapyGdxCamera escapyCamera);
	
	/**
	 * Gets the render target.
	 *
	 * @return the render target
	 */
	T getRenderTarget();
	
	/**
	 * Sets the render target.
	 *
	 * @param renderTarget
	 *            the render target
	 * @return the escapy extra renderer
	 */
	EscapyExtraRenderer<T> setRenderTarget(T renderTarget);
	
	/**
	 * Gets the translation vec.
	 *
	 * @return the translation vec
	 */
	float[] getTranslationVec();
	
	/**
	 * Sets the translation vec.
	 *
	 * @param translationVec
	 *            the translation vec
	 * @return the escapy extra renderer
	 */
	EscapyExtraRenderer<T> setTranslationVec(float[] translationVec);
	
}
