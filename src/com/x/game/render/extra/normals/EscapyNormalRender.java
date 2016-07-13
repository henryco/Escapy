package com.x.game.render.extra.normals;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.EscapyRenderable;

public interface EscapyNormalRender extends EscapyRenderable {

	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera);
	
}
