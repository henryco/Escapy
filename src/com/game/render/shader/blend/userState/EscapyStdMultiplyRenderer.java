package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyStdMultiplyRenderer.
 */
public class EscapyStdMultiplyRenderer extends EscapyBlendRenderer {

	/** The Constant VERTEX. */
	public static final String VERTEX = "";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "";
	
	/** The Constant TARGETMAP. */
	public static final String TARGETMAP = "";
	
	/** The Constant BLENDMAP. */
	public static final String BLENDMAP = "";
	
	/**
	 * Instantiates a new escapy std multiply renderer.
	 */
	public EscapyStdMultiplyRenderer() {
		super(VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);	
	}
	
	/**
	 * Instantiates a new escapy std multiply renderer.
	 *
	 * @param ID
	 *            the id
	 */
	public EscapyStdMultiplyRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);	
	}
	
	

}
