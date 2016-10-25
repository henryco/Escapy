package com.game.utils.primitives.lines;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Henry on 24/10/16.
 */
public class EscapyLine {

	public Vector2 start, end;

	public EscapyLine() {
		createPoints();}
	public EscapyLine(float x1, float y1, float x2, float y2) {
		this();
		set(x1, y1, x2, y2);
	}
	public EscapyLine(float x1, float y1) {
		this(0, 0, x1, y1);
	}
	public EscapyLine(EscapyLine line) {
		this();
		set(line);
	}

	public float[] intersectedPoint(EscapyLine other) {

		float dx1 = end.x - start.x;
		float dx2 = other.end.x - other.start.x;
		float dy1 = end.y - start.y;
		float dy2 = other.end.y - other.start.y;
		float denom = (dy2 * dx1) - (dx2 * dy1);

		if (denom == 0) return null;

		float u = ((dx2 * (start.y - other.start.y)) - (dy2 * (start.x - other.start.x))) / denom;
		return new float[]{start.x + (u * (end.x - start.x)), start.y + (u * (end.y - start.y))};
	}

	public void translate(float x, float y) {
		start.add(x,y);
		end.add(x,y);
	}
	public EscapyLine set(float x1, float y1) {
		return set(0, 0, x1, y1);
	}
	public EscapyLine set(EscapyLine line) {
		return set(line.start.x, line.start.y, line.end.x, line.end.y);
	}
	public EscapyLine set(float x1, float y1, float x2, float y2) {
		createPoints();
		start.set(x1, y1);
		end.set(x2, y2);
		return this;
	}

	public float length(){
		return (float) Math.sqrt(lengthSqr());
	}
	public float lengthSqr() {
		return ((end.x - start.x) * (end.x - start.x)) + ((end.y - start.y) * (end.y - start.y));
	}
	public float[] normal(){
		return new float[]{start.y - end.y, end.x - start.x};
	}
	public float[] normedNormal(){
		float[] n = normal();
		float l = length();
		n[0] /= l;
		n[1] /= l;
		return n;
	}

	private void createPoints() {
		if (start == null) start = new Vector2();
		if (end == null) end = new Vector2();
	}
	public EscapyLine copy(){
		return new EscapyLine(this);
	}

	@Override
	public String toString() {
		return "["+start.x+", "+ start.y+"]" + " : " + "["+end.x+", "+ end.y+"]";
	}
}
