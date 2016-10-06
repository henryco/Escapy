package com.game.map.objects.layers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.game.map.objects.layers.utils.LayerCameraShift;
import com.game.render.EscapyUniRender;
import com.game.render.camera.EscapyGdxCamera;
import com.game.utils.absContainer.EscapyAbsContainer;

import java.util.function.Consumer;

/**
 * @author Henry on 02/10/16.
 */
public class ObjectLayer extends EscapyAbsContainer<EscapyUniRender> {

	private Batch batch = new SpriteBatch();
	public final String name;

	private LayerCameraShift cameraShift = (camera, pos) -> camera;


	public ObjectLayer(final float xShift, final float yShift, final int frameW, final int frameH, String ... name) {
		super();

		final float[] frameVec = new float[]{0.5f * frameW, 0.5f * frameH};
		cameraShift = (camera, pos) -> {
			camera.setCameraPosition((xShift * (pos[0] - frameVec[0])) + frameVec[0], (yShift * (pos[1] - frameVec[1])) + frameVec[1]);
			return camera;
		};

		this.name = setName(name);
		System.out.println(this);
	}
	public ObjectLayer(final int frameW, final int frameH, String ... name) {
		super();
		this.name = setName(name);
		System.out.println(this);
	}


	public void renderGraphic(EscapyGdxCamera escapyCamera) {

		float[] pos = getCamPos(escapyCamera);
		escapyCamera = cameraShift.shiftCamera(escapyCamera, pos);
		escapyCamera.update();

		render(escapyCamera, t -> t.renderGraphic(batch));

		escapyCamera.setCameraPosition(pos);
	}

	public void renderNormals(EscapyGdxCamera escapyCamera) {

		float[] pos = getCamPos(escapyCamera);
		escapyCamera = cameraShift.shiftCamera(escapyCamera, pos);
		escapyCamera.update();

		render(escapyCamera, t -> t.renderNormals(batch));

		escapyCamera.setCameraPosition(pos);
	}

	public void renderLightMap(EscapyGdxCamera escapyCamera) {

		float[] pos = getCamPos(escapyCamera);
		escapyCamera = cameraShift.shiftCamera(escapyCamera, pos);
		escapyCamera.update();

		render(escapyCamera, t -> t.renderLightMap(batch));

		escapyCamera.setCameraPosition(pos);
	}

	private void render(EscapyGdxCamera camera, Consumer<EscapyUniRender> consumer) {

		batch.setProjectionMatrix(camera.combined());
		batch.begin();
		forEach(consumer);
		batch.end();
	}

	private static String setName(String ... name) {
		String tmp = "";
		for (String n : name) tmp += n;
		return tmp;
	}

	private static float[] getCamPos(EscapyGdxCamera camera){
		Vector3 pos = camera.getCamera().position;
		return new float[]{pos.x, pos.y};
	}
	@Override
	public String toString() {
		return "COMPILED: "+this.name+ " layer";
	}
}
