package com.game.render.fbo.psRender;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.utils.translationVec.TransVec;

public interface EscapyPostRenderable {

	public abstract void postRender(EscapyFBO fbo, TransVec translationVec);
	public abstract EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera);
}
