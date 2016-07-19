package com.game.render.fbo.psProcess.lights.vol;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.render.fbo.psProcess.EscapyPostProcessed;

// TODO: Auto-generated Javadoc
/**
 * The Class AbsVolLight.
 */
public abstract class AbsVolLight implements EscapyPostProcessed {

	/** The position. */
	protected Vector2 position;
	
	/** The color. */
	protected Vector3 color;

	/** The intencity. */
	protected float intencity;
	
	/** The distance. */
	protected float distance;
	
	/** The id. */
	protected int id;

	/** The b. */
	public float r, g, b;

	/**
	 * Instantiates a new abs light.
	 */
	public AbsVolLight() {

	}

	/**
	 * Instantiates a new abs light.
	 *
	 * @param position
	 *            the position
	 * @param color
	 *            the color
	 * @param intencity
	 *            the intencity
	 * @param distance
	 *            the distance
	 */
	public AbsVolLight(Vector2 position, Vector3 color, float intencity, float distance) {
		this.position = new Vector2(position);
		this.color = new Vector3(color);
		this.intencity = intencity;
		this.distance = distance;
		this.id = this.hashCode();
		this.genPublicColors();
	}
	
	/**
	 * Gen public colors.
	 */
	protected void genPublicColors() {
		this.r = this.color.x;
		this.g = this.color.y;
		this.b = this.color.z;
	}

	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position
	 *            the position
	 * @return the abs light
	 */
	public AbsVolLight setPosition(Vector2 position) {
		this.position = position;
		return this;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Vector3 getColor() {
		this.genPublicColors();
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the color
	 * @return the abs light
	 */
	public AbsVolLight setColor(Vector3 color) {
		this.color = color;
		this.genPublicColors();
		return this;
	}

	/**
	 * Gets the intencity.
	 *
	 * @return the intencity
	 */
	public float getIntencity() {
		return intencity;
	}

	/**
	 * Sets the intencity.
	 *
	 * @param intencity
	 *            the intencity
	 * @return the abs light
	 */
	public AbsVolLight setIntencity(float intencity) {
		this.intencity = intencity;
		return this;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distance
	 *            the distance
	 * @return the abs light
	 */
	public AbsVolLight setDistance(float distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
}
