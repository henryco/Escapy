package com.game.physics_temp;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyPhysicsConstants.
 */
public class EscapyPhysicsConstants implements EscapyPhysics {

	private HashMap<String, Integer> intMap;
	private HashMap<String, Double> doubleMap;
	private HashMap<String, Float> floatMap;
	private HashMap<String, Long> longMap;
	private static float gConstant = ((40.8f) / 1.0f);
	// private static float gConstant = ((1.8f)/1.0f);

	/**
	 * Instantiates a new escapy physics constants.
	 */
	public EscapyPhysicsConstants() {
		intMap = new HashMap<>();
		floatMap = new HashMap<>();
		doubleMap = new HashMap<>();
		longMap = new HashMap<>();
	}

	/**
	 * G.
	 *
	 * @return the float
	 */
	public static float g() {
		return gConstant;
	}

	/**
	 * New long.
	 *
	 * @param Long
	 *            the long
	 * @param name
	 *            the name
	 * @return the long
	 */
	public long newLong(long Long, String name) {
		longMap.put(name, Long);
		return Long;
	}

	/**
	 * Long.
	 *
	 * @param name
	 *            the name
	 * @return the long
	 */
	public long Long(String name) {
		if (longMap.containsKey(name))
			return longMap.get(name);
		else
			return Long.MAX_VALUE;
	}

	/**
	 * Long.
	 *
	 * @param Long
	 *            the long
	 * @param name
	 *            the name
	 * @return the long
	 */
	public long Long(long Long, String name) {
		longMap.put(name, Long);
		return longMap.get(name);
	}

	/**
	 * New int.
	 *
	 * @param Int
	 *            the int
	 * @param name
	 *            the name
	 * @return the int
	 */
	public int newInt(int Int, String name) {
		intMap.put(name, Int);
		return Int;
	}

	/**
	 * Int.
	 *
	 * @param name
	 *            the name
	 * @return the int
	 */
	public int Int(String name) {
		if (intMap.containsKey(name))
			return intMap.get(name);
		else
			return Integer.MAX_VALUE;
	}

	/**
	 * Int.
	 *
	 * @param Int
	 *            the int
	 * @param name
	 *            the name
	 * @return the int
	 */
	public int Int(int Int, String name) {
		intMap.put(name, Int);
		return intMap.get(name);
	}

	/**
	 * New double.
	 *
	 * @param Double
	 *            the double
	 * @param name
	 *            the name
	 * @return the double
	 */
	public double newDouble(double Double, String name) {
		doubleMap.put(name, Double);
		return Double;
	}

	/**
	 * Double.
	 *
	 * @param name
	 *            the name
	 * @return the double
	 */
	public double Double(String name) {
		if (doubleMap.containsKey(name))
			return doubleMap.get(name);
		else
			return Double.MAX_VALUE;
	}

	/**
	 * Double.
	 *
	 * @param Double
	 *            the double
	 * @param name
	 *            the name
	 * @return the double
	 */
	public double Double(double Double, String name) {
		doubleMap.put(name, Double);
		return doubleMap.get(name);
	}

	/**
	 * New float.
	 *
	 * @param Float
	 *            the float
	 * @param name
	 *            the name
	 * @return the float
	 */
	public float newFloat(float Float, String name) {
		floatMap.put(name, Float);
		return Float;
	}

	/**
	 * Float.
	 *
	 * @param name
	 *            the name
	 * @return the float
	 */
	public float Float(String name) {
		if (floatMap.containsKey(name))
			return floatMap.get(name);
		else
			return Float.MAX_VALUE;
	}

	/**
	 * Float.
	 *
	 * @param Float
	 *            the float
	 * @param name
	 *            the name
	 * @return the float
	 */
	public float Float(float Float, String name) {
		floatMap.put(name, Float);
		return floatMap.get(name);
	}

}
