package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

public class EscapyStdClrDodgeRenderer extends EscapyBlendRenderer {

	
	public static final String VERTEX = "shaders\\blend\\colorDodge\\colorDodge.vert";
	public static final String FRAGMENT = "shaders\\blend\\colorDodge\\colorDodge.frag";
	public static final String TARGETMAP = "targetMap";
	public static final String BLENDMAP = "blendMap";
	
	public EscapyStdClrDodgeRenderer() {
		super(VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}
	
	public EscapyStdClrDodgeRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}
	
	@Override
	public String toString() {
		return "EscapyStdClrDodgeRenderer_"+super.id;
	}

}
