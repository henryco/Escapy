package com.game.map.objectsAlt.layers;

import com.game.map.objectsAlt.layers.utils.UniMaskRenderer;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.mask.LightMask;
import com.game.utils.arrContainer.EscapyArrContainer;

/**
 * @author Henry on 02/10/16.
 */
public class LayerContainer extends EscapyArrContainer <ObjectLayer> {

	private UniMaskRenderer maskRenderer = (fbo, mask1) -> fbo.renderFBO();

	public EscapyFBO layerFBO;
	public LightMask mask;

	public LayerContainer() {
		super(ObjectLayer.class);
	}
	public LayerContainer(int[] dimension, String ... name) {
		super(ObjectLayer.class);
		this.initFBO(dimension, name);
	}

	public LayerContainer initFBO(int[] dim, String ... name) {
		if (dim.length == 2) this.layerFBO = new StandartFBO(new int[]{0, 0, dim[0], dim[1]}, name);
		else if (dim.length == 4) this.layerFBO = new StandartFBO(dim, name);
		return this;
	}
	public LayerContainer setMask(LightMask mask) {
		if (mask != null) {
			if (mask.buffered) maskRenderer = (fbo, mask1) -> mask1.renderMaskBuffered(fbo.getTextureRegion().getTexture()).renderFBO();
			else maskRenderer = (fbo, mask1) -> mask1.renderMask(fbo.getTextureRegion().getTexture());
		}
		this.mask = mask;
		return this;
	}

	public LayerContainer prepareContained(EscapyGdxCamera camera) {
		layerFBO.begin().wipeFBO();
		forEach(objectLayer -> objectLayer.renderGraphic(camera));
		layerFBO.end();
		return this;
	}

	public void renderContained(){
		maskRenderer.renderUniMask(layerFBO, mask);
	}

}
