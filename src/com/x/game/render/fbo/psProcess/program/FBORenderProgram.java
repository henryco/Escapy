package com.x.game.render.fbo.psProcess.program;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.fbo.EscapyFBO;
import com.x.game.render.fbo.psProcess.EscapyPostProcessed;

public abstract class FBORenderProgram<T extends EscapyFBO> {

	protected T fbo;
	
	public FBORenderProgram(T fboProgramTarget) {
		
		this.fbo = fboProgramTarget;
	}
	
	public abstract void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP);
	
	public FBORenderProgram<T> setFboTarget(T fboProgramTarget)
	{
		this.fbo = fboProgramTarget;
		return this;
	}

	public T getFbo() {
		return fbo;
	}
	
}
