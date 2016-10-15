package com.game.map.objects.objects.utils.translators;

import com.game.map.objects.objects.utils.PositionTranslator;
import net.henryco.struct.container.tree.StructNode;

/**
 * @author Henry on 15/10/16.
 */
public class GameObjTranslators  {

	public static PositionTranslator orto(StructNode node) {

		long timeStep = node.getInt(Integer.MAX_VALUE, "3", "dt", "time", "period");
		float step = node.getFloat(0, "2", "step");
		float[] vector = new float[2];
		StructNode vecNode = node.getStructSafe("0", "vector", "vec");
		if (vecNode != null) vector = new float[]{vecNode.getFloat(0, "0", "x", "s"), vecNode.getFloat(0, "1", "y", "t")};
		final float[] vec = vector;

		return new PositionTranslator() {
			long t0 = 0;
			long t1 = 0;
			@Override
			public float[] translatePosition(float width, float height, float[] position) {
				if ((t1 = System.currentTimeMillis() - t0) >= timeStep) {
					t0 = System.currentTimeMillis();
					position[0] += (vec[0] * step);
					position[1] += (vec[1] * step);
				}
				return position;
			}
		};
	}
	public static PositionTranslator polar(StructNode node) {
		return (width, height, position) -> {
			return null;
		};
	}
}
