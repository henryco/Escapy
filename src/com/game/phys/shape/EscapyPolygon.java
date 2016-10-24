package com.game.phys.shape;

import com.badlogic.gdx.math.Polygon;

/**
 * @author Henry on 23/10/16.
 */
public class EscapyPolygon extends Polygon {

	private float[] xVert, yVert;
	private float[][] lines;
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
		lines = new float[vertNumb][4];
		for (int i = 0; i < vertNumb; i++){
			xVert[i] = vertices[2*i];
			yVert[i] = vertices[2*i + 1];
		}
		for (int i = 0; i < vertNumb - 1; i++) {
			lines[i][0] = xVert[i];
			lines[i][1] = yVert[i];
			lines[i][2] = xVert[i+1];
			lines[i][3] = yVert[i+1];
		}
		lines[vertNumb - 1][0] = xVert[vertNumb - 1];
		lines[vertNumb - 1][1] = yVert[vertNumb - 1];
		lines[vertNumb - 1][2] = xVert[0];
		lines[vertNumb - 1][3] = yVert[0];
	}

	public float[] getCollisionVector(float[] normvec, EscapyPolygon otherPolygon) {


		for (int i = 0; i < otherPolygon.vertNumb; i++)
			if (contains(otherPolygon.xVert[i], otherPolygon.yVert[i])) {
				for (float[] line : lines) {

				}
			}
		for (int i = 0; i < vertNumb; i++)
			if (otherPolygon.contains(xVert[i], yVert[i])) {

			}


		return null;
	}

}
