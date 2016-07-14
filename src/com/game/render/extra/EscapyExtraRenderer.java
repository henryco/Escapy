package com.game.render.extra;

import com.game.render.EscapyGdxCamera;
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
	public void extraRender(float[] translationVec, EscapyGdxCamera escapyCamera);
	
	/**
	 * Gets the render target.
	 *
	 * @return the render target
	 */
	public T getRenderTarget();
	
	/**
	 * Sets the render target.
	 *
	 * @param renderTarget
	 *            the render target
	 * @return the escapy extra renderer
	 */
	public EscapyExtraRenderer<T> setRenderTarget(T renderTarget);
	
	/**
	 * Gets the translation vec.
	 *
	 * @return the translation vec
	 */
	public float[] getTranslationVec();
	
	/**
	 * Sets the translation vec.
	 *
	 * @param translationVec
	 *            the translation vec
	 * @return the escapy extra renderer
	 */
	public EscapyExtraRenderer<T> setTranslationVec(float[] translationVec);
	
}
