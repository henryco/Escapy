package com.game.render;

import com.game.render.camera.EscapyGdxCamera;

/**
 * @author Henry on 04/10/16.
 */
@FunctionalInterface
public interface EscapyMapRenderer {

	void renderTextureMap(EscapyGdxCamera escapyCamera);
}
