package com.game.render.fbo.psProcess.mask;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.StandartMultiFBO;
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
	protected StandartMask() {
		super();
	}
	
	/**
	 * Instantiates a new standart mask.
	 *
	 * @param postRenderCamera
	 *            the post render camera
	 */
	protected StandartMask(EscapyGdxCamera postRenderCamera) {
		super(postRenderCamera);
	}

	/* (non-Javadoc)
	 * @see com.game.render.fbo.psProcess.mask.EscapyMask#initMask()
	 */
	@Override
	protected void initMask() {
		
		//this.maskFBO = new EscapyStandartMul();//.setRenderProgram(null); //TODO not null!
		this.maskFBO = new StandartMultiFBO();
	}
	
	/**
	 * Post render.
	 *
	 * @param fbo
	 *            the fbo
	 * @param translationVec
	 *            the translation vec
	 */
	@Override
	public void postRender(EscapyFBO fbo, TransVec translationVec) {
		
		this.maskFBO.forceWipeFBO();
		fbo.renderToBuffer(maskFBO.getFrameBuffer());
		this.maskFBO.renderFBO();

	}

	@Override
	public EscapyMask addMaskTarget(FrameBuffer targetBuffer) {
		((StandartMultiFBO)this.maskFBO).addMultiFrameBuffer(targetBuffer);
		return this;
	}
	

	



}
