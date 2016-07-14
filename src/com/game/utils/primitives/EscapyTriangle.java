package com.game.utils.primitives;

import com.badlogic.gdx.math.Vector2;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyTriangle.
 */
public abstract class EscapyTriangle implements EscapyGeometry {

	private float r, x0, y0, beta, alpha;
	private long time0, time1;

	/** The Constant FILL. */
	protected static final int FILL = 0;
	
	/** The Constant UPDATE. */
	protected static final int UPDATE = 1;

	private float xPoint1, yPoint1, xPoint2, yPoint2;
	private float translationX = 0, translationY = 0;
	private EscapyLine endOrtagonal, line1, line2;
	private Vector2 Vector0;

	/**
	 * Instantiates a new escapy triangle.
	 *
	 * @param r
	 *            the r
	 * @param x0
	 *            the x 0
	 * @param y0
	 *            the y 0
	 * @param gamma
	 *            the gamma
	 * @param beta
	 *            the beta
	 */
	public EscapyTriangle(float r, float x0, float y0, float gamma, float beta) {
		this.r = r;
		this.x0 = x0;
		this.y0 = y0;
		this.beta = beta;
		this.alpha = calcAlpha(gamma, beta);
		calculatePoints();
	}

	/**
	 * Calc alpha.
	 *
	 * @param gamma
	 *            the gamma
	 * @param beta
	 *            the beta
	 * @return the float
	 */
	protected float calcAlpha(float gamma, float beta) {
		return addAngle(gamma, beta);
	}

	/**
	 * Calculate points.
	 */
	protected void calculatePoints() {
		float alpha_radian = (float) Math.toRadians(alpha);
		float beta_radian = (float) Math.toRadians(beta);

		this.xPoint1 = calculateXPos(alpha_radian, this.r);
		this.xPoint2 = calculateXPos(beta_radian, this.r);

		this.yPoint1 = calculateYPos(alpha_radian, this.r);
		this.yPoint2 = calculateYPos(beta_radian, this.r);

		this.setLine1(new EscapyLine(getXPoint0(), getYPoint0(), getXPoint1(), getYPoint1()));
		this.setLine2(new EscapyLine(getXPoint0(), getYPoint0(), getXPoint2(), getYPoint2()));

		this.setEndOrtagonal(new EscapyLine(getXPoint1(), getYPoint1(), getXPoint2(), getYPoint2()));

		this.setVector0(new Vector2(getPoint0()[0], getPoint0()[1]));

	}

	/**
	 * Rotation.
	 *
	 * @param angle
	 *            the angle
	 * @param timePause_ms
	 *            the time pause ms
	 */
	public void rotation(float angle, float timePause_ms) {
		time1 = System.nanoTime() - time0;

		float timeGoBy = (float) time1 / 1000000.f;
		if (timeGoBy >= timePause_ms) {
			this.beta = addAngle(this.beta, angle);
			this.alpha = addAngle(this.alpha, angle);
			calculatePoints();
			time0 = System.nanoTime();
		}
	}

	/**
	 * Translate.
	 *
	 * @param translationX
	 *            the translation X
	 * @param translationY
	 *            the translation Y
	 */
	public void translate(float translationX, float translationY) {
		this.setTranslationX(translationX);
		this.setTranslationY(translationY);

		calculatePoints();
	}

	/**
	 * Adds the angle.
	 *
	 * @param angle
	 *            the angle
	 * @param angleToAdd
	 *            the angle to add
	 * @return the float
	 */
	protected float addAngle(float angle, float angleToAdd) {
		if ((angle + angleToAdd) >= 360)
			return (angle + angleToAdd) - 360;

		return angle + angleToAdd;
	}

	/**
	 * Calculate X pos.
	 *
	 * @param angle_rad
	 *            the angle rad
	 * @param radius
	 *            the radius
	 * @return the float
	 */
	protected float calculateXPos(float angle_rad, float radius) {
		return ((float) Math.cos(angle_rad)) * radius;
	}

	/**
	 * Calculate Y pos.
	 *
	 * @param angle_rad
	 *            the angle rad
	 * @param radius
	 *            the radius
	 * @return the float
	 */
	protected float calculateYPos(float angle_rad, float radius) {
		return ((float) Math.sin(angle_rad)) * radius;
	}

	/**
	 * Gets the point matrix.
	 *
	 * @return the point matrix
	 */
	public float[] getPointMatrix() {
		return new float[] { getXPoint0(), getYPoint0(), getXPoint1(), getYPoint1(), getXPoint2(), getYPoint2() };
	}

	/**
	 * Gets the point 0.
	 *
	 * @return the point 0
	 */
	public float[] getPoint0() {
		return new float[] { getXPoint0(), getYPoint0() };
	}

	/**
	 * Gets the point 1.
	 *
	 * @return the point 1
	 */
	public float[] getPoint1() {
		return new float[] { getXPoint1(), getYPoint1() };
	}

	/**
	 * Gets the point 2.
	 *
	 * @return the point 2
	 */
	public float[] getPoint2() {
		return new float[] { getXPoint2(), getYPoint2() };
	}

	/**
	 * Gets the x point 0.
	 *
	 * @return the x point 0
	 */
	public float getXPoint0() {
		return x0 - getTranslationX();
	}

	/**
	 * Gets the y point 0.
	 *
	 * @return the y point 0
	 */
	public float getYPoint0() {
		return y0 - getTranslationY();
	}

	/**
	 * Gets the x point 1.
	 *
	 * @return the x point 1
	 */
	public float getXPoint1() {
		return xPoint1 + getXPoint0();
	}

	/**
	 * Gets the y point 1.
	 *
	 * @return the y point 1
	 */
	public float getYPoint1() {
		return yPoint1 + getYPoint0();
	}

	/**
	 * Gets the x point 2.
	 *
	 * @return the x point 2
	 */
	public float getXPoint2() {
		return xPoint2 + getXPoint0();
	}

	/**
	 * Gets the y point 2.
	 *
	 * @return the y point 2
	 */
	public float getYPoint2() {
		return yPoint2 + getYPoint0();
	}

	/**
	 * Sets the xpos.
	 *
	 * @param x
	 *            the new xpos
	 */
	public void setXpos(int x) {
		this.x0 = x;
	}

	/**
	 * Sets the ypos.
	 *
	 * @param y
	 *            the new ypos
	 */
	public void setYpos(int y) {
		this.y0 = y;
	}

	/**
	 * Gets the translation X.
	 *
	 * @return the translation X
	 */
	public float getTranslationX() {
		return translationX;
	}

	/**
	 * Sets the translation X.
	 *
	 * @param translationX
	 *            the new translation X
	 */
	public void setTranslationX(float translationX) {
		this.translationX = translationX;
	}

	/**
	 * Gets the translation Y.
	 *
	 * @return the translation Y
	 */
	public float getTranslationY() {
		return translationY;
	}

	/**
	 * Sets the translation Y.
	 *
	 * @param translationY
	 *            the new translation Y
	 */
	public void setTranslationY(float translationY) {
		this.translationY = translationY;
	}

	/**
	 * Gets the end ortagonal.
	 *
	 * @return the end ortagonal
	 */
	public EscapyLine getEndOrtagonal() {
		return endOrtagonal;
	}

	/**
	 * Sets the end ortagonal.
	 *
	 * @param endOrtagonal
	 *            the new end ortagonal
	 */
	public void setEndOrtagonal(EscapyLine endOrtagonal) {
		this.endOrtagonal = endOrtagonal;
	}

	/**
	 * Gets the line 1.
	 *
	 * @return the line 1
	 */
	public EscapyLine getLine1() {
		return line1;
	}

	/**
	 * Sets the line 1.
	 *
	 * @param line1
	 *            the new line 1
	 */
	public void setLine1(EscapyLine line1) {
		this.line1 = line1;
	}

	/**
	 * Gets the line 2.
	 *
	 * @return the line 2
	 */
	public EscapyLine getLine2() {
		return line2;
	}

	/**
	 * Sets the line 2.
	 *
	 * @param line2
	 *            the new line 2
	 */
	public void setLine2(EscapyLine line2) {
		this.line2 = line2;
	}

	/**
	 * Gets the vector 0.
	 *
	 * @return the vector 0
	 */
	public Vector2 getVector0() {
		return Vector0;
	}

	/**
	 * Sets the vector 0.
	 *
	 * @param vector0
	 *            the new vector 0
	 */
	public void setVector0(Vector2 vector0) {
		Vector0 = vector0;
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius
	 *            the new radius
	 */
	public void setRadius(float radius) {
		this.r = radius;
	}

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public float getRadius() {
		return r;
	}

}
