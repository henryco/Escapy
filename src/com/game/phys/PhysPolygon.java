package com.game.phys;

import com.badlogic.gdx.math.MathUtils;
import com.game.phys.shape.EscapyPolygon;

import java.util.function.Consumer;

/**
 * @author Henry on 24/10/16.
 */
public class PhysPolygon {

	public String name = "";
	public EscapyPolygon polygon;
	public boolean frozen = false;

	public float[] speed_vec;
	public float mass, bounding;
	public float energyLoss;
	public float liveTime;
	public long liveHits;
	public float minSpeed_x, minSpeed_y;
	public float collisionTime;
	private float actualCollisionTime;

	public Consumer<PhysPolygon> timeOutAction = physPolygon -> {};
	public Consumer<PhysPolygon> hitsOutAction = physPolygon -> liveTime = -1;
	public Consumer<PhysPolygon> collOutAction = hitsOutAction;

	public PhysPolygon(EscapyPolygon polygon, boolean frozen, String ... name) {
		this.polygon = polygon;
		this.frozen = frozen;
		this.speed_vec = new float[2];
		this.mass = 200f;
		this.bounding = frozen ? 50000000 : 0;
		this.energyLoss = 0.7f;
		this.liveTime = 999999999999999999f;
		this.liveHits = 999999999999999999L;
		this.collisionTime = 999999999999999999f;
		this.actualCollisionTime = 0;
		this.minSpeed_x = 0.99f;
		this.minSpeed_y = 1.25f;
		setName(name);
	}
	public PhysPolygon(EscapyPolygon polygon, String ... name) {
		this(polygon, false, name);
	}

	public void translate(float x, float y) {
		if (!frozen) polygon.translate(x, y);
	}
	public void translate(float x, float y, float m) {
		checkBounds(x, y, m);
		translate(x, y);
	}
	public boolean checkLiveTime(float delta) {
		return (liveTime -= delta) > 0;
	}
	public void updHits(){
		if ((liveHits -= 1) <= 0) hitsOutAction.accept(this);
	}
	public void checkBounds(float x, float y, float m) {

		if (frozen) {
			float absXY = Math.abs((x+y)*m);
			if (MathUtils.isEqual(absXY , bounding) || absXY > bounding) frozen = false;
			else {
				speed_vec[0] = 0;
				speed_vec[1] = 0;
			}
		}
	}
	public void updCollisionTime(float d){
		if ((actualCollisionTime += d) >= collisionTime) collOutAction.accept(this);
	}
	public void restartCollisionTime() {
		this.actualCollisionTime = 0;
	}
	public void checkBounds() {
		checkBounds(speed_vec[0], speed_vec[1], mass);
	}

	public PhysPolygon setPosition(float x, float y) {
		polygon.setPosition(x, y);
		return this;
	}

	public PhysPolygon setName(String ... name) {
		if (name == null || name.length == 0) this.name = Integer.toString(hashCode());
		else for (String n : name) this.name += n;
		return this;
	}

	public PhysPolygon setSpeedX(float x) {
		speed_vec[0] = x;
		return this;
	}
	public PhysPolygon setSpeedY(float y) {
		speed_vec[1] = y;
		return this;
	}
	public PhysPolygon setSpeed(float x, float y) {
		speed_vec[0] = x;
		speed_vec[1] = y;
		return this;
	}
	public PhysPolygon setMass(float mass) {
		this.mass = mass;
		return this;
	}
	public PhysPolygon setBounding(float b) {
		this.bounding = b;
		return this;
	}
	public PhysPolygon setEnergyLoss(float e) {
		this.energyLoss = 1 - e;
		return this;
	}
	public PhysPolygon setLiveTime(float timeSec) {
		liveTime = timeSec;
		return this;
	}
	public PhysPolygon setLiveHits(long numb) {
		this.liveHits = numb;
		return this;
	}
	public PhysPolygon setMinSpeedX(float x){
		minSpeed_x = x;
		return this;
	}
	public PhysPolygon setMinSpeedY(float y){
		minSpeed_y = y;
		return this;
	}
	public PhysPolygon setMinSpeed(float x, float y){
		minSpeed_x = x;
		minSpeed_y = y;
		return this;
	}
	public PhysPolygon setTimeOutAction(Consumer<PhysPolygon> ta) {
		timeOutAction = ta;
		return this;
	}
	public PhysPolygon setHitsOutAction(Consumer<PhysPolygon> ht) {
		hitsOutAction = ht;
		return this;
	}
	public PhysPolygon setCollisionsOutAction(Consumer<PhysPolygon> ca) {
		collOutAction = ca;
		return this;
	}

	public void outSpeed() {
		System.out.println(name+": "+speed_vec[0] + " <:> " + speed_vec[1]);
	}
	@Override
	public String toString(){
		return name;
	}
}
