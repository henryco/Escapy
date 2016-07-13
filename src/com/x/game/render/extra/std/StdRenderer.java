package com.x.game.render.extra.std;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.EscapyRenderable;
import com.x.game.render.extra.ExtraRendererSuper;

public class StdRenderer extends ExtraRendererSuper <EscapyRenderable>{

	
	public StdRenderer(EscapyRenderable target) {
		super(target);
	}

	@Override
	public void extraRender(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		
		super.renderTarget.renderGraphic(translationMatrix, escapyCamera);
	}

}
