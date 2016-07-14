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

	/* (non-Javadoc)
	 * @see com.game.render.EscapyRenderable#renderGraphic(float[], com.game.render.EscapyGdxCamera)
	 */
	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		for (EscapyExtraRenderer<?> EER : super.targetsList) 
			EER.extraRender(translationMatrix, escapyCamera);	
	}
	
	/**
	 * Render graphic.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 */
	public void renderGraphic(EscapyGdxCamera escapyCamera) {
		for (EscapyExtraRenderer<?> EER : super.targetsList) 
			EER.extraRender(EER.getTranslationVec(), escapyCamera);	
	}
	
}
