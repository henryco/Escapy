package com.game.phys;

import com.game.phys.shape.EscapyPolygon;

/**
 * @author Henry on 24/10/16.
 */
public class PhysPolygon {

	public EscapyPolygon polygon;
	public boolean frozen = false;
	public float[] speed_vec;
	public String name = "";

	public PhysPolygon(EscapyPolygon polygon, boolean frozen, String ... name) {
		this.polygon = polygon;
		this.frozen = frozen;
		this.speed_vec = new float[2];
		setName(name);
	}
	public PhysPolygon(EscapyPolygon polygon, String ... name) {
		this(polygon, false, name);
	}

	public void translate(float x, float y) {
		polygon.translate(x, y);
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
}
