package com.game.physics_temp;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyPhysicsObjectSuper.
 */
public abstract class EscapyPhysicsObjectSuper extends EscapyPhysicsSuper implements EscapyPhysics {

	/** The py. */
	public float width, height, mass, xpos, ypos, Vx, Vy, tetha, ax, ay, Fx, Fy, px, py;
	private EscapyPhysicsEvent obj;
	
	/** The thisob. */
	public EscapyPhysicsObjectSuper thisob;
	
	/** The variables. */
	protected EscapyPhysicsConstants variables;

	/**
	 * Instantiates a new escapy physics object super.
	 *
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 * @param m
	 *            the m
	 * @param xp
	 *            the xp
	 * @param yp
	 *            the yp
	 */
	public EscapyPhysicsObjectSuper(float w, float h, float m, float xp, float yp) {
		this.width = w;
		this.height = h;
		this.mass = m;
		this.xpos = xp;
		this.ypos = yp;
		this.variables = new EscapyPhysicsConstants();
	}

	/**
	 * Instantiates a new escapy physics object super.
	 */
	public EscapyPhysicsObjectSuper() {
	}

	/**
	 * Switch the calculation.
	 *
	 * @param calculation
	 *            the new calculation
	 */
	public void setCalculation(boolean calculation) {
		if (calculation)
			addToCalcList(obj, thisob);

		else if (!calculation)
			removeFromCalcList(obj, thisob);
	}

	/**
	 * Sets the object.
	 *
	 * @param obj
	 *            the obj
	 * @param thisob
	 *            the thisob
	 */
	protected void setObject(EscapyPhysicsEvent obj, EscapyPhysicsObjectSuper thisob) {
		this.obj = obj;
		this.thisob = thisob;
	}

	/**
	 * Var.
	 *
	 * @return the escapy physics constants
	 */
	public EscapyPhysicsConstants var() {
		return variables;
	}

	/**
	 * Width.
	 *
	 * @return the float
	 */
	public float width() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Height.
	 *
	 * @return the float
	 */
	public float height() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Mass.
	 *
	 * @return the float
	 */
	public float mass() {
		return mass;
	}

	/**
	 * Sets the mass.
	 *
	 * @param mass
	 *            the new mass
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	/**
	 * Xpos.
	 *
	 * @return the float
	 */
	public float xpos() {
		return xpos;
	}

	/**
	 * Sets the xpos.
	 *
	 * @param xpos
	 *            the new xpos
	 */
	public void setXpos(float xpos) {
		this.xpos = xpos;
	}

	/**
	 * Ypos.
	 *
	 * @return the float
	 */
	public float ypos() {
		return ypos;
	}

	/**
	 * Sets the ypos.
	 *
	 * @param ypos
	 *            the new ypos
	 */
	public void setYpos(float ypos) {
		this.ypos = ypos;
	}

	/**
	 * Gets the vx.
	 *
	 * @return the vx
	 */
	public float getVx() {
		return Vx;
	}

	/**
	 * Sets the vx.
	 *
	 * @param vx
	 *            the new vx
	 */
	public void setVx(float vx) {
		this.Vx = vx;
	}

	/**
	 * Gets the vy.
	 *
	 * @return the vy
	 */
	public float getVy() {
		return Vy;
	}

	/**
	 * Sets the vy.
	 *
	 * @param vy
	 *            the new vy
	 */
	public void setVy(float vy) {
		this.Vy = vy;
	}

	/**
	 * Body xpos.
	 *
	 * @return the float
	 */
	public float bodyXpos() {
		return (xpos + (width * 0.52f));
	}

	/**
	 * Body ypos.
	 *
	 * @return the float
	 */
	public float bodyYpos() {
		return (ypos + (height * 0.48f));
	}

	/**
	 * Sets the position.
	 *
	 * @param position
	 *            the position
	 * @return the escapy physics object super
	 */
	public EscapyPhysicsObjectSuper setPosition(float[] position) {
		this.xpos = position[0];
		this.ypos = position[1];

		return this;
	}
}
