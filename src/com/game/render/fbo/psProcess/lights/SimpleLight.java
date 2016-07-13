package com.game.render.fbo.psProcess.lights;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SimpleLight extends AbsLight {

	private Vector2 dimension;

	public SimpleLight() {
		super();
	}

	public SimpleLight(Vector2 dimension, Vector2 position, Vector3 color, float intencity, float distance) {
		super(position, color, intencity, distance);
		this.dimension = new Vector2(dimension);
		return;
	}

	public SimpleLight(float[] dimension, float[] position, float[] color, float intencity, float distance) {
		super(new Vector2(position[0], position[1]), new Vector3(color[0], color[1], color[2]), intencity, distance);
		this.dimension = new Vector2(dimension[0], dimension[1]);
		return;
	}

	public Vector2 getDimension() {
		return dimension;
	}

	public void setDimension(Vector2 dimension) {
		this.dimension = dimension;
	}

}
