package com.game.render.shader.volumeLight.userState;

import com.game.render.shader.volumeLight.EscapyVolumeRenderer;

public class EscapyStdVolumeRenderer extends EscapyVolumeRenderer{

	public static final String VERTEX = "shaders\\dynamicLights\\dynamicLights.vert";
	public static final String FRAGMENT = "shaders\\dynamicLights\\dynamicLights.frag";
	
	public EscapyStdVolumeRenderer(int ID) {
		super(ID, VERTEX, FRAGMENT);
	}

	public EscapyStdVolumeRenderer() {
		super(VERTEX, FRAGMENT);	
	}

	@Override
	public String toString() {
		return "EscapyStdVolumeRenderer_"+super.id;
	}
}
