package com.x.game.physics_temp;

public abstract class EscapyPhysicsObjectSuper extends EscapyPhysicsSuper implements EscapyPhysics {

	public float width, height, mass, xpos, ypos, Vx, Vy, tetha, ax, ay, Fx, Fy, px, py;
	private EscapyPhysicsEvent obj;
	public EscapyPhysicsObjectSuper thisob;
	protected EscapyPhysicsConstants variables;

	public EscapyPhysicsObjectSuper(float w, float h, float m, float xp, float yp) {
		this.width = w;
		this.height = h;
		this.mass = m;
		this.xpos = xp;
		this.ypos = yp;
		this.variables = new EscapyPhysicsConstants();
	}

	public EscapyPhysicsObjectSuper() {
	}

	public void setCalculation(boolean calculation) {
		if (calculation)
			addToCalcList(obj, thisob);

		else if (!calculation)
			removeFromCalcList(obj, thisob);
	}

	protected void setObject(EscapyPhysicsEvent obj, EscapyPhysicsObjectSuper thisob) {
		this.obj = obj;
		this.thisob = thisob;
	}

	public EscapyPhysicsConstants var() {
		return variables;
	}

	public float width() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float height() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float mass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float xpos() {
		return xpos;
	}

	public void setXpos(float xpos) {
		this.xpos = xpos;
	}

	public float ypos() {
		return ypos;
	}

	public void setYpos(float ypos) {
		this.ypos = ypos;
	}

	public float getVx() {
		return Vx;
	}

	public void setVx(float vx) {
		this.Vx = vx;
	}

	public float getVy() {
		return Vy;
	}

	public void setVy(float vy) {
		this.Vy = vy;
	}

	public float bodyXpos() {
		return (xpos + (width * 0.52f));
	}

	public float bodyYpos() {
		return (ypos + (height * 0.48f));
	}

	public EscapyPhysicsObjectSuper setPosition(float[] position) {
		this.xpos = position[0];
		this.ypos = position[1];

		return this;
	}
}
