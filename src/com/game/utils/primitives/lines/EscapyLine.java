package com.game.utils.primitives.lines;

import com.badlogic.gdx.math.Vector2;
import com.game.utils.primitives.EscapyGeometry;

/**
 * @author Henry on 24/10/16.
 */
public class EscapyLine {

	public Vector2 start, end;
	public float[] normal, coeff_Ax_By_C;

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
	public float denom(float sx, float sy, float ex, float ey){
		return (((ey - sy) * (end.x - start.x)) - ((ex - sx) * (end.y - start.y)));
	}
	public boolean intersects(float sx, float sy, float ex, float ey){
		return denom(sx, sy, ex, ey) != 0;
	}
	public boolean intersects(float[] other){
		return intersects(other[0], other[1], other[2], other[3]);
	}
	public boolean intersects(EscapyLine other){
		return intersects(other.start.x, other.start.y, other.end.x, other.end.y);
	}
	public float[] intersectedPoint(float sx, float sy, float ex, float ey) {

		float denom = denom(sx, sy, ex, ey);
		if (denom == 0) return null;
		float u = (((ex - sx) * (start.y - sy)) - ((ey - sy) * (start.x - sx))) / denom;
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
		coeff_Ax_By_C = EscapyGeometry.getLineAxByC(new float[]{x1, y1}, new float[]{x2, y2});
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
