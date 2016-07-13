package com.game.render.extra.normals;

import com.game.render.EscapyGdxCamera;
import com.game.render.extra.ExtraRendererSuper;

public class NormalRenderer extends ExtraRendererSuper<EscapyNormalRender> {

	
	public NormalRenderer(EscapyNormalRender target) {
		super(target);
	}

	@Override
	public void extraRender(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
	
		super.renderTarget.renderNormals(translationMatrix, escapyCamera);
	}

	

}
