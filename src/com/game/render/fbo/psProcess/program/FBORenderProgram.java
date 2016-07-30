package com.game.render.fbo.psProcess.program;

import java.lang.reflect.Array;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;
import com.game.render.fbo.psProcess.EscapyPostProcessed;

// TODO: Auto-generated Javadoc
/**
 * The Class FBORenderProgram.
 *
 * @param <T>
 *            the generic type
 */
public abstract class FBORenderProgram<T extends EscapyFBO> {

	public class arrayFBO <U extends EscapyFBO>{
		
		private U[] FBOARRAY;
		private arrayFBO(U[] fbo) {
			this.FBOARRAY = fbo;
		}
		public int lenght() {
			return this.FBOARRAY.length;
		}
		public U get(int index) {
			return this.FBOARRAY[index];
		}
	}
	
	private T fbo;
	private T[] fboArray;
	
	/**
	 * Instantiates a new FBO render program.
	 *
	 * @param fboProgramTarget
	 *            the fbo program target
	 */
	public FBORenderProgram(T fboProgramTarget) {

		setFBOTarget(fboProgramTarget);
	}
	@SuppressWarnings("unchecked")
	public FBORenderProgram() {	
		
		fbo = (T) new StandartFBO();
		final T[] tempFBO = (T[]) Array.newInstance(fbo.getClass(), 0);
		this.fboArray = tempFBO;
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
		@SuppressWarnings("unchecked")
		final T[] tempFBO = (T[]) Array.newInstance(fboProgramTarget.getClass(), 0);
		this.fboArray = tempFBO;
		this.addFBOTarget(fboProgramTarget);
		return this;
	}
	
	
	public FBORenderProgram<T> addFBOTarget(T fboProgramTarget) throws EscapyFBOtypeException
	{
		T inst = fboProgramTarget;
		@SuppressWarnings("unchecked")
		final T[] tempFBO = (T[]) Array.newInstance(inst.getClass(),fboArray.length + 1);
		int i = 0;
		for (T fboArr : fboArray) {
			tempFBO[i] = fboArr; 
			i++;
		}	tempFBO[i] = fboProgramTarget;
		this.fboArray = tempFBO;
		this.fbo = fboArray[0];
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
	
	public T getFBOTarget(int index) {
		return fboArray[index];
	}
	
	public arrayFBO<T> getFBOArray() {
		return new arrayFBO<T>(this.fboArray);
	}
	
	
}
