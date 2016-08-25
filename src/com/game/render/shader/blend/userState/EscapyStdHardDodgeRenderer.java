package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

public class EscapyStdHardDodgeRenderer extends EscapyBlendRenderer {

	/** The Constant VERTEX. */
	public static final String VERTEX = "data/shaders/blend/hardDodge/hardDodge.vert";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "data/shaders/blend/hardDodge/hardDodge.frag";
	
	/** The Constant TARGETMAP. */
	public static final String TARGETMAP = "targetMap";
	
	/** The Constant BLENDMAP. */
	public static final String BLENDMAP = "blendMap";
	
	/**
	 * Instantiates a new escapy std multiply renderer.
	 */
	public EscapyStdHardDodgeRenderer() {
		super(VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);	
	}
	
	/**
	 * Instantiates a new escapy std multiply renderer.
	 *
	 * @param ID
	 *            the id
	 */
	public EscapyStdHardDodgeRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);	
	}
	

	@Override
	public String toString() {
		return "EscapyStdHardDodgeRenderer_"+super.id;
	}

}
