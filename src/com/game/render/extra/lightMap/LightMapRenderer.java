package com.game.render.extra.lightMap;

import com.game.render.camera.EscapyGdxCamera;
import com.game.render.extra.ExtraRendererSuper;

public class LightMapRenderer extends ExtraRendererSuper<EscapyLightMapRenderer>{

	public LightMapRenderer(EscapyLightMapRenderer target) {
		super(target);
	}

	@Override
	public void extraRender(float[] translationVec, EscapyGdxCamera escapyCamera) {
		
		super.renderTarget.renderLightMap(translationVec, escapyCamera);
	}

}
