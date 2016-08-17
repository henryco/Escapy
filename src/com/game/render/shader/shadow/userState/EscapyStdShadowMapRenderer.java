package com.game.render.shader.shadow.userState;

import com.game.render.shader.shadow.EscapyShadowMapRenderer;

public class EscapyStdShadowMapRenderer extends EscapyShadowMapRenderer {

	public static final String VERTEX = "shaders\\shadows\\creator\\shadows.vert";
	public static final String FRAGMENT = "shaders\\shadows\\creator\\shadows.frag";
	public static final String SOURCEMAP = "u_texture";
	
	public EscapyStdShadowMapRenderer() {
		super(SOURCEMAP, VERTEX, FRAGMENT);
	}
	public EscapyStdShadowMapRenderer(int id) {
		super(id, SOURCEMAP, VERTEX, FRAGMENT);
	}
	
	@Override
	public String toString() {
		return "EscapyStdShadowMapRenderer_"+super.id;
	}
}