package com.game.render.program.gl.separate;

import com.badlogic.gdx.graphics.GL30;
import com.game.render.program.RenderProgram;

/**
 * @author Henry on 23/09/16.
 */
public final class GLBlendProgram implements RenderProgram {

	public static final int[] ADD_RGB = new int[]{GL30.GL_SRC_ALPHA, GL30.GL_ONE, GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_COLOR};
	public static final int[] ADD_RGBA = new int[]{GL30.GL_SRC_ALPHA, GL30.GL_ONE, GL30.GL_ONE, GL30.GL_ONE_MINUS_SRC_COLOR};
}
