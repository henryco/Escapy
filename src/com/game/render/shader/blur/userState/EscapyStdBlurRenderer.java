package com.game.render.shader.blur.userState;

import com.game.render.shader.blur.EscapyBlurRenderer;

public class EscapyStdBlurRenderer extends EscapyBlurRenderer{

	public static final class EscapyBlur {
		
		private static final String url = "shaders\\blur\\";

		public static final String[] GAUSSIAN_5 = new String[]{
				url+"gauss5\\gauss5.vert", url+"gauss5\\gauss5.frag"};
		public static final String[] GAUSSIAN_9 = new String[]{
				url+"gauss9\\gauss9.vert", url+"gauss9\\gauss9.frag"};
		public static final String[] GAUSSIAN_13 = new String[]{
				url+"gauss13\\gauss13.vert", url+"gauss13\\gauss13.frag"};
	}
	
	public static final String SOURCEMAP = "u_texture";
	
	public EscapyStdBlurRenderer(String[] blurType) {
		super(SOURCEMAP, blurType[0], blurType[1]);
	}
	public EscapyStdBlurRenderer(int id, String[] blurType) {
		super(id, SOURCEMAP, blurType[0], blurType[1]);
	}
	
	
	@Override
	public String toString() {
		return "EscapyStdBlurRenderer_"+super.id;
	}
}
