package com.game.map.objects.layers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.map.objects.layers.utils.UniMaskRenderer;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.cont.EscapyLightContainer;
import com.game.render.fbo.psProcess.cont.init.EscapyLights;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.render.mask.LightMask;
import com.game.render.program.shader.blend.EscapyBlendRenderer;
import com.game.render.program.shader.blend.ShaderBlendProgram;
import com.game.utils.arrContainer.EscapyArrContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Henry on 02/10/16.
 */
public class LayerContainer extends EscapyArrContainer <ObjectLayer> {

	private UniMaskRenderer maskRenderer = (fbo, mask1) -> fbo.renderFBO();

	public EscapyFBO layerFBO, actualFBO, otherFBO;
	public LightMask mask;
	public EscapyLights lights = null;
	public int[][] lightID;

	private Batch batch = new SpriteBatch();
	private EscapyBlendRenderer blender = ShaderBlendProgram.blendProgram("max");

	public LayerContainer() {
		super(ObjectLayer.class);
	}
	public LayerContainer(int[] dimension, String ... name) {
		super(ObjectLayer.class);
		this.initFBO(dimension, name);
	}

	public LayerContainer initFBO(int[] dim, String ... name) {
		if (dim.length == 2) {
			this.layerFBO = new StandartFBO(new int[]{0, 0, dim[0], dim[1]}, name);
			this.otherFBO = new StandartFBO(new int[]{0, 0, dim[0], dim[1]}, "CORR_", name[0]);
		}
		else if (dim.length == 4) {
			this.layerFBO = new StandartFBO(dim, name);
			this.otherFBO = new StandartFBO(dim, "CORR_", name[0]);
		}
		actualFBO = layerFBO;
		return this;
	}
	public LayerContainer setMask(LightMask mask) {
		if (mask != null)
			if (mask.buffered) maskRenderer = (fbo, mask1) -> actualFBO = mask1.renderMaskBuffered(fbo.getTextureRegion().getTexture()).renderFBO();
			else maskRenderer = (fbo, mask1) -> mask1.renderMask(fbo.getTextureRegion().getTexture());
		this.mask = mask;
		return this;
	}

	public LayerContainer prepareContained(EscapyGdxCamera camera) {
		layerFBO.begin().wipeFBO();
		forEach(objectLayer -> objectLayer.renderGraphic(camera));
		layerFBO.end();
		return this;
	}

	public LayerContainer renderContained(){
		maskRenderer.renderUniMask(layerFBO, mask);
		return this;
	}

	public LayerContainer setLights(EscapyLights lights) {
		this.lights = lights;
		return this;
	}

	public LayerContainer makeAndRenderLights(EscapyGdxCamera camera, EscapyFBO lightBuffFBO) {

		if (lights != null) {
			otherFBO.forceWipeFBO();
			lights.forEach(l -> l.makeLights().renderBlendedLights(camera, layerFBO.getSpriteRegion(), otherFBO));
			lightBuffFBO.begin();
			blender.renderBlended(actualFBO.getSpriteRegion(), otherFBO.getSpriteRegion(), otherFBO.getFBOCamera().getCamera());
			lightBuffFBO.end();
		}
		return this;
	}
	public void makeLights() {
		if (lights != null) lights.forEach(EscapyLightContainer::makeLights);
	}
	public void renderLights(EscapyGdxCamera camera, EscapyFBO lightBuffFBO) {
		if (lights != null) lights.forEach(l -> l.renderBlendedLights(camera, layerFBO.getSpriteRegion(), lightBuffFBO));
	}

	public AbsStdLight getSourceByID(int id) {
		return lights.lightContainers[lightID[id][0]].getSourceByID(lightID[id][1]);
	}
	public int[] getIndexID() {

		List<Integer> id = new ArrayList<>();
		for (EscapyLightContainer c : lights.lightContainers) c.forEach(l -> id.add(l.getID()));
		int[] arr = new int[id.size()];
		for (int i = 0; i < id.size(); i++) arr[i] = id.get(i);
		return arr;
	}

}
