package com.game.render.fbo.psProcess.cont;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;

public interface EscapyFBOContainer {
	
	public EscapyFBO mergeContainedFBO();
	public EscapyFBO mergeContainedFBO(EscapyGdxCamera camera);
}
