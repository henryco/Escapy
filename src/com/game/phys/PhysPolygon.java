package com.game.phys;

import com.game.phys.shape.EscapyPolygon;

/**
 * @author Henry on 24/10/16.
 */
public class PhysPolygon {

	public EscapyPolygon polygon;
	public boolean frozen = false;

	public float[] speed_vec;

	public PhysPolygon(EscapyPolygon polygon, boolean frozen) {
		this.polygon = polygon;
		this.frozen = frozen;
		this.speed_vec = new float[2];
	}
	public PhysPolygon(EscapyPolygon polygon) {
		this(polygon, false);
	}

	public void translate(float x, float y) {
		polygon.translate(x, y);
	}

	public void setPosition(float x, float y) {
		polygon.setPosition(x, y);
	}

}
