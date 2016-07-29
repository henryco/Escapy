package com.game.render.fbo.psProcess.program.userState;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.blend.EscapyBlendRenderer;
import com.game.render.shader.blend.userState.EscapyStdMultiplyRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class FBOMultiplyProgram, that contains target FBO with PROTECTED modifier, so u
 * can easely get access to it by <b> super.fbo.??? </b>
 */
public class FBOMultiplyProgram extends FBORenderProgram<EscapyMultiFBO>{

	private EscapyBlendRenderer blednRenderer;
	
	/**
	 * Instantiates a new FBO multiply mask program.
	 *
	 * @param fboProgramTarget
	 *            the fbo program target
	 */
	public FBOMultiplyProgram(EscapyMultiFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.blednRenderer = new EscapyStdMultiplyRenderer(super.fbo.getId());
	}

	public FBOMultiplyProgram() {
		super();
		this.blednRenderer = new EscapyStdMultiplyRenderer();
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psProcess.program.FBORenderProgram#renderProgram(com.game.render.EscapyGdxCamera, com.game.render.fbo.psProcess.EscapyPostProcessed)
	 */
	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) 
	{
		this.blednRenderer.renderBlended(super.fbo.getMultiTextureRegion(), super.fbo.getTextureRegion(),
				0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera.getCamera());
	}

	

}
