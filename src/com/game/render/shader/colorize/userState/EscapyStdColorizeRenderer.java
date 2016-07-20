package com.game.render.shader.colorize.userState;

import com.game.render.shader.colorize.EscapyColorizeRenderer;

public class EscapyStdColorizeRenderer extends EscapyColorizeRenderer {

	/** The Constant VERTEX. */
	public static final String VERTEX = "shaders\\blend\\colorize\\colorize.vert";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "shaders\\blend\\colorize\\colorize.frag";
	
	/** The Constant TARGETMAP. */
	public static final String TARGETMAP = "targetMap";
	
	public EscapyStdColorizeRenderer() {
		super(TARGETMAP, VERTEX, FRAGMENT);	
	}
	public EscapyStdColorizeRenderer(int ID) {
		super(ID, TARGETMAP, VERTEX, FRAGMENT);	
	}
	
	@Override
	public String toString() {
		return "EscapyStdColorizeRenderer_"+super.id;
	}
	
	
}
