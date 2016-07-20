package com.game.render.shader.blend.userState;

import com.game.render.shader.blend.EscapyBlendRenderer;

public class EscapyStdSoftLightRenderer extends EscapyBlendRenderer {

	public static final String VERTEX = "shaders\\blend\\softLight\\softLight.vert";
	public static final String FRAGMENT = "shaders\\blend\\softLight\\softLight.frag";
	public static final String TARGETMAP = "targetMap";
	public static final String BLENDMAP = "blendMap";
	
	public EscapyStdSoftLightRenderer() {
		super(VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}
	
	public EscapyStdSoftLightRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT, TARGETMAP, BLENDMAP);
	}

	@Override
	public String toString() {
		return "EscapyStdSoftLightRenderer_"+super.id;
	}
}
