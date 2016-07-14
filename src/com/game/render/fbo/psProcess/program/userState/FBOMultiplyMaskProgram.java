package com.game.render.fbo.psProcess.program.userState;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.shader.blend.EscapyBlendRenderer;
import com.game.render.shader.blend.userState.EscapyStdMultiplyRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class FBOMultiplyMaskProgram.
 */
public class FBOMultiplyMaskProgram extends FBORenderProgram<EscapyFBO>{

	private EscapyBlendRenderer blednRenderer;
	
	/**
	 * Instantiates a new FBO multiply mask program.
	 *
	 * @param fboProgramTarget
	 *            the fbo program target
	 */
	public FBOMultiplyMaskProgram(EscapyFBO fboProgramTarget) {
		super(fboProgramTarget);
		this.blednRenderer = new EscapyStdMultiplyRenderer();
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psProcess.program.FBORenderProgram#renderProgram(com.game.render.EscapyGdxCamera, com.game.render.fbo.psProcess.EscapyPostProcessed)
	 */
	@Override
	public void renderProgram(EscapyGdxCamera camera, EscapyPostProcessed ePP) {
		// TODO Auto-generated method stub
		
	}

	

}
