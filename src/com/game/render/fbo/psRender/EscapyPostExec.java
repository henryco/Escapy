package com.game.render.fbo.psRender;

import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.program.FBORenderProgram;

/**
 * 
 * @author Henry
 *
 * @param <T> Contained FBO target
 */
public interface EscapyPostExec <T extends EscapyFBO> extends EscapyPostRenderable {

	/**
	 * 
	 * @param FBOProgram 
	 * @return
	 * @see FBORenderProgram
	 */
	public abstract EscapyPostRenderable setRenderProgram(FBORenderProgram<T> program);
}
