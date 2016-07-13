package com.x.game.render.extra.normals;

import com.x.game.render.EscapyGdxCamera;
import com.x.game.render.extra.ExtraRendererSuper;

public class NormalRenderer extends ExtraRendererSuper<EscapyNormalRender> {

	
	public NormalRenderer(EscapyNormalRender target) {
		super(target);
	}

	@Override
	public void extraRender(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
	
		super.renderTarget.renderNormals(translationMatrix, escapyCamera);
	}

	

}
