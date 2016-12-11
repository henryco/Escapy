package com.game.boxPhysics.body;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * @author Henry on 11/12/16.
 */
public interface IBoxBody {

	class Velocity {
		public float move;
		public float run;
		public float max;
		public Velocity(float move, float run, float max) {
			this.move = move;
			this.run = run;
			this.max = max;
		}
		public Velocity(){
			this(0f,0f,0f);
		}
	}

	class SensorFixture {
		public Fixture sensorHead;
		public Fixture sensorBody;
		public Fixture sensorLegs;

		public void apply() {
			sensorHead.setSensor(true);
			sensorBody.setSensor(true);
			sensorLegs.setSensor(true);
		}
	}

	class BodyFixture {
		public Fixture partHead;
		public Fixture partBody;
		public Fixture partLegs;

		public void setFriction(float friction) {
			partHead.setFriction(friction);
			partBody.setFriction(friction);
			partLegs.setFriction(friction);
		}
	}

	Body getBody();
	void setBody(Body body);
	void updateHolder();
	void updateBody(Object ... var);
	void setMoveSign(float moveSign);
	float getMeterScale();

	static IBoxBody createStdCharacter(final float meter_scale, World world, BodyHolder holder) {

		BodyCharacter character = new BodyCharacter(holder, meter_scale);

		BodyDef bodyDefChamp = holder.getBodyDef();
		bodyDefChamp.position.scl(meter_scale);
		character.setBody(world.createBody(bodyDefChamp));

		PolygonShape bodyShape = new PolygonShape();
		float[] chSet = new float[]{0,0, 50,0, 50,90, 0,90};
		for (int f = 0; f < chSet.length; f++) chSet[f] *= meter_scale;
		bodyShape.set(chSet);

		CircleShape legsShape = new CircleShape();
		legsShape.setRadius(25 * meter_scale);
		legsShape.setPosition(new Vector2(25 * meter_scale, 91 * meter_scale));

		CircleShape headShape = new CircleShape();
		headShape.setRadius(25 * meter_scale);
		headShape.setPosition(new Vector2(25 * meter_scale, 0));


		character.sensors.sensorHead = character.getBody().createFixture(headShape, 1f);
		character.sensors.sensorBody = character.getBody().createFixture(bodyShape, 1f);
		character.sensors.sensorLegs = character.getBody().createFixture(legsShape, 1f);
		character.bodyParts.partHead = character.getBody().createFixture(headShape, 1f);
		character.bodyParts.partBody = character.getBody().createFixture(bodyShape, 1f);
		character.bodyParts.partLegs = character.getBody().createFixture(legsShape, 1f);

		character.sensors.apply();
		character.bodyParts.setFriction(0.9f);
		character.getBody().setBullet(true);


		headShape.dispose();
		bodyShape.dispose();
		legsShape.dispose();

		holder.updateHolder(character);
		return character;
	}

}


