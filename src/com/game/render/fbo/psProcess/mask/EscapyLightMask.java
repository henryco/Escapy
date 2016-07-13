package com.game.render.fbo.psProcess.mask;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.psRender.EscapyPostRenderable;
import com.game.utils.translationVec.TransVec;


public class EscapyLightMask implements EscapyPostRenderable {

	private ArrayList<EscapyMask> maskList;
	private EscapyGdxCamera postRenderCamera;
	
	public EscapyLightMask()
	{
		this.maskList = new ArrayList<>();
		postRenderCamera = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return;
	}
	public EscapyLightMask(EscapyGdxCamera camera)
	{
		this.maskList = new ArrayList<>();
		this.postRenderCamera = camera;
		return;
	}
	
	public EscapyMask standartMask()
	{
		this.maskList.add(new StandartMask(this.postRenderCamera));
		return maskList.get(maskList.size()-1);
	}
	
	public EscapyMask standartMask(EscapyGdxCamera camera)
	{
		this.maskList.add(new StandartMask(camera));
		return maskList.get(maskList.size()-1);
	}
	
	public boolean removeMask(EscapyMask mask)
	{
		if (maskList.contains(mask))
		{
			maskList.remove(mask);
			return true;
		}	return false;
	}


	@Override
	public void postRender(EscapyFBO fbo, TransVec translationVec) {
		
		for (EscapyMask mask : maskList) {
			mask.postRender(fbo, translationVec);
		}
	}

	@Override
	public EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}

}
