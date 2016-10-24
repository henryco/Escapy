package com.game.phys.shape;

import com.badlogic.gdx.math.Polygon;
import com.game.utils.primitives.lines.EscapyLine;

/**
 * @author Henry on 23/10/16.
 */
public class EscapyPolygon extends Polygon {

	private float[] xVert, yVert;
	private EscapyLine[] lines;
	private int vertNumb = 0;

	public EscapyPolygon(float[] vertices) {
		setVertices(vertices);
	}

	@Override
	public void setVertices(float[] vertices) {
		super.setVertices(vertices);
		vertNumb = vertices.length / 2;
		xVert = new float[vertNumb];
		yVert = new float[vertNumb];
		lines = new EscapyLine[vertNumb];
		for (int i = 0; i < vertNumb; i++){
			xVert[i] = vertices[2*i];
			yVert[i] = vertices[2*i + 1];
		}
		for (int i = 0; i < vertNumb - 1; i++)
			lines[i] = new EscapyLine(xVert[i], yVert[i], xVert[i+1], yVert[i+1]);
		lines[vertNumb - 1] = new EscapyLine(xVert[vertNumb - 1], yVert[vertNumb - 1], xVert[0], yVert[0]);
	}

	@Override
	public void translate(float x, float y) {
		super.translate(x, y);
		for (int i = 0; i < vertNumb; i++) {
			lines[i].translate(x, y);
			xVert[i] += x;
			yVert[i] += y;
		}
	}

	private EscapyLine checkLine = new EscapyLine(0, 0);
	private float lastLength;
	private float[] lastIntersected;
	private EscapyLine retLine;
	public float[] getCollisionVector(float[] normvec, float length, EscapyPolygon otherPolygon) {

		lastLength = 0;
		lastIntersected = new float[]{normvec[0] * length, normvec[1] * length};
		retLine = null;

		for (int i = 0; i < otherPolygon.vertNumb; i++)
			if (contains(otherPolygon.xVert[i], otherPolygon.yVert[i])) {
				checkLine.set(otherPolygon.xVert[i], otherPolygon.yVert[i], normvec[0] * (-length), normvec[1] * (-length));
				calc(lines, checkLine, 1);
			}
		for (int i = 0; i < vertNumb; i++)
			if (otherPolygon.contains(xVert[i], yVert[i])) {
				checkLine.set(xVert[i], yVert[i], normvec[0] * length, normvec[1] * length);
				calc(otherPolygon.lines, checkLine, -1);
			}
		if (retLine == null) return null;
		float[] norm = retLine.normal();
		return new float[]{lastIntersected[0], lastIntersected[1], norm[0], norm[1]};
	}

	private void calc(EscapyLine[] lines, EscapyLine checkLine, int sign){

		for (EscapyLine line : lines) {
			float[] intersected = line.intersectedPoint(checkLine);
			if (intersected != null) {
				float length = squaredLength(checkLine.start.x, checkLine.start.y, intersected[0], intersected[1]);
				if (length > lastLength) {
					lastLength = length;
					lastIntersected[0] = sign * (checkLine.start.x - intersected[0]);
					lastIntersected[1] = sign * (checkLine.start.y - intersected[1]);
					retLine = line;
				}
			}
		}
	}

	private float squaredLength(float x1, float y1, float x2, float y2) {
		return (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2);
	}
}
