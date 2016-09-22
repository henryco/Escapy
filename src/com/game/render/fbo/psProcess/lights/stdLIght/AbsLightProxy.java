package com.game.render.fbo.psProcess.lights.stdLIght;

/**
 * @author Henry on 22/09/16.
 */
public class AbsLightProxy {

	public int accuracy = Integer.MAX_VALUE;
	public float minRadius = Float.NaN;
	public float maxRadius = Float.NaN;
	public float scale = Float.NaN;
	public float umbraCoeff = Float.NaN;
	public float umbraRecess = Float.NaN;
	public float threshold = Float.NaN;
	public float angle = Float.NaN;
	public float angleCorr = Float.NaN;
	public float angleShift = Float.NaN;
	public float[] position = null;
	public int[] color = null;
	public boolean visible = true;

	public AbsLightProxy(){

	}
}
