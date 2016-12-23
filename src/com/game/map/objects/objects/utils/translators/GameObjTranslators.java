package com.game.map.objects.objects.utils.translators;

import com.game.map.objects.objects.utils.PositionTranslator;
import net.henryco.struct.container.tree.StructNode;

import java.lang.reflect.Method;

/**
 * @author Henry on 15/10/16.
 */
public class GameObjTranslators  {


	public static PositionTranslator loadPosTranslator(StructNode shiftNode) {

		if (shiftNode != null && shiftNode.getChild().length > 0) {
			StructNode transNode = shiftNode.getStructSafe(shiftNode.getChild()[0]);
			if (transNode != null)
				try {
					Method trm = GameObjTranslators.class.getDeclaredMethod(transNode.name, StructNode.class);
					trm.setAccessible(true);
					return (PositionTranslator) trm.invoke(GameObjTranslators.class, transNode);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	public static PositionTranslator loadByName(String[] arg) {

		try {
			Method mmm = GameObjTranslators.class.getDeclaredMethod(arg[0], String[].class);
			mmm.setAccessible(true);
			return (PositionTranslator) mmm.invoke(GameObjTranslators.class, (Object) arg);
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
		return null;
	}


	public static PositionTranslator orto(String[] args) {

		float[] vec = new float[]{Float.parseFloat(args[1]), Float.parseFloat(args[2])};
		float step = Float.parseFloat(args[3]);
		float timeStep = Float.parseFloat(args[4]);

		return new PositionTranslator() {
			long t0 = 0;
			long t1 = 0;
			@Override
			public float[] translatePosition(float width, float height, float[] position) {
				if ((t1 = System.currentTimeMillis() - t0) >= timeStep) {
					t0 = System.currentTimeMillis();
					return new float[]{position[0] + (vec[0] * step), position[1] + (vec[1] * step)};
				}
				return position;
			}
		};
	}

	public static PositionTranslator polar(String[] args) {
		return (width, height, position) -> null;
	}

	public static PositionTranslator orto(StructNode node) {

		StructNode vecNode = node.getStructSafe("0", "vector", "vec");
		if (vecNode != null) {
			return orto(new String[]{"", vecNode.getPrimitive("0", "x", "s"), vecNode.getPrimitive("1", "y", "t"),
					node.getPrimitive("1", "step"), node.getPrimitive("2", "dt", "time", "period")});
		}
		return null;
	}
	public static PositionTranslator polar(StructNode node) {
		return (width, height, position) -> {
			return null;
		};
	}
}
