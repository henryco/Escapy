package com.game.phys.shape;

import com.badlogic.gdx.math.Polygon;
import com.game.utils.primitives.EscapyGeometry;
import com.game.utils.primitives.lines.EscapyLine;

/**
 * @author Henry on 23/10/16.
 */
public class EscapyPolygon extends Polygon {

	private float[] xVert, yVert;
	private EscapyLine[] lines;
	private int vertNumb = 0;
	private int normNumb = 0;

	public EscapyPolygon(float[] vertices) {
		setVertices(vertices);
	}

	@Override
	public void setVertices(float[] vertices) {
		super.setVertices(vertices);
		vertNumb = vertices.length / 2;
		normNumb = vertNumb;
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

	@Override
	public void setPosition(float x, float y) {
		float tx = getX();
		float ty = getY();
		super.setPosition(x, y);
		for (int i = 0; i < vertNumb; i++) {
			lines[i].translate(x - tx, y - ty);
			xVert[i] += x - tx;
			yVert[i] += y - ty;
		}
	}

	private float squaredLength(float x1, float y1, float x2, float y2) {
		return (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2);
	}

	public boolean isCollide(EscapyPolygon otherPolygon){

		float[] vertXY = new float[2 * (otherPolygon.vertNumb + this.vertNumb)];
		float[] normals = new float[2 * (otherPolygon.normNumb + this.normNumb)];
		int iter = 0;
		for (int i = 0; i < otherPolygon.vertNumb; i++) {
			vertXY[iter] = otherPolygon.xVert[i];
			vertXY[iter+1] = otherPolygon.yVert[i];
			normals[iter] = otherPolygon.lines[i].normal[0];
			normals[iter+1] = otherPolygon.lines[i].normal[1];
			iter += 2;
		}
		for (int i = 0; i < vertNumb; i++) {
			vertXY[iter] = xVert[i];
			vertXY[iter+1] = yVert[i];
			normals[iter] = lines[i].normal[0];
			normals[iter+1] = lines[i].normal[1];
			iter += 2;
		}

		for (int i = 0; i < normals.length - 1; i+=2) {

			float[] min_max_1 = new float[]{100000000, 0};
			float[] min_max_2 = new float[]{100000000, 0};

			iter = 0;
			for (int z = 0; z < otherPolygon.vertNumb; z++) {
				float proj_1 = EscapyGeometry.dot2(vertXY[iter], vertXY[iter+1], normals[i], normals[i + 1]);
				if (proj_1 < min_max_1[0]) min_max_1[0] = proj_1;
				if (proj_1 > min_max_1[1]) min_max_1[1] = proj_1;
				iter+=2;
			}
			for (int z = 0; z < vertNumb; z++) {
				float proj_2 = EscapyGeometry.dot2(vertXY[iter], vertXY[iter+1], normals[i], normals[i + 1]);
				if (proj_2 < min_max_2[0]) min_max_2[0] = proj_2;
				if (proj_2 > min_max_2[1]) min_max_2[1] = proj_2;
				iter+=2;
			}
			if (min_max_2[1] < min_max_1[0] || min_max_1[1] < min_max_2[0]) return false;
		}
		return true;
	}
}
