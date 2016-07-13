package com.game.render.fbo.psProcess.mask;

import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.utils.translationVec.TransVec;

public class StandartMask extends EscapyMask {

	private EscapyFBO maskFBO;
	
	protected StandartMask() {
		super();
	}
	protected StandartMask(EscapyGdxCamera postRenderCamera) {
		super(postRenderCamera);
	}

	@Override
	protected void initMask() {
		
		this.maskFBO = new StandartFBO();
		
	}
	
	@Override
	public void postRender(EscapyFBO fbo, TransVec translationVec) {
		
		this.maskFBO.forceWipeFBO();
		fbo.renderToBuffer(maskFBO.getFrameBuffer(), null);
		this.maskFBO.renderFBO();
		
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
