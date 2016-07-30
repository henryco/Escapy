package com.game.render.fbo.psProcess.program.stdVoLights;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.volumeLight.EscapyVolumeRenderer;

public class FBOStdVoLightProgram extends FBORenderProgram<EscapyFBO>{

	private EscapyVolumeRenderer volumeRenderer;

	public FBOStdVoLightProgram() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FBOStdVoLightProgram(EscapyFBO fboProgramTarget) {
		super(fboProgramTarget);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		// TODO Auto-generated method stub
		
	}

	public EscapyVolumeRenderer getVolumeRenderer() {
		return volumeRenderer;
	}

	public FBOStdVoLightProgram setVolumeRenderer(EscapyVolumeRenderer volumeRenderer) {
		this.volumeRenderer = volumeRenderer;
		return this;
	}

}
