package com.game.phys;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.game.render.camera.EscapyGdxCamera;
import com.game.utils.primitives.EscapyGeometry;

import java.util.function.Consumer;

/**
 * @author Henry on 24/10/16.
 */
public class PhysExecutor implements EscapyGeometry {

	private ShapeRenderer renderer = new ShapeRenderer();
	public float gravity_a = 9.8f;
	public float meter = 1;
	private Array<PhysPolygon> physQueue = new Array<>();

	private float delta;

	public PhysExecutor(){
		this(0);
	}
	public PhysExecutor(float delta){
		setDeltaTime(delta);
	}

	public void executePhysics(){
		executePhysics(this.delta);
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

						float[] n = new float[]{counter[2], counter[3]};
						float[] t = new float[]{-counter[2], counter[3]};
						if (n[0] == 0 && n[1] == 1) {
							t[0] = -1;
							t[1] = 0;
						} else if (n[0] == -1 && n[1] == 0) {
							t[0] = 0;
							t[1] = 1;
						} else if (n[0] == 0 && n[1] == -1) {
							t[0] = 1;
							t[1] = 0;
						} else if (n[0] == 1 && n[1] == 0) {
							t[0] = 0;
							t[1] = -1;
						}

						float m_sum = (polygon.mass + polyTarget.mass);
						float u_polygon = polygon.mass / m_sum;
						float u_polyTarget = polyTarget.mass / m_sum;

						float proj_polygon_n = EscapyGeometry.dot2(n, polygon.speed_vec);
						float proj_polygon_t = EscapyGeometry.dot2(t, polygon.speed_vec);
						float proj_polyTarget_n = EscapyGeometry.dot2(n, polyTarget.speed_vec);
						float proj_polyTarget_t = EscapyGeometry.dot2(t, polyTarget.speed_vec);

						float prim_proj_polygon_n = (u_polygon - u_polyTarget) * proj_polygon_n + 2 * (u_polyTarget * proj_polyTarget_n);
						float prim_proj_polyTarget_n = (u_polyTarget - u_polygon) * proj_polyTarget_n + 2 * (u_polygon * proj_polygon_n);

						float polygon_v_x = prim_proj_polygon_n * n[0] + proj_polygon_t * t[0];
						float polygon_v_y = prim_proj_polygon_n * n[1] + proj_polygon_t * t[1];
						float polyTarget_v_x = prim_proj_polyTarget_n * n[0] + proj_polyTarget_t * t[0];
						float polyTarget_v_y = prim_proj_polyTarget_n * n[1] + proj_polyTarget_t * t[1];

						polygon.setSpeedX(polygon_v_x);
						polygon.setSpeedY(polygon_v_y);
						polyTarget.setSpeedX(polyTarget_v_x);
						polyTarget.setSpeedY(polyTarget_v_y);
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
	public PhysExecutor setDeltaTime(float delta) {
		this.delta = delta;
		return this;
	}
}
