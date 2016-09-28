package com.game.render.extra.std;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.EscapyRenderable;
import com.game.render.extra.ExtraRendererSuper;

// TODO: Auto-generated Javadoc
/**
 * The Class StdRenderer.
 */
public class StdRenderer extends ExtraRendererSuper <EscapyRenderable>{

	
	/**
	 * Instantiates a new std renderer.
	 *
	 * @param target
	 *            the target
	 */
	public StdRenderer(EscapyRenderable target) {
		super(target);
	}

	/* (non-Javadoc)
	 * @see com.game.render.extra.EscapyExtraRenderer#extraRender(float[], com.game.render.camera.EscapyGdxCamera)
	 */
	@Override
	public void extraRender(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		
		super.renderTarget.renderGraphic(translationMatrix, escapyCamera);
	}

}
