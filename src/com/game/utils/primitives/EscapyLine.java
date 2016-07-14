package com.game.utils.primitives;

import com.badlogic.gdx.math.Vector2;

// TODO: Auto-generated Javadoc
/*
 	* TODO INTERSECTION, INCLUDES and other collision methods
 	* @author HenryCo
 */

/**
 * The Class EscapyLine.
 */
public class EscapyLine {

	private Vector2 start, end;

	/**
	 * Instantiates a new escapy line.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public EscapyLine(Vector2 start, Vector2 end) {
		this.setStart(start);
		this.setEnd(end);
		return;
	}
	
	/**
	 * Instantiates a new escapy line.
	 *
	 * @param x1
	 *            the x 1
	 * @param y1
	 *            the y 1
	 * @param x2
	 *            the x 2
	 * @param y2
	 *            the y 2
	 */
	public EscapyLine(float x1, float y1, float x2, float y2) {
		this.setStart(new Vector2(x1, y1));
		this.setEnd(new Vector2(x2, y2));
		return;
	}

	/**
	 * Instantiates a new escapy line.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public EscapyLine(float[] start, float[] end) {
		this.setStart(new Vector2(start[0], start[1]));
		this.setEnd(new Vector2(end[0], end[1]));
		return;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Vector2 getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start
	 *            the new start
	 */
	public void setStart(Vector2 start) {
		this.start = start;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public Vector2 getEnd() {
		return end;
	}

	/**
	 * Sets the end.
	 *
	 * @param end
	 *            the new end
	 */
	public void setEnd(Vector2 end) {
		this.end = end;
	}

}
