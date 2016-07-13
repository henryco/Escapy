package com.x.game.render.fbo.psProcess.program;

import com.badlogic.gdx.Gdx;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.fbo.NormalMapFBO;
import com.x.game.render.fbo.psProcess.EscapyPostProcessed;
import com.x.game.render.fbo.psProcess.lights.SimpleLight;
import com.x.game.render.shader.EscapyNrmlShader;

public class FBOVolumeLightProgram extends FBORenderProgram<NormalMapFBO> {

	private EscapyNrmlShader nrmlShader;
	
	public FBOVolumeLightProgram(NormalMapFBO fboProgramTarget) 
	{
		super(fboProgramTarget);
		this.nrmlShader = new EscapyNrmlShader();
	}
	

	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) 
	{
		this.nrmlShader.renderNrmLight(super.fbo.getTargetTextureRegion(), 
				super.fbo.getNormalMapTexureRegion(), 0, 0, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight(), camera.getCamera(), (SimpleLight) ePP);

	}

}
