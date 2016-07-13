package com.x.game.render.extra;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.EscapyRenderable;
import com.x.game.utils.absContainer.EscapyContainerable;

public interface EscapyExtraRenderer <T extends EscapyRenderable> 
	extends EscapyContainerable {

	public void extraRender(float[] translationVec, EscapyGdxCamera escapyCamera);
	public T getRenderTarget();
	public EscapyExtraRenderer<T> setRenderTarget(T renderTarget);
	public float[] getTranslationVec();
	public EscapyExtraRenderer<T> setTranslationVec(float[] translationVec);
	
}
