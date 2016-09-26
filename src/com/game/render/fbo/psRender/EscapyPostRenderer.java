package com.game.render.fbo.psRender;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyPostRenderer.
 */
public interface EscapyPostRenderer {

	/**
	 * Post render.
	 *
	 * @param fbo
	 *            the fbo
	 * @param translationVec
	 *            the translation vec
	 * @return the escapy FBO
	 */
	EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec);
	
	/**
	 * Post render.
	 *
	 * @param translationVec
	 *            the translation vec
	 */
	void postRender(TransVec translationVec);
	
	EscapyPostRenderer setPostRenderCamera(EscapyGdxCamera camera);

}
