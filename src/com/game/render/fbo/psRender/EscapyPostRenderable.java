package com.game.render.fbo.psRender;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyPostRenderable.
 */
public interface EscapyPostRenderable {

	/**
	 * Post render.
	 *
	 * @param fbo
	 *            the fbo
	 * @param translationVec
	 *            the translation vec
	 */
	public abstract void postRender(EscapyFBO fbo, TransVec translationVec);
	
	/**
	 * Sets the post render camera.
	 *
	 * @param camera
	 *            the camera
	 * @return the escapy post renderable
	 */
	public abstract EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera);
}
