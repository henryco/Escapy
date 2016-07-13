package com.game.render.extra.normals;

import com.game.render.EscapyGdxCamera;
import com.game.render.EscapyRenderable;

public interface EscapyNormalRender extends EscapyRenderable {

	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera);
	
}
