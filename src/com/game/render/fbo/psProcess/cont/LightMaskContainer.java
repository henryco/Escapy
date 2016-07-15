package com.game.render.fbo.psProcess.cont;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psProcess.mask.EscapyMask;
import com.game.render.fbo.psProcess.mask.StandartMask;
import com.game.render.fbo.psRender.EscapyPostRenderer;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * Queued factory of simple GL based light mask {@link EscapyMask}, <br>
 * that implements {@link EscapyPostRenderable} and work with FBO <br>
 * see: {@link EscapyFBO}.
 * @author Henry
 *
 */
public class LightMaskContainer implements EscapyPostRenderer {

	private ArrayList<EscapyMask> maskList;
	private EscapyGdxCamera postRenderCamera;
	
	/**
	 * Create queued lightmask factory.
	 */
	public LightMaskContainer()
	{
		this.maskList = new ArrayList<>();
		postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return;
	}
	
	/** 
	 * Create queued lightmask factory.
	 * @param camera - {@link EscapyGdxCamera} object cannot be null.
	 */
	public LightMaskContainer(EscapyGdxCamera camera)
	{
		this.maskList = new ArrayList<>();
		this.postRenderCamera = camera;
		return;
	}
	
	/**
	 * Create standart mask, with default internal camera;.
	 *
	 * @return {@link StandartMask}
	 */
	public EscapyMask standartMask()
	{
		this.maskList.add(new StandartMask(this.postRenderCamera));
		return maskList.get(maskList.size()-1);
	}
	
	/**
	 * Create standart mask.
	 * @param camera - {@link EscapyGdxCamera} object cannot be null.
	 * @return {@link StandartMask}
	 */
	public EscapyMask standartMask(EscapyGdxCamera camera)
	{
		this.maskList.add(new StandartMask(camera));
		return maskList.get(maskList.size()-1);
	}
	
	/**
	 * Remove mask from postrender queue.
	 * @param mask - {@link EscapyMask} object.
	 * @return <b>true</b> if removed<br> <b>false</b> if not.
	 */
	public boolean removeMask(EscapyMask mask)
	{
		if (maskList.contains(mask))
		{
			maskList.remove(mask);
			return true;
		}	return false;
	}

	/**
	 * Post render.
	 *
	 * @param fbo
	 *            the fbo
	 * @param translationVec
	 *            the translation vec
	 * @return the escapy FBO
	 * @see EscapyPostRenderable
	 * @see EscapyFBO
	 * @see TransVec
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
		for (EscapyMask mask : maskList) 
			mask.postRender(translationVec);
	}

	
	
	

}
