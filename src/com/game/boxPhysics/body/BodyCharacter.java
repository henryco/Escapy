package com.game.boxPhysics.body;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * @author Henry on 11/12/16.
 */
public class BodyCharacter implements IBoxBody {


	protected Velocity velocity = new Velocity(1.825f, 3f, 0.4f);
	protected SensorFixture sensors = new SensorFixture();
	protected BodyFixture bodyParts = new BodyFixture();
	protected Body body;

	private BodyHolder holder;
	private Vector2 lastPos;

	private float capSpeed = 0;
	private float moveSign = 0;
	private float meterScale = 1;
	private boolean movin = false;
	private boolean grounded = false;

	public BodyCharacter(BodyHolder holder, float meterScale) {
		this.holder = holder;
		this.meterScale = meterScale;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void setBody(Body body) {
		this.body = body;
	}

	@Override
	public void updateHolder() {
		holder.updateHolder(this);
	}

	@Override
	public void updateBody(Object ... var) {

		float delta = (float) var[0];
		World world = (World) var[1];

		if (movin && Math.abs(body.getLinearVelocity().x) <= MathUtils.FLOAT_ROUNDING_ERROR)
			capSpeed = (Math.abs(capSpeed) > velocity.max) ? 0 : capSpeed + 0.1f;
		else capSpeed = 0;
		if (!movin && grounded && Math.abs(body.getLinearVelocity().x) >= MathUtils.FLOAT_ROUNDING_ERROR)
			body.setTransform(lastPos, 0);
		movin = false;
		grounded = false;

		if (isBodyGrounded(delta, world, body, sensors.sensorLegs)) {
			grounded = true;
			if (moveSign != 0) {
				movin = true;
				body.setLinearVelocity(velocity.move * moveSign, body.getLinearVelocity().y - capSpeed);
			} else {
				capSpeed = 0;
				body.setLinearVelocity(0, body.getLinearVelocity().y - capSpeed);
			}
		}
		body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
		body.setAwake(true);
		lastPos = body.getPosition();
		moveSign = 0;
	}

	@Override
	public void setMoveSign(float moveSign) {
		this.moveSign = moveSign;
	}

	@Override
	public float getMeterScale() {
		return meterScale;
	}


	synchronized private static boolean isBodyGrounded(float deltaTime, World world, Body player, Fixture playerSensorFixture) {
		Array<Contact> contactList = world.getContactList();
		for (int i = 0; i < contactList.size; i++) {
			Contact contact = contactList.get(i);
			if(contact != null && contact.isTouching()
					&& (contact.getFixtureA() == playerSensorFixture || contact.getFixtureB() == playerSensorFixture)) {
				Vector2 pos = player.getPosition();
				WorldManifold manifold = contact.getWorldManifold();
				boolean below = true;
				for(int j = 0; j < manifold.getNumberOfContactPoints(); j++)
					below &= (manifold.getPoints()[j].y < pos.y - 1.5f);
				return below;
			}
		}
		return false;
	}
}
