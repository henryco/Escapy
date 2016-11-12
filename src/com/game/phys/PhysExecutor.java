package com.game.phys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.game.render.camera.EscapyGdxCamera;
import com.game.utils.primitives.EscapyGeometry;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Henry on 24/10/16.
 */
public class PhysExecutor {

	private BitmapFont font = new BitmapFont(true);
	private ShapeRenderer renderer = new ShapeRenderer();
	private Batch fntRederer = new SpriteBatch();

	public float gravity_a = 9.8f;
	public float meter = 1;
	private Array<PhysPolygon> physQueue = new Array<>();

	private float delta, wait_time;
	private boolean u_go = true;
	private int normSign = 1;

	public PhysExecutor(){
		this(0);
	}
	public PhysExecutor(float delta){
		setDeltaTime(delta);
	}

	public PhysExecutor wait_ms(float dt) {
		if (!u_go) {
			if ((wait_time -= dt) <= 0) {
				u_go = true;
				wait_time = delta;
			}
		}
		return this;
	}
	public void executePhysics(){
		executePhysics(this.delta);
	}
	public void executePhysics(float dt){

		if (u_go) {
			u_go = false;
			for (int z = 0; z < physQueue.size; z++) {
				PhysPolygon polygon = physQueue.get(z);
				if (!polygon.checkLiveTime(dt)) {
					physQueue.removeIndex(z);
					polygon = physQueue.get(z -= 1);
				}

				float[] vec = new float[2];
				polygon.speed_vec[1] += (gravity_a * dt * meter);
				if (Math.abs(polygon.speed_vec[0]) >= polygon.minSpeed_x) vec[0] = polygon.speed_vec[0];
				if (Math.abs(polygon.speed_vec[1]) >= polygon.minSpeed_y) vec[1] = polygon.speed_vec[1];
				polygon.translate(vec[0], vec[1], polygon.mass);

				for (int i = 0; i < physQueue.size; i++) {
					PhysPolygon polyTarget = physQueue.get(i);
					if (polyTarget != polygon && !polygon.frozen) {
						if (polyTarget.polygon.isCollide(polygon.polygon)){
							float[] counter = polyTarget.polygon.collisionVector(polygon.polygon, polygon.speed_vec[0], polygon.speed_vec[1]);
							if (counter != null) {
								polygon.polygon.counter = counter;
								polygon.translate(counter[0] * normSign, counter[1] * normSign); //mass not necessary here

								float[] n = new float[]{counter[2], counter[3]};

								calculateStrikes(polygon, polyTarget, n, EscapyGeometry.getVector_t(n));

							}
						}
					}
				}
			}
		}
	}

	private static void calculateStrikes(PhysPolygon polygon, PhysPolygon polyTarget, float[] n, float[] t) {

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

		polygon.speed_vec[0] = polygon_v_x * polygon.energyLoss;
		polygon.speed_vec[1] = polygon_v_y * polygon.energyLoss;
		polyTarget.speed_vec[0] = polyTarget_v_x;
		polyTarget.speed_vec[1] = polyTarget_v_y;

		if (Math.abs(polygon.speed_vec[0]) < 0.0001f) polygon.speed_vec[0] = 0;
		if (Math.abs(polygon.speed_vec[1]) < 0.0001f) polygon.speed_vec[1] = 0;
		if (Math.abs(polyTarget.speed_vec[0]) < 0.0001f) polyTarget.speed_vec[0] = 0;
		if (Math.abs(polyTarget.speed_vec[1]) < 0.0001f) polyTarget.speed_vec[1] = 0;

		polygon.checkBounds();
		polygon.updHits();
		polyTarget.checkBounds();
		polyTarget.updHits();
	}

	private int getSign(float val){
		if (val > 0) return 1;
		if (val < 0) return 1;
		return 0;
	}
	public void draw(EscapyGdxCamera camera) {
		renderer.setProjectionMatrix(camera.combined());
		renderer.begin(ShapeRenderer.ShapeType.Line);
		physQueue.forEach(p -> renderer.polygon(p.polygon.getTransformedVertices()));
		renderer.end();
		outStats(camera);
	}
	public PhysExecutor setGravityAcceleration(float g) {
		gravity_a = g;
		return this;
	}
	public PhysExecutor setNormSign(int ns) {
		normSign = ns;
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
	public PhysExecutor load(List<PhysPolygon> polygonList) {
		polygonList.forEach(p -> physQueue.add(p));
		return this;
	}

	private void outStats(EscapyGdxCamera camera){
		fntRederer.setProjectionMatrix(camera.combined());
		fntRederer.begin();
		physQueue.forEach(p -> {
			font.setColor(Color.ROYAL);
			font.draw(fntRederer, p.name, p.polygon.getX() + 70, p.polygon.getY() - 20);
			font.setColor(Color.WHITE);
			font.draw(fntRederer, p.outSpeed(), p.polygon.getX() + 70, p.polygon.getY());
			font.setColor(Color.RED);
			font.draw(fntRederer, p.polygon.outCounter(), p.polygon.getX() + 70, p.polygon.getY()+20);
		});
		fntRederer.end();
	}

}
