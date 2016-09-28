package com.game.render.extra.lightMap;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.EscapyRenderable;

public interface EscapyLightMapRenderer extends EscapyRenderable {


	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera);
}
