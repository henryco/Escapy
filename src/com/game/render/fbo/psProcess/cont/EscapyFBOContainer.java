package com.game.render.fbo.psProcess.cont;

import com.game.render.EscapyGdxCamera;

public interface EscapyFBOContainer {
	
	public EscapyFBOContainer mergeContainedFBO();
	public EscapyFBOContainer mergeContainedFBO(EscapyGdxCamera camera);
}
