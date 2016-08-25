package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyStdMultiplyRenderer.
 */
public class EscapyStdMultiplyRenderer extends EscapyBlendRenderer {

	/** The Constant VERTEX. */
	public static final String VERTEX = "data/shaders/blend/multiply/multiply.vert";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "data/shaders/blend/multiply/multiply.frag";
	
	/** The Constant TARGETMAP. */
	public static final String TARGETMAP = "targetMap";
	
	/** The Constant BLENDMAP. */
	public static final String BLENDMAP = "blendMap";
	
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
	
	/* (non-Javadoc)
	 * @see com.game.render.shader.blend.EscapyBlendRenderer#toString()
	 */
	@Override
	public String toString() {
		return "EscapyStdMultiplyShader_"+super.id;
	}

}
