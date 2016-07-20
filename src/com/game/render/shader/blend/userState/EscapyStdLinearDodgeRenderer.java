package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

public class EscapyStdLinearDodgeRenderer extends EscapyBlendRenderer{

	public static final String VERTEX = "shaders\\blend\\linearDodge\\linearDodge.vert";
	public static final String FRAGMENT = "shaders\\blend\\linearDodge\\linearDodge.frag";
	public static final String TARGETMAP = "targetMap";
	public static final String BLENDMAP = "blendMap";
	
	public EscapyStdLinearDodgeRenderer() {
		super(VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}
	
	public EscapyStdLinearDodgeRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}
	
	@Override
	public String toString() {
		return "EscapyStdLinearDodgeProgram_"+super.id;
	}
}
