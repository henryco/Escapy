package com.game.render.shader.lightSrc.userState;

import com.game.render.shader.lightSrc.EscapyLightSrcRenderer;

public class EscapyStdShadedLightSrcRenderer extends EscapyLightSrcRenderer {

	/** The Constant VERTEX. */
	public static final String VERTEX = "data/shaders/lightSrc/lightSrc.vert";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "data/shaders/lightSrc/lightSrc_S.frag";
	
	public static final String LIGHTMAP = "u_lightMap";
	
	public EscapyStdShadedLightSrcRenderer() {
		super(LIGHTMAP, VERTEX, FRAGMENT);	
	}
	public EscapyStdShadedLightSrcRenderer(int ID) {
		super(ID, LIGHTMAP, VERTEX, FRAGMENT);	
	}
	
	@Override
	public String toString() {
		return "EscapyStdShadedSrcRenderer_"+super.id;
	}
	
	
}
