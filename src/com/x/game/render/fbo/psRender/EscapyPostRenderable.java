package com.x.game.render.fbo.psRender;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.fbo.EscapyFBO;
import com.x.game.utils.translationVec.TransVec;

public interface EscapyPostRenderable {

	public abstract void postRender(EscapyFBO fbo, TransVec translationVec);
	public abstract EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera);
}
