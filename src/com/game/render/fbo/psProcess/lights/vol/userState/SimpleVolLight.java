package com.game.render.fbo.psProcess.lights.vol.userState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.render.fbo.psProcess.lights.vol.AbsVolLight;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleVolLight.
 */
public class SimpleVolLight extends AbsVolLight {

	private Vector2 dimension;

	/**
	 * Instantiates a new simple light.
	 */
	public SimpleVolLight() {
		super();
	}

	/**
	 * Instantiates a new simple light.
	 *
	 * @param dimension
	 *            the dimension
	 * @param position
	 *            the position
	 * @param color
	 *            the color
	 * @param intencity
	 *            the intencity
	 * @param distance
	 *            the distance
	 */
	public SimpleVolLight(Vector2 dimension, Vector2 position, Vector3 color, float intencity, float distance) {
		super(position, color, intencity, distance);
		this.dimension = new Vector2(dimension);
		return;
	}

	/**
	 * Instantiates a new simple light.
	 *
	 * @param dimension
	 *            the dimension
	 * @param position
	 *            the position
	 * @param color
	 *            the color
	 * @param intencity
	 *            the intencity
	 * @param distance
	 *            the distance
	 */
	public SimpleVolLight(float[] dimension, float[] position, float[] color, float intencity, float distance) {
		super(new Vector2(position[0], position[1]), new Vector3(color[0], color[1], color[2]), intencity, distance);
		this.dimension = new Vector2(dimension[0], dimension[1]);
		return;
	}

	/**
	 * Gets the dimension.
	 *
	 * @return the dimension
	 */
	public Vector2 getDimension() {
		return dimension;
	}

	/**
	 * Sets the dimension.
	 *
	 * @param dimension
	 *            the new dimension
	 */
	public void setDimension(Vector2 dimension) {
		this.dimension = dimension;
	}

}
