package com.game.utils.primitives.lines;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Henry on 24/10/16.
 */
public class EscapyLine {

	public Vector2 start, end;
	public float[] normal;

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
	public EscapyLine(float[] p) {
		this();
		if (p.length == 2) set(0, 0, p[0], p[1]);
		else if (p.length == 4) set(p[0], p[1], p[2], p[3]);
	}

	public float[] intersectedPoint(float sx, float sy, float ex, float ey) {

		float dx1 = end.x - start.x;
		float dx2 = ex - sx;
		float dy1 = end.y - start.y;
		float dy2 = ey - sy;
		float denom = (dy2 * dx1) - (dx2 * dy1);

		if (denom == 0) return null;

		float u = ((dx2 * (start.y - sy)) - (dy2 * (start.x - sx))) / denom;
		return new float[]{start.x + (u * (end.x - start.x)), start.y + (u * (end.y - start.y))};
	}
	public float[] intersectedPoint(float[] other) {
		return intersectedPoint(other[0], other[1], other[2], other[3]);
	}
	public float[] intersectedPoint(EscapyLine other) {
		return intersectedPoint(other.start.x, other.start.y, other.end.x, other.end.y);
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
		updateNormals();
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

	public void updateNormals() {
		normal = normedNormal();
	}

	@Override
	public String toString() {
		return "["+start.x+", "+ start.y+"]" + " : " + "["+end.x+", "+ end.y+"]";
	}
}
