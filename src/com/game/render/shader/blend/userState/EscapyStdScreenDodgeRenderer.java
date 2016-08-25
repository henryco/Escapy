package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

public class EscapyStdScreenDodgeRenderer extends EscapyBlendRenderer{

	/** The Constant VERTEX. */
	public static final String VERTEX = "data/shaders/blend/scrClrDdg/scrClrDdg.vert";
	
	/** The Constant FRAGMENT. */
	public static final String FRAGMENT = "data/shaders/blend/scrClrDdg/scrClrDdg.frag";
	
	/** The Constant TARGETMAP. */
	public static final String TARGETMAP = "targetMap";
	
	/** The Constant BLENDMAP. */
	public static final String BLENDMAP = "blendMap";
	
	
	
	public EscapyStdScreenDodgeRenderer() {
		super(VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);	
	}
	
	public EscapyStdScreenDodgeRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);	
	}
	
	@Override
	public String toString() {
		return "EscapyStdScreenDodgeRenderer_"+super.id;
	}
}
