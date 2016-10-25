package com.game.phys;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.game.render.camera.EscapyGdxCamera;

import java.util.function.Consumer;

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
			polygon.speed_vec[1] += g;
			float[] mov_vec = new float[]{(polygon.speed_vec[0] * delta) * meter, (polygon.speed_vec[1] * delta) * meter};
			polygon.translate(mov_vec[0], mov_vec[1], polygon.mass);
			for (int i = 0; i < physQueue.size; i++) {
				PhysPolygon polyTarget = physQueue.get(i);
				if (polyTarget != polygon) {
					float[] counter = polygon.polygon.getCollisionVector(mov_vec, polyTarget.polygon);
					if (counter != null) {
						polygon.translate(counter[0], counter[1]); //mass not necessary here
						polygon.speed_vec[1] = 0;
					}
				}
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
	public void forEach(Consumer<PhysPolygon> p) {
		physQueue.forEach(p);
	}
	public PhysPolygon getPhysPolygon(String name) {
		for (PhysPolygon p : physQueue) if (p.name.equalsIgnoreCase(name)) return p;
		return null;
	}
	public PhysPolygon getPhysPolygon(int index) {
		return physQueue.get(index);
	}
}
