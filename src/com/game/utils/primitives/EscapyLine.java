package com.game.utils.primitives;

import com.badlogic.gdx.math.Vector2;

/*
 	* TODO INTERSECTION, INCLUDES and other collision methods
 	* @author HenryCo
 */

public class EscapyLine {

	private Vector2 start, end;

	public EscapyLine(Vector2 start, Vector2 end) {
		this.setStart(start);
		this.setEnd(end);
		return;
	}
	
	public EscapyLine(float x1, float y1, float x2, float y2) {
		this.setStart(new Vector2(x1, y1));
		this.setEnd(new Vector2(x2, y2));
		return;
	}

	public EscapyLine(float[] start, float[] end) {
		this.setStart(new Vector2(start[0], start[1]));
		this.setEnd(new Vector2(end[0], end[1]));
		return;
	}

	public Vector2 getStart() {
		return start;
	}

	public void setStart(Vector2 start) {
		this.start = start;
	}

	public Vector2 getEnd() {
		return end;
	}

	public void setEnd(Vector2 end) {
		this.end = end;
	}

}
