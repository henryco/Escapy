package com.game.render.fbo.psProcess.program;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;

// TODO: Auto-generated Javadoc
/**
 * The Class FBORenderProgram.
 *
 * @param <T>
 *            the generic type
 */
public abstract class FBORenderProgram<T extends EscapyFBO> {

	/** The fbo. */
	protected T fbo;
	
	/**
	 * Instantiates a new FBO render program.
	 *
	 * @param fboProgramTarget
	 *            the fbo program target
	 */
	public FBORenderProgram(T fboProgramTarget) {
		
		this.fbo = fboProgramTarget;
	}
	public FBORenderProgram() {	
		this.fbo = null;
	}
	
	/**
	 * Render program.
	 *
	 * @param camera
	 *            the camera
	 * @param ePP
	 *            the e PP
	 */
	public abstract void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP);
	
	/**
	 * Sets the fbo target.
	 *
	 * @param fboProgramTarget
	 *            the fbo program target
	 * @return the FBO render program
	 */
	public FBORenderProgram<T> setFBOTarget(T fboProgramTarget)
	{
		this.fbo = fboProgramTarget;
		return this;
	}

	/**
	 * Gets the fbo.
	 *
	 * @return the fbo
	 */
	public T getFBOTarget() {
		return fbo;
	}
	
}
