package com.game.render.fbo.psProcess.mask;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
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
		
		this.maskFBO = new StandartFBO();
		
	}
	
	/**
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
		//super.postRenderCamera.update();
		//super.maskBatch.setProjectionMatrix(super.postRenderCamera.combined());
		
		
		/*		
		GL20.glEnable(GL20.GL_BLEND);
		GL11.glBlendFunc(super.modeType[0], super.modeType[1]);
		g.setColor(super.COLOR);
		g.fillRect(super.startX, super.startY, super.WIDTH, super.HEIGHT);
		GL11.glDisable(GL11.GL_BLEND);
		g.setDrawMode(Graphics.MODE_NORMAL);
	*/
	}
	

	



}
