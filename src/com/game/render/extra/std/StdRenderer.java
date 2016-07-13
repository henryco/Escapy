package com.game.render.extra.std;

import com.game.render.EscapyGdxCamera;
import com.game.render.EscapyRenderable;
import com.game.render.extra.ExtraRendererSuper;

public class StdRenderer extends ExtraRendererSuper <EscapyRenderable>{

	
	public StdRenderer(EscapyRenderable target) {
		super(target);
	}

	@Override
	public void extraRender(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		
		super.renderTarget.renderGraphic(translationMatrix, escapyCamera);
	}

}
