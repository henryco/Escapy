package com.game.physics_temp;

import java.util.ArrayList;

import cern.colt.matrix.ObjectMatrix3D;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyPhysicsSuper.
 */
public abstract class EscapyPhysicsSuper implements EscapyPhysics {

	/** The event object. */
	protected static ArrayList<EscapyPhysicsEvent> eventObject = new ArrayList<>();
	
	/** The phys object. */
	protected static ArrayList<EscapyPhysics> physObject = new ArrayList<>();
	
	/** The area map. */
	protected static ObjectMatrix3D areaMap;

	/**
	 * Adds the to calc list.
	 *
	 * @param object
	 *            the object
	 * @param physOb
	 *            the phys ob
	 */
	protected static void addToCalcList(EscapyPhysicsEvent object, EscapyPhysics physOb) {
		physObject.add(physOb);
		eventObject.add(object);
	}

	/**
	 * Removes the from calc list.
	 *
	 * @param object
	 *            the object
	 * @param physOb
	 *            the phys ob
	 */
	protected static void removeFromCalcList(EscapyPhysicsEvent object, EscapyPhysics physOb) {
		physObject.remove(physOb);
		eventObject.remove(object);
	}

	/**
	 * Adds the map.
	 *
	 * @param map
	 *            the map
	 */
	protected static void addMap(ObjectMatrix3D map) {
		areaMap = map;
	}

	/**
	 * Adds the inputs.
	 */
	protected static void addInputs() {
		// phsInp = inp;
		// TODO INPUTS
	}

}
