package com.game.render.fbo.psProcess.mask;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * Simply lightmask extends {@link EscapyMask}.
 *
 * @author Henry
 */
public class StandartMask extends EscapyMask {

	private EscapyFBO maskFBO;
	
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
	protected void initMask() {
		
		this.maskFBO = new StandartMultiFBO();
		//this.maskFBO.setRenderProgram(new FBOMultiplyMaskProgram(maskFBO)); //TODO
	}
	


	/* (non-Javadoc)
	 * @see com.game.render.fbo.psProcess.mask.EscapyMask#addMaskTarget(com.badlogic.gdx.graphics.glutils.FrameBuffer)
	 */
	@Override
	public EscapyMask addMaskTarget(FrameBuffer targetBuffer) {
		((EscapyMultiFBO) this.maskFBO).addMultiFrameBuffer(targetBuffer);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderer#postRender(com.game.render.fbo.EscapyFBO, com.game.utils.translationVec.TransVec)
	 */
	@Override
	public EscapyFBO postRender(EscapyFBO fbo, TransVec translationVec) {
		fbo.begin();
			this.postRender(translationVec);
		fbo.end();
		return fbo;
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderer#postRender(com.game.utils.translationVec.TransVec)
	 */
	@Override
	public void postRender(TransVec translationVec) {
		//this.maskFBO = super.applyColor(this.maskFBO);
		//fbo.renderToBuffer(maskFBO.getFrameBuffer());
		//this.maskFBO.renderFBO();
				
		super.applyColor(this.maskFBO);
		this.maskFBO.renderFBO();
		
	}


	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#setPostRenderFBO(com.game.render.fbo.EscapyFBO)
	 */
	@Override
	public <T extends EscapyFBO> EscapyPostRenderable setPostRenderFBO(T postRednerFBO) {
		this.maskFBO = postRednerFBO;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.fbo.psRender.EscapyPostRenderable#getPostRenderFBO()
	 */
	@Override
	public EscapyFBO getPostRenderFBO() {
		return this.maskFBO;
	}

	



}
