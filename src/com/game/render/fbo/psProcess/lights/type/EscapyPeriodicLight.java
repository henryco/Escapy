package com.game.render.fbo.psProcess.lights.type;

import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.utils.periodic.EscapyPeriodicAction;
import net.henryco.struct.container.tree.StructNode;

import java.lang.reflect.Method;

/**
 * @author Henry on 04/11/16.
 */
public class EscapyPeriodicLight {

	@SuppressWarnings("unchecked")
	public static AbsStdLight createPeriodActions(AbsStdLight light, StructNode periodicNode) {

		if (periodicNode != null) {
			System.out.println(periodicNode);
			float[] period = new float[0];
			StructNode periods = periodicNode.getStructSafe("periods", "0", "T");
			if (periods != null) {
				String[] ch = periods.getPrimitiveArray();
				period = new float[ch.length];
				for (int i = 0; i < period.length; i++) period[i] = Integer.parseInt(ch[i]) / 1000f;
				light.setPeriods(period);
			}
			StructNode actions = periodicNode.getStructSafe("actions", "1", "action");
			if (actions != null) {
				String[] ch = actions.getStructChild();
				int min = Math.min(ch.length, period.length);
				EscapyPeriodicAction<AbsStdLight>[] periodActions = new EscapyPeriodicAction[period.length];
				EscapyPeriodicAction<AbsStdLight>[] tmpAct = new EscapyPeriodicAction[min];
				for (int i = 0; i < min; i++) {
					StructNode actStruct = actions.getStructSafe(i);
					if (actStruct != null) {
						String method = actStruct.getStructChild()[0];
						String[] args = actStruct.getStructSafe(method).getPrimitiveArray();
						tmpAct[i] = loadMethod(method, args);
					}
				}
				int flag = 0;
				for (int i = 0; i < period.length; i++) {
					periodActions[i] = tmpAct[flag];
					if ((flag += 1) >= min) flag = 0;
				}
				light.setPeriodicActions(periodActions);
			}
		}
		return light;
	}

	@SuppressWarnings("unchecked")
	private static EscapyPeriodicAction<AbsStdLight> loadMethod(String method, String... args) {
		try {
			Method retMeth = EscapyPeriodicLight.class.getDeclaredMethod(method, String[].class);
			return (EscapyPeriodicAction<AbsStdLight>) retMeth.invoke(EscapyPeriodicLight.class, (Object) args);
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}




	public static EscapyPeriodicAction<AbsStdLight> simpSwitch(final String ... args) {

		final boolean switcher = Boolean.parseBoolean(args[0]);
		return (delta, actMax, obj) -> obj.setVisible(switcher);
	}


	

}
