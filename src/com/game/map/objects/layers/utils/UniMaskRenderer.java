package com.game.map.objects.layers.utils;

import com.game.render.fbo.EscapyFBO;
import com.game.render.mask.LightMask;

/**
 * @author Henry on 05/10/16.
 */
@FunctionalInterface
public interface UniMaskRenderer {

	void renderUniMask(EscapyFBO fbo, LightMask mask);
}
