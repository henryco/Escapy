package com.game.render.extra.container;

import com.game.render.EscapyGdxCamera;
import com.game.render.EscapyRenderable;
import com.game.render.extra.EscapyExtraRenderer;
import com.game.utils.absContainer.EscapyAbsContainer;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtraRenderContainer.
 */
public class ExtraRenderContainer extends EscapyAbsContainer<EscapyExtraRenderer<?>> 
	implements EscapyRenderable {

	/**
	 * Instantiates a new extra render container.
	 */
	public ExtraRenderContainer() {
		super();
	}

	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		super.targetsList.forEach(
				EER -> EER.extraRender(translationMatrix, escapyCamera));
	}
	
	/**
	 * Render graphic.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 */
	public void renderGraphic(EscapyGdxCamera escapyCamera) {
		super.targetsList.forEach(
				EER -> EER.extraRender(EER.getTranslationVec(), escapyCamera));
	}

	
}
