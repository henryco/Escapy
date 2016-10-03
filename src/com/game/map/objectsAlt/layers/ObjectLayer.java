package com.game.map.objectsAlt.layers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.map.objectsAlt.objects.GameObject;
import com.game.render.camera.EscapyGdxCamera;
import com.game.utils.absContainer.EscapyAbsContainer;

import java.util.function.Consumer;

/**
 * @author Henry on 02/10/16.
 */
public class ObjectLayer extends EscapyAbsContainer<GameObject> {

	private Batch batch = new SpriteBatch();


	public ObjectLayer() {
		super();
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

	private void render(EscapyGdxCamera camera, Consumer<GameObject> consumer) {

		batch.setProjectionMatrix(camera.combined());
		batch.begin();
		forEach(consumer);
		batch.end();
	}
}
