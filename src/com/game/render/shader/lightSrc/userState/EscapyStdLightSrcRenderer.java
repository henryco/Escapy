package com.game.render.shader.lightSrc.userState;

import com.game.render.shader.lightSrc.EscapyLightSrcRenderer;

public class EscapyStdLightSrcRenderer extends EscapyLightSrcRenderer {

	/** The Constant VERTEX. */
	public static final String VERTEX = "shaders\\lightSrc\\lightSrc.vert";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "shaders\\lightSrc\\lightSrc_N.frag";
	
	public static final String LIGHTMAP = "u_lightMap";
	
	public EscapyStdLightSrcRenderer() {
		super(LIGHTMAP, VERTEX, FRAGMENT);	
	}
	public EscapyStdLightSrcRenderer(int ID) {
		super(ID, LIGHTMAP, VERTEX, FRAGMENT);	
	}
	
	@Override
	public String toString() {
		return "EscapyStdLightSrcRenderer_"+super.id;
	}
	
	
}
