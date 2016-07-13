package com.x.game.render.extra.container;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.EscapyRenderable;
import com.x.game.render.extra.EscapyExtraRenderer;
import com.x.game.utils.absContainer.EscapyAbsContainer;

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
