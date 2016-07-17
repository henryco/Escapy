package com.game.render.fbo.excp;

public class EscapyFBOTypeException extends EscapyFBOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3668559053505643859L;

	protected static final String DEF_EXCP = "Wrong FBO type";
	
	public EscapyFBOTypeException() {
		super(DEF_EXCP);
	}
	public EscapyFBOTypeException(String excp) {
		super(excp);
	}
	
}
