package com.game.render.fbo.psRender;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyPostRenderable.
 */
public interface EscapyPostRenderable extends EscapyPostRenderer {

	/**
	 * Sets the post render FBO.
	 *
	 * @param <T>
	 *            the generic type
	 * @param postRednerFBO
	 *            the post redner FBO
	 * @return the escapy post renderable
	 */
	public abstract <T extends EscapyFBO> EscapyPostRenderable setPostRenderFBO(T postRednerFBO) throws EscapyFBOtypeException;
	
	/**
	 * Gets the post render FBO.
	 *
	 * @return the post render FBO
	 */
	public abstract EscapyFBO getPostRenderFBO();
	
	/**
	 * Sets the post render camera.
	 *
	 * @param camera
	 *            the camera
	 * @return the escapy post renderable
	 */
	public abstract EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera);
	
}
