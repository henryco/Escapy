package com.x.game.utils.primitives;

import com.badlogic.gdx.math.Vector2;

public abstract class EscapyTriangle implements EscapyGeometry {

	private float r, x0, y0, beta, alpha;
	private long time0, time1;

	protected static final int FILL = 0;
	protected static final int UPDATE = 1;

	private float xPoint1, yPoint1, xPoint2, yPoint2;
	private float translationX = 0, translationY = 0;
	private EscapyLine endOrtagonal, line1, line2;
	private Vector2 Vector0;

	public EscapyTriangle(float r, float x0, float y0, float gamma, float beta) {
		this.r = r;
		this.x0 = x0;
		this.y0 = y0;
		this.beta = beta;
		this.alpha = calcAlpha(gamma, beta);
		calculatePoints();
	}

	protected float calcAlpha(float gamma, float beta) {
		return addAngle(gamma, beta);
	}

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

	public void translate(float translationX, float translationY) {
		this.setTranslationX(translationX);
		this.setTranslationY(translationY);

		calculatePoints();
	}

	protected float addAngle(float angle, float angleToAdd) {
		if ((angle + angleToAdd) >= 360)
			return (angle + angleToAdd) - 360;

		return angle + angleToAdd;
	}

	protected float calculateXPos(float angle_rad, float radius) {
		return ((float) Math.cos(angle_rad)) * radius;
	}

	protected float calculateYPos(float angle_rad, float radius) {
		return ((float) Math.sin(angle_rad)) * radius;
	}

	public float[] getPointMatrix() {
		return new float[] { getXPoint0(), getYPoint0(), getXPoint1(), getYPoint1(), getXPoint2(), getYPoint2() };
	}

	public float[] getPoint0() {
		return new float[] { getXPoint0(), getYPoint0() };
	}

	public float[] getPoint1() {
		return new float[] { getXPoint1(), getYPoint1() };
	}

	public float[] getPoint2() {
		return new float[] { getXPoint2(), getYPoint2() };
	}

	public float getXPoint0() {
		return x0 - getTranslationX();
	}

	public float getYPoint0() {
		return y0 - getTranslationY();
	}

	public float getXPoint1() {
		return xPoint1 + getXPoint0();
	}

	public float getYPoint1() {
		return yPoint1 + getYPoint0();
	}

	public float getXPoint2() {
		return xPoint2 + getXPoint0();
	}

	public float getYPoint2() {
		return yPoint2 + getYPoint0();
	}

	public void setXpos(int x) {
		this.x0 = x;
	}

	public void setYpos(int y) {
		this.y0 = y;
	}

	public float getTranslationX() {
		return translationX;
	}

	public void setTranslationX(float translationX) {
		this.translationX = translationX;
	}

	public float getTranslationY() {
		return translationY;
	}

	public void setTranslationY(float translationY) {
		this.translationY = translationY;
	}

	public EscapyLine getEndOrtagonal() {
		return endOrtagonal;
	}

	public void setEndOrtagonal(EscapyLine endOrtagonal) {
		this.endOrtagonal = endOrtagonal;
	}

	public EscapyLine getLine1() {
		return line1;
	}

	public void setLine1(EscapyLine line1) {
		this.line1 = line1;
	}

	public EscapyLine getLine2() {
		return line2;
	}

	public void setLine2(EscapyLine line2) {
		this.line2 = line2;
	}

	public Vector2 getVector0() {
		return Vector0;
	}

	public void setVector0(Vector2 vector0) {
		Vector0 = vector0;
	}

	public void setRadius(float radius) {
		this.r = radius;
	}

	public float getRadius() {
		return r;
	}

}
