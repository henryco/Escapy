package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

public class EscapyStdSoftDodgeRenderer extends EscapyBlendRenderer{

	public static final String VERTEX = "shaders\\blend\\softDodge\\softDodge.vert";
	public static final String FRAGMENT = "shaders\\blend\\softDodge\\softDodge.frag";
	public static final String TARGETMAP = "targetMap";
	public static final String BLENDMAP = "blendMap";
	
	public EscapyStdSoftDodgeRenderer() {
		super(VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}
	
	public EscapyStdSoftDodgeRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}
	
	@Override
	public String toString() {
		return "EscapyStdSoftDodgeProgram_"+super.id;
	}
}
