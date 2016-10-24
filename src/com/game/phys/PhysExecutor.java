package com.game.phys;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.game.render.camera.EscapyGdxCamera;

/**
 * @author Henry on 24/10/16.
 */
public class PhysExecutor {

	private ShapeRenderer renderer = new ShapeRenderer();
	public float gravity_a = 9.8f;
	public float meter = 1;
	private Array<PhysPolygon> physQueue = new Array<>();

	public PhysExecutor(){

	}

	public void executePhysics(float delta){
		float g = delta * gravity_a;
		for (PhysPolygon polygon : physQueue) {
			if (!polygon.frozen) {
				polygon.speed_vec[1] += g;
				polygon.translate((polygon.speed_vec[0] * delta) * meter, (polygon.speed_vec[1] * delta) * meter);
			}
		}
	}

	public void draw(EscapyGdxCamera camera) {
		renderer.setProjectionMatrix(camera.combined());
		renderer.begin(ShapeRenderer.ShapeType.Line);
		physQueue.forEach(p -> renderer.polygon(p.polygon.getTransformedVertices()));
		renderer.end();
	}

	public PhysExecutor setGravityAcceleration(float g) {
		gravity_a = g;
		return this;
	}
	public PhysExecutor addPhysObjectToQueue(PhysPolygon polygon) {
		physQueue.add(polygon);
		return this;
	}
}
