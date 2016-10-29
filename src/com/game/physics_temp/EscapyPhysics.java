package com.game.physics_temp;

// TODO: Auto-generated Javadoc

/**
 * The Interface EscapyPhysics.
 */
public interface EscapyPhysics {

	/** The Constant gravityTime0. */
	String gravityTime0 = "89fn9";

	/** The Constant gravityTime1. */
	String gravityTime1 = "4h3gn";

	/** The Constant objectPosY0. */
	String objectPosY0 = "dwe4t";

	/** The Constant objectPosX0. */
	String objectPosX0 = "2brme";

	/** The Constant objectPosYR1. */
	String objectPosYR1 = "f-0vcz";

	/** The Constant movLenght. */
	String movLenght = "80hg3";

	/** The Constant runLenght. */
	String runLenght = "d34vza";

	/** The Constant jumpSpeed. */
	String jumpSpeed = "19u0v";

	/** The Constant moveTime0. */
	String moveTime0 = "23czs";

	/** The Constant moveTime1. */
	String moveTime1 = "32zx3";

	/** The Constant w_c_r. */
	String w_c_r = "e5zxva";

	/** The Constant h_c_r. */
	String h_c_r = "ze2z1";

	/** The Constant fluct_acc. */
	String fluct_acc = "9mr3c";

	/** The Constant inFly. */
	String inFly = "sxcwe3";

	/** The Constant notLanded. */
	String notLanded = "1bx8a";

	/**
	 * Phys base calculations.
	 *
	 * @param physObject the phys object
	 * @return the escapy physics object super
	 */
	static EscapyPhysicsObjectSuper physBaseCalculations(EscapyPhysicsObjectSuper physObject) {
		return physObject;
	}

	/**
	 * Inits the default mov.
	 *
	 * @param physObject the phys object
	 * @param mov_leng   the mov leng
	 * @param run_leng   the run leng
	 * @param jump_speed the jump speed
	 * @return the escapy physics object super
	 */
	static EscapyPhysicsObjectSuper initDefaultMov(EscapyPhysicsObjectSuper physObject, float mov_leng,
												   float run_leng, float jump_speed) {
		physObject.variables.newInt((int) Math.ceil(mov_leng / 5.), movLenght);
		physObject.variables.newInt((int) Math.ceil(run_leng / 5.), runLenght);
		physObject.variables.newInt((int) Math.ceil((int) (jump_speed)), jumpSpeed);
		physObject.variables.newLong(System.nanoTime(), moveTime0);
		physObject.variables.newLong(0, moveTime1);
		physObject.variables.newInt(0, notLanded);
		return physObject;
	}

	/**
	 * Default movement.
	 *
	 * @param physObject the phys object
	 * @param left       the left
	 * @param right      the right
	 * @param run        the run
	 * @param jump       the jump
	 * @return the escapy physics object super
	 */
	static EscapyPhysicsObjectSuper defaultMovement(EscapyPhysicsObjectSuper physObject, boolean left,
													boolean right, boolean run, boolean jump) {
		physObject.variables.newLong(System.nanoTime(), moveTime1);
		if (!left & !right) {
			physObject.variables.Long(System.nanoTime(), moveTime0);
			physObject.variables.Long(physObject.variables.Long(moveTime0), moveTime1);
		}
		float movetime = (physObject.variables.Long(moveTime1) - physObject.variables.Long(moveTime0)) / 1000000000.f;

		if (left || right) {
			if (movetime >= 0.0050f) {
				physObject.variables.Long(System.nanoTime(), moveTime0);
				if (left) {
					if (!run)
						physObject.xpos -= Math
								.abs(horizontalMapCollides(physObject, -(physObject.variables.Int(movLenght))));
					if (run)
						physObject.xpos -= Math
								.abs(horizontalMapCollides(physObject, -(physObject.variables.Int(runLenght))));
				} else if (right) {
					if (!run)
						physObject.xpos += Math
								.abs(horizontalMapCollides(physObject, (physObject.variables.Int(movLenght))));
					if (run)
						physObject.xpos += Math
								.abs(horizontalMapCollides(physObject, (physObject.variables.Int(runLenght))));
				}
			}
		}
		if (jump & physObject.variables.Int(inFly) == 0 & physObject.variables.Int(notLanded) == 0) {
			physObject.variables.Int(1, notLanded);
			int height = physObject.variables.Int(jumpSpeed);
			TEMP_EscapyPhysicsPlayerControls.setSpace(false);
			TEMP_EscapyPhysicsPlayerControls.setJumping(true);
			TEMP_EscapyPhysicsPlayerControls.setFlyin(true);
			physObject.variables.Int(height, inFly);
			physObject.ypos -= 1;
			physObject.Vy -= 25;
			physObject.variables.Int(
					(int) (physObject.ypos + (physObject.height() * physObject.variables.Float(h_c_r))), objectPosY0);
		}
		return physObject;
	}

	/**
	 * Horizontal map collides.
	 *
	 * @param physObject the phys object
	 * @param delta      the delta
	 * @return the int
	 */
	static int horizontalMapCollides(EscapyPhysicsObjectSuper physObject, int delta) {
		if (physObject.variables.Int(objectPosY0) != Integer.MAX_VALUE) {
			while ((EscapyPhysicsSuper.areaMap.get(physObject.variables.Int(objectPosX0) + delta,
					(physObject.variables.Int(objectPosY0)), 0)) != null
					&& Byte.toUnsignedInt(
					(byte) EscapyPhysicsSuper.areaMap.get(physObject.variables.Int(objectPosX0) + delta,
							(physObject.variables.Int(objectPosY0)), 0)) == 1) {
				if (delta > 0)
					delta -= 1;
				else if (delta < 0)
					delta += 1;

				if (delta == 0) {
					physObject.ypos += 1;
					physObject.variables.Int(
							(int) (physObject.ypos + (physObject.height() * physObject.variables.Float(h_c_r))),
							objectPosY0);
				}
			}
		}
		return delta;
	}

	/**
	 * Default gravity.
	 *
	 * @param physObject the phys object
	 * @return the escapy physics object super
	 */
	static EscapyPhysicsObjectSuper defaultGravity(EscapyPhysicsObjectSuper physObject) {
		physObject.variables.Long(System.nanoTime(), gravityTime1);
		float timeago = (physObject.variables.Long(gravityTime1) - physObject.variables.Long(gravityTime0))
				/ 1000000000.f;
		physObject.ay = EscapyPhysicsConstants.g() + physObject.variables.Float(fluct_acc);

		if (physObject.variables.Int(inFly) != 0) {
			TEMP_EscapyPhysicsPlayerControls.setJumping(true);
			physObject.ypos -= 1f;
			physObject.variables.Int(physObject.variables.Int(inFly) - 1, inFly);
		} else {
			TEMP_EscapyPhysicsPlayerControls.setJumping(false);
		}

		physObject.Vy = (int) ((physObject.ay) * timeago);

		if (physObject.variables.Int(objectPosX0) != Integer.MAX_VALUE) {
			physObject.variables.Int((int) (physObject.xpos + (physObject.width() * physObject.variables.Float(w_c_r))),
					objectPosX0);
			physObject.variables.Int(
					(int) (physObject.ypos + (physObject.height() * physObject.variables.Float(h_c_r))), objectPosY0);

			physObject.ypos += verticalMapCollides(physObject, timeago);
		} else
			physObject.ypos += (timeago * physObject.Vy);

		if (physObject.Vy >= 10) {
			TEMP_EscapyPhysicsPlayerControls.setFlyin(true);
		} else
			TEMP_EscapyPhysicsPlayerControls.setFlyin(false);

		return physObject;
	}

	/**
	 * Inits the default gravity acceleration.
	 *
	 * @param physObject the phys object
	 * @return the escapy physics object super
	 */
	static EscapyPhysicsObjectSuper initDefaultGravityAcceleration(EscapyPhysicsObjectSuper physObject) {
		physObject.variables.newInt(0, inFly);
		physObject.variables.newFloat(0, fluct_acc);
		physObject.ay = EscapyPhysicsConstants.g();
		physObject.variables.newLong(System.nanoTime(), gravityTime0);
		physObject.variables.newLong(0, gravityTime1);
		return physObject;
	}

	/**
	 * Inits the default physical map.
	 *
	 * @param physObject        the phys object
	 * @param colideWidthratio  the colide widthratio
	 * @param colideHeightRatio the colide height ratio
	 * @return the escapy physics object super
	 */
	static EscapyPhysicsObjectSuper initDefaultPhysicalMap(EscapyPhysicsObjectSuper physObject,
														   float colideWidthratio, float colideHeightRatio) {
		physObject.variables.newFloat(colideWidthratio, w_c_r);
		physObject.variables.newFloat(colideHeightRatio, h_c_r);
		physObject.variables.newInt((int) (physObject.xpos + (physObject.width() * physObject.variables.Float(w_c_r))),
				objectPosX0);
		physObject.variables.newInt((int) (physObject.ypos + (physObject.height() * physObject.variables.Float(h_c_r))),
				objectPosY0);
		return physObject;
	}

	/**
	 * Vertical map collides.
	 *
	 * @param physObject the phys object
	 * @param time       the time
	 * @return the int
	 */
	static int verticalMapCollides(EscapyPhysicsObjectSuper physObject, float time) {
		int deltaY = (int) (time * time * physObject.ay);
		if ((EscapyPhysicsSuper.areaMap.get(physObject.variables.Int(objectPosX0),
				(int) (physObject.ypos + physObject.height() + deltaY - 1), 0)) != null) {
			while ((EscapyPhysicsSuper.areaMap.get(physObject.variables.Int(objectPosX0),
					(int) (physObject.ypos + physObject.height() + deltaY - 1), 0)) != null
					&& Byte.toUnsignedInt((byte) EscapyPhysicsSuper.areaMap.get(physObject.variables.Int(objectPosX0),
					(int) (physObject.ypos + physObject.height() + deltaY - 1), 0)) == 1) {
				deltaY -= 1;
			}
		}

		if ((EscapyPhysicsSuper.areaMap.get(physObject.variables.Int(objectPosX0),
				(int) (physObject.ypos + physObject.height() + deltaY), 0)) != null) {
			if (Byte.toUnsignedInt((byte) EscapyPhysicsSuper.areaMap.get(physObject.variables.Int(objectPosX0),
					(int) (physObject.ypos + physObject.height() + deltaY), 0)) == 1) {
				physObject.variables.Float(0, fluct_acc);
				physObject.variables.Long(System.nanoTime(), gravityTime0);
				physObject.variables.Int(0, notLanded);
				TEMP_EscapyPhysicsPlayerControls.setFlyin(false);
				TEMP_EscapyPhysicsPlayerControls.setJumping(false);
			}
		}
		return deltaY;
	}

	/**
	 * Default g debug.
	 *
	 * @param physObject the phys object
	 */
	static void default_g_debug(EscapyPhysicsObjectSuper physObject) {
		float time = (physObject.variables.Long(EscapyPhysics.gravityTime1)
				- physObject.variables.Long(EscapyPhysics.gravityTime0)) / 1000000000.f;
		System.out.println("\n");
		System.out.println("Vy: " + physObject.Vy);
		System.out.println("ay: " + physObject.ay);
		System.out.println("f.fall time: " + time);
		System.out.println("ypos: " + physObject.ypos);
		System.out.println("xpos: " + physObject.xpos);
	}

	/**
	 * Default jump debug.
	 *
	 * @param physObject the phys object
	 */
	static void default_jump_debug(EscapyPhysicsObjectSuper physObject) {
		System.out.println("In Jump: " + TEMP_EscapyPhysicsPlayerControls.isJumping());
		System.out.println("In Fall: " + TEMP_EscapyPhysicsPlayerControls.isFlyin());
		System.out.println("Land stat:: " + physObject.variables.Int(notLanded));
		System.out.println("\n");
	}

}
