package com.game.phys;

import com.badlogic.gdx.math.MathUtils;
import com.game.phys.shape.EscapyPolygon;

/**
 * @author Henry on 24/10/16.
 */
public class PhysPolygon {

	public String name = "";
	public EscapyPolygon polygon;
	public boolean frozen = false;

	public float[] speed_vec;
	public float mass;
	public float bounding;

	public PhysPolygon(EscapyPolygon polygon, boolean frozen, String ... name) {
		this.polygon = polygon;
		this.frozen = frozen;
		this.speed_vec = new float[2];
		this.mass = 0.1f;
		this.bounding = frozen ? 500 : 0;

		setName(name);
	}
	public PhysPolygon(EscapyPolygon polygon, String ... name) {
		this(polygon, false, name);
	}

	public void translate(float x, float y) {
		if (!frozen) polygon.translate(x, y);
	}
	public void translate(float x, float y, float m) {
		checkBounds(x, y, m);
		translate(x, y);
	}

	public void checkBounds(float x, float y, float m) {
		if (MathUtils.isEqual((x+y)*m , bounding) || (x+y)*m > bounding) frozen = false;
		else {
			speed_vec[0] = 0;
			speed_vec[1] = 0;
		}
	}

	public void setPosition(float x, float y) {
		polygon.setPosition(x, y);
	}

	public PhysPolygon setName(String ... name) {
		if (name == null || name.length == 0) this.name = Integer.toString(hashCode());
		else for (String n : name) this.name += n;
		return this;
	}

	public PhysPolygon setSpeedX(float x) {
		speed_vec[0] = x;
		return this;
	}
	public PhysPolygon setSpeedY(float y) {
		speed_vec[1] = y;
		return this;
	}
	public PhysPolygon setMass(float mass) {
		this.mass = mass;
		return this;
	}
	public PhysPolygon setBounding(float b) {
		this.bounding = b;
		return this;
	}

	public void outSpeed() {
		System.out.println(name+": "+speed_vec[0] + " <:> " + speed_vec[1]);
	}
}
