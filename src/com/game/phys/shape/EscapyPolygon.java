package com.game.phys.shape;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.RandomXS128;
import com.game.utils.primitives.EscapyGeometry;
import com.game.utils.primitives.lines.EscapyLine;

/**
 * @author Henry on 23/10/16.
 */
public class EscapyPolygon extends Polygon {

	private static final float maxFloat = 99999999999999999999999999999999999999f;

	private float[] xVert, yVert;
	public float[] counter;
	private EscapyLine[] lines;
	private int vertNumb = 0;
	private int normNumb = 0;
	public String name = "";

	public EscapyPolygon(float ... vertices) {
		setVertices(vertices);
	}

	@Override
	public void setVertices(float[] vertices) {
		super.setVertices(vertices);
		counter = new float[2];
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

	public EscapyPolygon setName(String name) {
		this.name = name;
		return this;
	}

	public String outCounter(){
		return Float.toString(counter[0])+" : "+Float.toString(counter[1]);
	}
	public String outVert(){
		String n = "";
		for (int i = 0; i < vertNumb; i++) {
			n += "["+Float.toString(xVert[i])+","+Float.toString(yVert[i])+"] ";
		}
		return n;
	}


	public float[] collisionVector(EscapyPolygon otherPolygon, float ... moveVec) {

		float maxSqrdLengtgh = 0;
		float[] retNormal = new float[2];
		float[] direct = EscapyGeometry.normalize(moveVec);

		for (EscapyLine line : lines) {
			for (int i = 0; i < otherPolygon.vertNumb; i++) {
				float xVert = otherPolygon.xVert[i];
				float yVert = otherPolygon.yVert[i];
				float[] interPoint = line.intersectedPoint(xVert, yVert, xVert - (direct[0] * 900), yVert - (direct[1] * 900));
				if (interPoint != null) {
					float minX = Math.min(xVert - moveVec[0], xVert);
					float maxX = Math.max(xVert - moveVec[0], xVert);
					float minY = Math.min(yVert - moveVec[1], yVert);
					float maxY = Math.max(yVert - moveVec[1], yVert);
					float l = EscapyGeometry.squaredLength(xVert, yVert, interPoint[0], interPoint[1]);
					if (interPoint[0] >= minX && interPoint[0] <= maxX && interPoint[1] >= minY && interPoint[1] <= maxY) {
						if (l >= maxSqrdLengtgh) {
							maxSqrdLengtgh = l;
							retNormal = line.normal;
						}
					}
				}
			}
		}
		if (maxSqrdLengtgh > 0){
			float length = (float) Math.sqrt(maxSqrdLengtgh);
			return new float[]{-direct[0]*(length * 1.01f), -direct[1]*(length * 1.01f), retNormal[0], retNormal[1]};
		}
		EscapyLine tmpLine = null;
		float ortoDist = maxFloat;
		float[][] verts = new float[otherPolygon.vertNumb][];

		for (int i = 0; i < otherPolygon.vertNumb; i++) {
			float xVerts = otherPolygon.xVert[i];
			float yVerts = otherPolygon.yVert[i];
			if (contains(xVerts, yVerts)) {
				verts[i] = new float[2];
				verts[i][0] = xVerts;
				verts[i][1] = yVerts;
			}
		}
		for (EscapyLine line : lines) {
			float dist = 0;
			for (float[] points : verts)
				if (points != null) {
					float l = EscapyGeometry.pointToLineDist(line.getCoeffABC(), points);
					if (l > dist) dist = l;
				}
			if (dist <= ortoDist) {
				ortoDist = dist;
				tmpLine = line;
			}
		}
		if (tmpLine == null) return null;
		float longDist = ortoDist*1.01f;
		return new float[]{tmpLine.normal[0] * longDist, -tmpLine.normal[1] * longDist, -tmpLine.normal[0], -tmpLine.normal[1]};
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

			float[] min_max_1 = new float[]{maxFloat, 0};
			float[] min_max_2 = new float[]{maxFloat, 0};

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
