package com.game.map.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;
import com.game.render.camera.EscapyGdxCamera;

/**
 * @author Henry on 19/10/16.
 */
public class ParticleContainer {

	private Batch batch;

	public void renderParticles(EscapyGdxCamera camera) {

		batch.setProjectionMatrix(camera.combined());
		batch.begin();

		batch.end();
	}

}
