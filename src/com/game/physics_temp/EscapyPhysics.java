package com.game.physics_temp;

public interface EscapyPhysics {

	public static final String gravityTime0 = "89fn9";
	public static final String gravityTime1 = "4h3gn";
	public static final String objectPosY0 = "dwe4t";
	public static final String objectPosX0 = "2brme";
	public static final String objectPosYR1 = "f-0vcz";
	public static final String movLenght = "80hg3";
	public static final String runLenght = "d34vza";
	public static final String jumpSpeed = "19u0v";
	public static final String moveTime0 = "23czs";
	public static final String moveTime1 = "32zx3";
	public static final String w_c_r = "e5zxva";
	public static final String h_c_r = "ze2z1";
	public static final String fluct_acc = "9mr3c";
	public static final String inFly = "sxcwe3";
	public static final String notLanded = "1bx8a";

	public static EscapyPhysicsObjectSuper physBaseCalculations(EscapyPhysicsObjectSuper physObject) {
		return physObject;
	}

	public static EscapyPhysicsObjectSuper initDefaultMov(EscapyPhysicsObjectSuper physObject, float mov_leng,
			float run_leng, float jump_speed) {
		physObject.variables.newInt((int) Math.ceil((double) (mov_leng / 5.)), movLenght);
		physObject.variables.newInt((int) Math.ceil((double) (run_leng / 5.)), runLenght);
		physObject.variables.newInt((int) Math.ceil((int) (jump_speed)), jumpSpeed);
		physObject.variables.newLong(System.nanoTime(), moveTime0);
		physObject.variables.newLong(0, moveTime1);
		physObject.variables.newInt(0, notLanded);
		return physObject;
	}

	public static EscapyPhysicsObjectSuper defaultMovement(EscapyPhysicsObjectSuper physObject, boolean left,
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

	public static int horizontalMapCollides(EscapyPhysicsObjectSuper physObject, int delta) {
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

	public static EscapyPhysicsObjectSuper defaultGravity(EscapyPhysicsObjectSuper physObject) {
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

	public static EscapyPhysicsObjectSuper initDefaultGravityAcceleration(EscapyPhysicsObjectSuper physObject) {
		physObject.variables.newInt(0, inFly);
		physObject.variables.newFloat(0, fluct_acc);
		physObject.ay = EscapyPhysicsConstants.g();
		physObject.variables.newLong(System.nanoTime(), gravityTime0);
		physObject.variables.newLong(0, gravityTime1);
		return physObject;
	}

	public static EscapyPhysicsObjectSuper initDefaultPhysicalMap(EscapyPhysicsObjectSuper physObject,
			float colideWidthratio, float colideHeightRatio) {
		physObject.variables.newFloat(colideWidthratio, w_c_r);
		physObject.variables.newFloat(colideHeightRatio, h_c_r);
		physObject.variables.newInt((int) (physObject.xpos + (physObject.width() * physObject.variables.Float(w_c_r))),
				objectPosX0);
		physObject.variables.newInt((int) (physObject.ypos + (physObject.height() * physObject.variables.Float(h_c_r))),
				objectPosY0);
		return physObject;
	}

	public static int verticalMapCollides(EscapyPhysicsObjectSuper physObject, float time) {
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

	public static void default_g_debug(EscapyPhysicsObjectSuper physObject) {
		float time = (physObject.variables.Long(EscapyPhysics.gravityTime1)
				- physObject.variables.Long(EscapyPhysics.gravityTime0)) / 1000000000.f;
		System.out.println("\n");
		System.out.println("Vy: " + physObject.Vy);
		System.out.println("ay: " + physObject.ay);
		System.out.println("f.fall time: " + time);
		System.out.println("ypos: " + physObject.ypos);
		System.out.println("xpos: " + physObject.xpos);
	}

	public static void default_jump_debug(EscapyPhysicsObjectSuper physObject) {
		System.out.println("In Jump: " + TEMP_EscapyPhysicsPlayerControls.isJumping());
		System.out.println("In Fall: " + TEMP_EscapyPhysicsPlayerControls.isFlyin());
		System.out.println("Land stat:: " + physObject.variables.Int(notLanded));
		System.out.println("\n");
	}

}
