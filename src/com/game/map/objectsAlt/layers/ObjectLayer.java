package com.game.map.objectsAlt.layers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	public ObjectLayer(float xShift, float yShift, String ... name) {
		super();

		this.name = setName(name);
		System.out.println(this);
	}
	public ObjectLayer(String ... name) {
		super();
		this.name = setName(name);
		System.out.println(this);
	}


	public void renderGraphic(EscapyGdxCamera escapyCamera) {

		escapyCamera.update();
		render(escapyCamera, t -> t.renderGraphic(batch));
	}

	public void renderNormals(EscapyGdxCamera escapyCamera) {

		escapyCamera.update();
		render(escapyCamera, t -> t.renderNormals(batch));
	}

	public void renderLightMap(EscapyGdxCamera escapyCamera) {

		escapyCamera.update();
		render(escapyCamera, t -> t.renderLightMap(batch));
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

	@Override
	public String toString() {
		return "COMPILED: "+this.name+ " layer";
	}
}
