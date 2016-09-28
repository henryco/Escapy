package com.game.render.fbo.psProcess.cont;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;

public interface EscapyFBOContainer {

    EscapyFBO mergeContainedFBO();
    EscapyFBO mergeContainedFBO(EscapyGdxCamera camera);
}
