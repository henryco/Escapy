package com.game.render.extra.container;

import com.game.render.EscapyGdxCamera;
import com.game.render.EscapyRenderable;
import com.game.render.extra.EscapyExtraRenderer;
import com.game.utils.absContainer.EscapyAbsContainer;

public class ExtraRenderContainer extends EscapyAbsContainer<EscapyExtraRenderer<?>> 
	implements EscapyRenderable {

	public ExtraRenderContainer() {
		super();
	}

	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		for (EscapyExtraRenderer<?> EER : super.targetsList) 
			EER.extraRender(translationMatrix, escapyCamera);	
	}
	
	public void renderGraphic(EscapyGdxCamera escapyCamera) {
		for (EscapyExtraRenderer<?> EER : super.targetsList) 
			EER.extraRender(EER.getTranslationVec(), escapyCamera);	
	}
	
}
