package com.game.render.fbo.psProcess.program.stdShadow;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;

public class FBOStdShadowsProgram extends FBORenderProgram<EscapyFBO>{

	public FBOStdShadowsProgram() {
		super();
	}
	
	public FBOStdShadowsProgram(EscapyFBO fboProgramTarget) {
		super(fboProgramTarget);
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		
	}

}
