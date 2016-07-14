package com.game.render.fbo.psProcess.mask;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.translationVec.TransVec;

/**
 * Queued factory of simple GL based light mask {@link EscapyMask}, <br>
 * that implements {@link EscapyPostRenderable} and work with FBO <br>
 * see: {@link EscapyFBO}.
 * @author Henry
 *
 */
public class EscapyLightMask implements EscapyPostRenderable {

	private ArrayList<EscapyMask> maskList;
	private EscapyGdxCamera postRenderCamera;
	
	/**
	 * Create queued lightmask factory.
	 */
	public EscapyLightMask()
	{
		this.maskList = new ArrayList<>();
		postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return;
	}
	
	/** 
	 * Create queued lightmask factory.
	 * @param camera - {@link EscapyGdxCamera} object cannot be null.
	 */
	public EscapyLightMask(EscapyGdxCamera camera)
	{
		this.maskList = new ArrayList<>();
		this.postRenderCamera = camera;
		return;
	}
	
	/**
	 * Create standart mask, with default internal camera;
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
	 * @return <b>true</b> if removed</br> <b>false</b> if not.
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
	 * @see EscapyPostRenderable
	 * @see EscapyFBO
	 * @see TransVec
	 */
	@Override
	public void postRender(EscapyFBO fbo, TransVec translationVec) {
		
		for (EscapyMask mask : maskList) {
			mask.postRender(fbo, translationVec);
		}
	}
	
	/**
	 * @param camera - {@link EscapyGdxCamera} object cannot be null.
	 */
	@Override
	public EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}

}
