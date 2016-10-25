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

	public float[] speed_vec, speed_min;
	public float mass, bounding;

	public PhysPolygon(EscapyPolygon polygon, boolean frozen, String ... name) {
		this.polygon = polygon;
		this.frozen = frozen;
		this.speed_vec = new float[2];
		this.speed_min = new float[]{0.01f, 0.01f};
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

		if (frozen) {
			float absXY = Math.abs((x+y)*m);
			if (MathUtils.isEqual(absXY , bounding) || absXY > bounding) frozen = false;
			else {
				speed_vec[0] = 0;
				speed_vec[1] = 0;
			}
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
		if (Math.abs(x) >= speed_min[0]) speed_vec[0] = x;
		else speed_vec[0] = 0;
		return this;
	}
	public PhysPolygon setSpeedY(float y) {
		if(Math.abs(y) >= speed_min[1]) speed_vec[1] = y;
		else speed_vec[1] = 0;
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
	public PhysPolygon setMinSpeed(float ... xy) {
		this.speed_min[0] = xy[0];
		this.speed_min[1] = xy[1];
		return this;
	}

	public void outSpeed() {
		System.out.println(name+": "+speed_vec[0] + " <:> " + speed_vec[1]);
	}
}
