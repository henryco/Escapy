package com.game.render.shader.colorize.userState;

import com.game.render.shader.colorize.EscapyLghtSrcRenderer;

public class EscapyStdLghtSrcRenderer extends EscapyLghtSrcRenderer {

	/** The Constant VERTEX. */
	public static final String VERTEX = "shaders\\lightSrc\\lightSrc.vert";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "shaders\\lightSrc\\lightSrc.frag";
	
	/** The Constant TARGETMAP. */
	public static final String TARGETMAP = "targetMap";
	public static final String LIGHTMAP = "u_lightMap";
	
	public EscapyStdLghtSrcRenderer() {
		super(TARGETMAP, LIGHTMAP, VERTEX, FRAGMENT);	
	}
	public EscapyStdLghtSrcRenderer(int ID) {
		super(ID, TARGETMAP, LIGHTMAP, VERTEX, FRAGMENT);	
	}
	
	@Override
	public String toString() {
		return "EscapyStdColorizeRenderer_"+super.id;
	}
	
	
}
