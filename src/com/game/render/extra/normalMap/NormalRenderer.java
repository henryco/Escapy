package com.game.render.extra.normalMap;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.extra.ExtraRendererSuper;

// TODO: Auto-generated Javadoc
/**
 * The Class NormalRenderer.
 */
public class NormalRenderer extends ExtraRendererSuper<EscapyNormalMapRender> {

	
	/**
	 * Instantiates a new normal renderer.
	 *
	 * @param target
	 *            the target
	 */
	public NormalRenderer(EscapyNormalMapRender target) {
		super(target);
	}

	/* (non-Javadoc)
	 * @see com.game.render.extra.EscapyExtraRenderer#extraRender(float[], com.game.render.camera.EscapyGdxCamera)
	 */
	@Override
	public void extraRender(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
	
		super.renderTarget.renderNormals(translationMatrix, escapyCamera);
	}

	

}
