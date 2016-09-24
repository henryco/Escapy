package com.game.render.program.shader.blend;

import com.game.render.program.RenderProgram;

/**
 * @author Henry on 23/09/16.
 */
public class ShaderBlendProgram implements RenderProgram {

	/** The Constant TARGETMAP. */
	private static final String TARGETMAP = "targetMap";
	/** The Constant BLENDMAP. */
	private static final String BLENDMAP = "blendMap";
	private final static String defaultShadersPath ="data/shaders/blend/";
	private static String vfpath(String path) {
		return defaultShadersPath+path+"/"+path;
	}
	private static String frag(String fragment) {
		return vfpath(fragment)+".frag";
	}
	private static String vert(String vert) {
		return vfpath(vert)+".vert";
	}



	public static EscapyBlendRenderer blendProgram(String program) {
		return new EscapyBlendRenderer(vert(program), frag(program), TARGETMAP, BLENDMAP);
	}


	public static final class program {

		/**
		 * Color + Exlusion + colorBurn blend program
		 * @author Henry
		 */
		public static final String CEB = "CEB";
		/**
		 * Color Dodge + Pinlight + Overlay blend program
		 * @author Henry
		 */
		public static final String DPO = "DPO";
		/**
		 * Vivid + Color + colorDodge blend program
		 * @author Henry
		 */
		public static final String VCD = "VCD";
		/**
		 * Vivid + Overlay + colorDodge blend program
		 * @author Henry
		 */
		public static final String VOD = "VOD";
		/**
		 * cDodge + Vivid + Overlay + cDodge blend program
		 * @author Henry
		 */
		public static final String VOD2 = "VOD2";
		/**
		 * Vivid + Saturation + ColorDodget blend program
		 * @author Henry
		 */
		public static final String VSD = "VSD";

		public static final String COLOR_DODGE = "colorDodge";
		public static final String COLOR_DODGE_MULTI = "dodgeMulti";
		public static final String COLOR_DODGE_HARD = "hardDodge";

		public static final String SOFT_COLOR_DODGE_REVERSED = "reverseSoftDodge";
		public static final String SOFT_COLOR_DODGE = "softDodge";
		public static final String SOFT_LIGHT = "softLight";
		public static final String SOFT_LIGHT_STRONG = "strongSoftLight";

		public static final String VIVID_DODGE = "vividDodge";
		public static final String VIVID_HUE = "vividHue";
		public static final String VIVID_SOFT = "vividSoft";

		public static final String SCREEN_COLOR_DODGE = "scrClrDdg";

		public static final String MULTIPLY = "multiply";

		public static final String OVERLAY = "overlay";

		public static final String SCREEN = "screen";

	}

}
