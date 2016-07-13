package com.x.game.render.fbo.psProcess.cont;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.fbo.EscapyFBO;
import com.x.game.render.fbo.psProcess.lights.AbsLight;
import com.x.game.render.fbo.psRender.EscapyPostRenderable;
import com.x.game.utils.absContainer.EscapyContainer;
import com.x.game.utils.translationVec.TransVec;

public class VolumeLightsContainer implements EscapyPostRenderable,
	EscapyContainer<AbsLight> {

	private List<AbsLight> volumeLights, buffer;
	private EscapyGdxCamera postRenderCamera;
	
	public VolumeLightsContainer() {
		
		this.volumeLights = new ArrayList<>();
		this.buffer = new ArrayList<>();
		this.setPostRenderCamera(new EscapyGdxCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()));
		return;
	}
	
	public VolumeLightsContainer(EscapyGdxCamera postRenderCamera) {
		
		this.volumeLights = new ArrayList<>();
		this.buffer = new ArrayList<>();
		this.setPostRenderCamera(postRenderCamera);
		return;
	}

	
	@Override
	public void postRender(EscapyFBO fbo, TransVec translationVec) {
		
		for (AbsLight light : volumeLights) {
		//	light.getPosition().add(camera.getTranslationVector());
			light.getPosition().sub(translationVec.getTranslationVector());
			fbo.renderFBO(postRenderCamera, light);
		}
	}

	
	@Override
	public int addSource(AbsLight light) {
		this.volumeLights.add(light);
		return volumeLights.get(volumeLights.size() - 1).getId();
	}
	
	
	@Override
	public AbsLight getSourceByID(int ID) {
		for (AbsLight lightBuff : buffer)
			if (lightBuff.getId() == ID)
				return lightBuff;
			
		for (AbsLight light : volumeLights)
			if (light.getId() == ID) {
				buffer.add(light);
				return light;
			}
		return null;
	}
	
	
	@Override
	public boolean removeSourceByID(int ID) {
		for (AbsLight light : volumeLights)
			if (light.getId() == ID) {
				buffer.remove(light);
				return volumeLights.remove(light);
			}
		return false;
	}
	
	
	@Override
	public boolean removeSource(AbsLight light) {
		buffer.remove(light);
		return volumeLights.remove(light);
	}
	
	
	public PostProcessedProxy<AbsLight, EscapyContainer<AbsLight>> getLight(int id) {
		return new PostProcessedProxy<AbsLight, EscapyContainer<AbsLight>>(id, this);
	}
	
	
	
	
	@Override
	public EscapyPostRenderable setPostRenderCamera(EscapyGdxCamera camera) {
		this.postRenderCamera = camera;
		return this;
	}

	
	
}
