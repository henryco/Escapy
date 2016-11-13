package com.game.boxPhysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.render.camera.EscapyGdxCamera;

/**
 * @author Henry on 13/11/16.
 */
public class PhysContainer {

	private Box2DDebugRenderer shapeRenderer;
	private World world;

	public Vector2 gravity_a = new Vector2(0,0);
	public int positionIterations = 2, velocityIterataions = 6;
	public float delta;
	public boolean visible = false;

	public PhysContainer(Vector2 ... g) {
		shapeRenderer = new Box2DDebugRenderer();
		if (g != null && g.length > 0) gravity_a = g[0];
		world = new World(gravity_a, true);
		delta = .3f;
	}
	public PhysContainer(float delta, Vector2 ... g) {
		this(g);
		this.delta = delta;
	}
	public PhysContainer(PhysShapes escapyWalls, Vector2... g) {
		this(escapyWalls, .3f, g);
	}
	public PhysContainer(PhysShapes escapyWalls, float delta, Vector2... g) {
		this(delta, g);
		load(escapyWalls);
	}

	public PhysContainer load(PhysShapes escapyWalls) {
		System.out.println("LOAD SHAPES");
		visible = escapyWalls.visible;
		for (int i = 0; i < escapyWalls.getVertices().size(); i++) {
			float[] wall = escapyWalls.getVertices().get(i);
			BodyDef bodyDef = escapyWalls.getBodyDefs().get(i);
			Body body = world.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			shape.set(wall);
			body.createFixture(shape, .0f);
			shape.dispose();
			String n = "";
			for (int z = 0; z < wall.length - 1; z+=2) n += " -> ["+Float.toString(wall[z])+", "+Float.toString(wall[z+1])+"]";
			System.out.println(n);
		}
		System.out.println();
		return this;
	}

	public void draw(EscapyGdxCamera camera) {
		if (visible) shapeRenderer.render(world, camera.combined());
	}
	public void update(float delta) {
		world.step(delta, velocityIterataions, positionIterations);
	}
	public void update() {
		update(delta);
	}
}
