package com.game.render.fbo.excp;

public class EscapyFBOtypeException extends EscapyFBOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3668559053505643859L;

	protected static final String DEF_EXCP = "Wrong FBO type";
	
	public EscapyFBOtypeException() {
		super(DEF_EXCP);
	}
	protected EscapyFBOtypeException(String excp) {
		super(excp);
	}
	
}
