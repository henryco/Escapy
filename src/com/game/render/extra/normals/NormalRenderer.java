package com.game.render.extra.normals;

import com.game.render.EscapyGdxCamera;
import com.game.render.extra.ExtraRendererSuper;

// TODO: Auto-generated Javadoc
/**
 * The Class NormalRenderer.
 */
public class NormalRenderer extends ExtraRendererSuper<EscapyNormalRender> {

	
	/**
	 * Instantiates a new normal renderer.
	 *
	 * @param target
	 *            the target
	 */
	public NormalRenderer(EscapyNormalRender target) {
		super(target);
	}

	/* (non-Javadoc)
	 * @see com.game.render.extra.EscapyExtraRenderer#extraRender(float[], com.game.render.EscapyGdxCamera)
	 */
	@Override
	public void extraRender(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
	
		super.renderTarget.renderNormals(translationMatrix, escapyCamera);
	}

	

}
