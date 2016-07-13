package com.game.render.fbo.psProcess.lights;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.render.fbo.psProcess.EscapyPostProcessed;

public abstract class AbsLight implements EscapyPostProcessed {

	protected Vector2 position;
	protected Vector3 color;

	protected float intencity;
	protected float distance;
	protected int id;

	public float r, g, b;

	public AbsLight() {

	}

	public AbsLight(Vector2 position, Vector3 color, float intencity, float distance) {
		this.position = new Vector2(position);
		this.color = new Vector3(color);
		this.intencity = intencity;
		this.distance = distance;
		this.id = this.hashCode();
		this.genPublicColors();
	}
	
	protected void genPublicColors() {
		this.r = this.color.x;
		this.g = this.color.y;
		this.b = this.color.z;
	}

	
	public Vector2 getPosition() {
		return position;
	}

	public AbsLight setPosition(Vector2 position) {
		this.position = position;
		return this;
	}

	public Vector3 getColor() {
		this.genPublicColors();
		return color;
	}

	public AbsLight setColor(Vector3 color) {
		this.color = color;
		this.genPublicColors();
		return this;
	}

	public float getIntencity() {
		return intencity;
	}

	public AbsLight setIntencity(float intencity) {
		this.intencity = intencity;
		return this;
	}

	public float getDistance() {
		return distance;
	}

	public AbsLight setDistance(float distance) {
		this.distance = distance;
		return this;
	}

	public int getId() {
		return id;
	}
	
}
