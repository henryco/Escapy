package com.game.render.fbo.psRender;

import com.game.render.fbo.EscapyFBO;
import com.game.utils.translationVec.TransVec;

public interface EscapyPostIterative extends EscapyPostRenderer {
	
	/** 
	 * 
	 * @param fbo - frameBufferObject, <b>see: {@link EscapyFBO} </b>
	 * @param translationVec - transtalion vector
	 * @param times - 3 is default for void frameBuffer
	 * @return fbo - same but filled fbo
	 */
	EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec, int times);
}
