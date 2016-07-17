package com.game.render.fbo.psProcess.mask;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.excp.EscapyFBOTypeException;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * Simply lightmask extends {@link EscapyMask}.
 *
 * @author Henry
 */
public class StandartMask extends EscapyMask {

	private EscapyMultiFBO maskFBO;
	
	/**
	 * Instantiates a new standart mask.
	 */
	public StandartMask() {
		super();
	}
	
	/**
	 * Instantiates a new standart mask.
	 *
	 * @param postRenderCamera
	 *            the post render camera
	 */
	public StandartMask(EscapyGdxCamera postRenderCamera) {
		super(postRenderCamera);
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psProcess.mask.EscapyMask#initMask()
	 */
	@Override
	protected EscapyMultiFBO initMaskFBO() {
		
		this.maskFBO = new StandartMultiFBO();
		//this.maskFBO.setRenderProgram(new FBOMultiplyMaskProgram(maskFBO)); //TODO
		return maskFBO;
	}
	


	/* (non-Javadoc)
	 * @see com.game.render.fbo.psProcess.mask.EscapyMask#addMaskTarget(com.badlogic.gdx.graphics.glutils.FrameBuffer)
	 */
	@Override
	public EscapyMask addMaskTarget(FrameBuffer targetBuffer) {
		this.maskFBO.addMultiFrameBuffer(targetBuffer);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderer#postRender(com.game.render.fbo.EscapyFBO, com.game.utils.translationVec.TransVec)
	 */
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec) {
		
		super.applyColor(this.maskFBO);
		this.maskFBO.mergeBuffer();
		fbo.begin();
			this.maskFBO.renderTargetMultiFBO();
			this.maskFBO.renderFBO();
		fbo.end();
		return fbo;
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderer#postRender(com.game.utils.translationVec.TransVec)
	 */
	@Override
	public void postRender(TransVec translationVec) {
		
		super.applyColor(this.maskFBO);
		this.maskFBO.mergeBuffer();
		this.maskFBO.renderFBO();
		
	}


	
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#getPostRenderFBO()
	 */
	@Override
	public EscapyFBO getPostRenderFBO() {
		return this.maskFBO;
	}

	@Override
	public <T extends EscapyFBO> EscapyPostRenderable setPostRenderFBO(T postRednerFBO)
			throws EscapyFBOTypeException {
		if (postRednerFBO instanceof EscapyMultiFBO)
			this.maskFBO = (EscapyMultiFBO) postRednerFBO;
		else 
			throw new EscapyFBOTypeException();	
		return this;
	}

	



}
