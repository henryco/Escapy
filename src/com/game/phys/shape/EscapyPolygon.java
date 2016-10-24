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

	public float[] getCollisionVector(float[] normvec, EscapyPolygon otherPolygon) {


		for (int i = 0; i < otherPolygon.vertNumb; i++)
			if (contains(otherPolygon.xVert[i], otherPolygon.yVert[i])) {
				for (EscapyLine line : lines) {

				}
			}
		for (int i = 0; i < vertNumb; i++)
			if (otherPolygon.contains(xVert[i], yVert[i])) {

			}


		return null;
	}

}
