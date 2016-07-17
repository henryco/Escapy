package com.game.render.fbo.excp;

public class EscapyFBOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4724078969498492051L;

	protected static final String DEF_EXCP ="ERROR: EscapyFBOException";
	
	public EscapyFBOException() {
		super(DEF_EXCP);
	}
	
	public EscapyFBOException(String excp) {
		super((DEF_EXCP+" "+excp));
	}
}
