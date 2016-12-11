package com.game.boxPhysics;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.boxPhysics.body.IBoxBody;
import com.game.characters.SharedCharacters;
import com.game.render.camera.EscapyGdxCamera;

import java.util.List;

/**
 * @author Henry on 13/11/16.
 */
public class PhysContainer {

	public static float meter_scale = 0.010f;

	private Box2DDebugRenderer shapeRenderer;
	private World world;

	public Vector2 gravity_a = new Vector2(0,0);
	public int positionIterations = 2, velocityIterataions = 6;
	public float delta;
	public boolean visible = false;
	private EscapyGdxCamera debugCam;

	private List<IBoxBody> iBoxBodyList;


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
	public PhysContainer(PhysShapes escapyWalls, SharedCharacters sharedCharacters, Vector2... g) {
		this(escapyWalls, .3f, sharedCharacters, g);
	}
	public PhysContainer(PhysShapes escapyWalls, float delta, SharedCharacters sharedCharacters, Vector2... g) {
		this(delta, g);
		load(escapyWalls, sharedCharacters);
	}

	@SuppressWarnings("unchecked")
	public PhysContainer load(PhysShapes escapyWalls, SharedCharacters sharedCharacters) {
		System.out.println("LOAD SHAPES");
		visible = escapyWalls.visible;
		for (int i = 0; i < escapyWalls.getVertices().size(); i++) {
			float[] wall = escapyWalls.getVertices().get(i);
			float[] scaledWall = new float[wall.length];
			for (int z = 0; z < wall.length; z++) scaledWall[z] = wall[z] * meter_scale;

			BodyDef bodyDef = escapyWalls.getBodyDefs().get(i);
			Body body = world.createBody(bodyDef);
			PolygonShape shape = new PolygonShape();
			shape.set(scaledWall);

			body.createFixture(shape, .0f);
			shape.dispose();
			String n = "";
			for (int z = 0; z < wall.length - 1; z+=2) n += " -> ["+Float.toString(wall[z])+", "+Float.toString(wall[z+1])+"]";
			System.out.println(n);
		}
		System.out.println();
		iBoxBodyList = sharedCharacters.loadBodyList(world, meter_scale);
		return this;
	}

	public void draw(EscapyGdxCamera cam) {
		if (visible) {
			try {
				debugCam.setCameraPosition(cam.getCamera().position.x * meter_scale, cam.getCamera().position.y * meter_scale);
				shapeRenderer.render(world, debugCam.combined());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void update() {
		update(delta);
	}


	public void update(float delta) {


		iBoxBodyList.forEach(boxBody -> boxBody.updateBody(delta, world));
		world.step(delta, velocityIterataions, positionIterations);
		iBoxBodyList.forEach(IBoxBody::updateHolder);

	}



	public PhysContainer setDebugCamera(EscapyGdxCamera cam) {
		float w = cam.getCamera().viewportWidth;
		float h = cam.getCamera().viewportHeight;
		debugCam = new EscapyGdxCamera(true, (int)(w * meter_scale), (int)(h * meter_scale));
		return this;
	}
}
