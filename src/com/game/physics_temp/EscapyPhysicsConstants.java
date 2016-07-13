package com.game.physics_temp;

import java.util.HashMap;

public class EscapyPhysicsConstants implements EscapyPhysics {

	private HashMap<String, Integer> intMap;
	private HashMap<String, Double> doubleMap;
	private HashMap<String, Float> floatMap;
	private HashMap<String, Long> longMap;
	private static float gConstant = ((40.8f) / 1.0f);
	// private static float gConstant = ((1.8f)/1.0f);

	public EscapyPhysicsConstants() {
		intMap = new HashMap<>();
		floatMap = new HashMap<>();
		doubleMap = new HashMap<>();
		longMap = new HashMap<>();
	}

	public static float g() {
		return gConstant;
	}

	public long newLong(long Long, String name) {
		longMap.put(name, Long);
		return Long;
	}

	public long Long(String name) {
		if (longMap.containsKey(name))
			return longMap.get(name);
		else
			return Long.MAX_VALUE;
	}

	public long Long(long Long, String name) {
		longMap.put(name, Long);
		return longMap.get(name);
	}

	public int newInt(int Int, String name) {
		intMap.put(name, Int);
		return Int;
	}

	public int Int(String name) {
		if (intMap.containsKey(name))
			return intMap.get(name);
		else
			return Integer.MAX_VALUE;
	}

	public int Int(int Int, String name) {
		intMap.put(name, Int);
		return intMap.get(name);
	}

	public double newDouble(double Double, String name) {
		doubleMap.put(name, Double);
		return Double;
	}

	public double Double(String name) {
		if (doubleMap.containsKey(name))
			return doubleMap.get(name);
		else
			return Double.MAX_VALUE;
	}

	public double Double(double Double, String name) {
		doubleMap.put(name, Double);
		return doubleMap.get(name);
	}

	public float newFloat(float Float, String name) {
		floatMap.put(name, Float);
		return Float;
	}

	public float Float(String name) {
		if (floatMap.containsKey(name))
			return floatMap.get(name);
		else
			return Float.MAX_VALUE;
	}

	public float Float(float Float, String name) {
		floatMap.put(name, Float);
		return floatMap.get(name);
	}

}
