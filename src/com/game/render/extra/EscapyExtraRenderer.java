package com.game.render.extra;

import com.game.render.EscapyGdxCamera;
import com.game.render.EscapyRenderable;
import com.game.utils.absContainer.EscapyContainerable;

public interface EscapyExtraRenderer <T extends EscapyRenderable> 
	extends EscapyContainerable {

	public void extraRender(float[] translationVec, EscapyGdxCamera escapyCamera);
	public T getRenderTarget();
	public EscapyExtraRenderer<T> setRenderTarget(T renderTarget);
	public float[] getTranslationVec();
	public EscapyExtraRenderer<T> setTranslationVec(float[] translationVec);
	
}
