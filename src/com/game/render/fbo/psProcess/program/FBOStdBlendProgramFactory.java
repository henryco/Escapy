package com.game.render.fbo.psProcess.program;

import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.psProcess.program.stdBlend.FBOStdBlendProgram;
import com.game.render.fbo.psProcess.program.userState.FBOColorDodgeProgram;
import com.game.render.fbo.psProcess.program.userState.FBOHardDodgeProgram;
import com.game.render.fbo.psProcess.program.userState.FBOMultiplyProgram;
import com.game.render.fbo.psProcess.program.userState.FBOScreenDodgeProgram;
import com.game.render.fbo.psProcess.program.userState.FBOSoftDodgeProgram;
import com.game.render.fbo.psProcess.program.userState.FBOSoftLightProgram;
import com.game.render.shader.blend.EscapyBlendRenderer;

public class FBOStdBlendProgramFactory {

	/** The Constant TARGETMAP. */
	private static final String TARGETMAP = "targetMap";
	/** The Constant BLENDMAP. */
	private static final String BLENDMAP = "blendMap";
	
	private final static String defaultShadersPath ="data/shaders/blend/";
	private static String shaderPath = defaultShadersPath;
	
	public static String getDefaultShadersPath() {
		return defaultShadersPath;
	}
	public static FBOStdBlendProgramFactory setDefaultShadersPath(String defaultShadersPath) {
		FBOStdBlendProgramFactory.shaderPath = defaultShadersPath;
		return new FBOStdBlendProgramFactory();
	}
	public static void restoreShaderPath() {
		shaderPath = defaultShadersPath;
	}
	
	private static String vfpath(String path) {
		return shaderPath+path+"/"+path;
	}
	private static String frag(String fragment) {
		return vfpath(fragment)+".frag";
	}
	private static String vert(String vert) {
		return vfpath(vert)+".vert";
	}
	
	public static FBORenderProgram<EscapyMultiFBO> customBlender(EscapyMultiFBO target, 
			String vertex, String fragment, String targetMap, String blendMap) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vertex, fragment, targetMap, blendMap));
	}
	public static FBORenderProgram<EscapyMultiFBO> blender(EscapyMultiFBO target, 
			String vertex, String fragment, String targetMap, String blendMap) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert(vertex), frag(fragment), targetMap, blendMap));
	}
	
	/** 
	 * Color Dodge blend program, good for colorblind lights
	 * @return {@link FBORenderProgram}
	 */
	public static FBORenderProgram<EscapyMultiFBO> colorDodge(EscapyMultiFBO target){
		return new FBOColorDodgeProgram(target);
	}
	/** 
	 * Color Dodge blend program, good for colorblind lights
	 * @return {@link FBORenderProgram}
	 */
	public static FBORenderProgram<EscapyMultiFBO> colorDodge(){
		return new FBOColorDodgeProgram();
	}
	
	
	
	public static FBORenderProgram<EscapyMultiFBO> hardDodge(EscapyMultiFBO target) {
		return new FBOHardDodgeProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> hardDodge() {
		return new FBOHardDodgeProgram();
	}
	
	
	public static FBORenderProgram<EscapyMultiFBO> multiply(EscapyMultiFBO target) {
		return new FBOMultiplyProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> multiply() {
		return new FBOMultiplyProgram();
	}
	
	/** 
	 * Screen + Color Dodge blend program, very good for colorblind lights
	 * @return {@link FBORenderProgram}
	 */
	public static FBORenderProgram<EscapyMultiFBO> screenDodge(EscapyMultiFBO target) {
		return new FBOScreenDodgeProgram(target).setAmbientIntensity(0.8f)
				.setLightIntensity(0.4f);
	}
	/** 
	 * Screen + Color Dodge blend program, very good for colorblind lights
	 * @return {@link FBORenderProgram}
	 */
	public static FBORenderProgram<EscapyMultiFBO> screenDodge() {
		return new FBOScreenDodgeProgram().setAmbientIntensity(0.8f)
				.setLightIntensity(0.4f);
	}
	
	
	public static FBORenderProgram<EscapyMultiFBO> softDodge(EscapyMultiFBO target) {
		return new FBOSoftDodgeProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> softDodge() {
		return new FBOSoftDodgeProgram();
	}
	
	/** 
	 * Soft light blend program, it's also might be good for colorblind lights
	 * @return {@link FBORenderProgram}
	 */
	public static FBORenderProgram<EscapyMultiFBO> softLight(EscapyMultiFBO target) {
		return new FBOSoftLightProgram(target);
	}
	/** 
	 * Soft light blend program, it's also might be good for colorblind lights
	 * @return {@link FBORenderProgram}
	 */
	public static FBORenderProgram<EscapyMultiFBO> softLight() {
		return new FBOSoftLightProgram();
	}
	
	
	public static FBORenderProgram<EscapyMultiFBO> vividDodge(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("vividDodge"), frag("vividDodge"), TARGETMAP, BLENDMAP));
	}
	public static FBORenderProgram<EscapyMultiFBO> vividDodge() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("vividDodge"), frag("vividDodge"), TARGETMAP, BLENDMAP));
	}
	
	
	
	public static FBORenderProgram<EscapyMultiFBO> vividHue(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("vividHue"), frag("vividHue"), TARGETMAP, BLENDMAP));
	}
	public static FBORenderProgram<EscapyMultiFBO> vividHue() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("vividHue"), frag("vividHue"), TARGETMAP, BLENDMAP));
	}
	
	
	
	public static FBORenderProgram<EscapyMultiFBO> vividSoft(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("vividSoft"), frag("vividSoft"), TARGETMAP, BLENDMAP));
	}
	public static FBORenderProgram<EscapyMultiFBO> vividSoft() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("vividSoft"), frag("vividSoft"), TARGETMAP, BLENDMAP));
	}
	
	
	/**
	 * Color Dodge + Pinglight + Overlay blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> DPO(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("DPO"), frag("DPO"), TARGETMAP, BLENDMAP));
	}
	/**
	 * Color Dodge + Pinglight + Overlay blend program
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> DPO() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("DPO"), frag("DPO"), TARGETMAP, BLENDMAP));
	}
	
	
	public static FBORenderProgram<EscapyMultiFBO> dodgeMulti(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("dodgeMulti"), frag("dodgeMulti"), TARGETMAP, BLENDMAP));
	}
	public static FBORenderProgram<EscapyMultiFBO> dodgeMulti() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("dodgeMulti"), frag("dodgeMulti"), TARGETMAP, BLENDMAP));
	}
	
	
	/**
	 * Vivid + Saturation + ColorDodget blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> VSD(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("VSD"), frag("VSD"), TARGETMAP, BLENDMAP));
	}
	/**
	 * Vivid + Saturation + ColorDodget blend program
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> VSD() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("VSD"), frag("VSD"), TARGETMAP, BLENDMAP));
	}
	
	
	/**
	 * Vivid + Color + colorDodge blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> VCD(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("VCD"), frag("VCD"), TARGETMAP, BLENDMAP));
	}
	/**
	 * Vivid + Color + colorDodge blend program
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> VCD() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("VCD"), frag("VCD"), TARGETMAP, BLENDMAP));
	}
	
	
	/**
	 * Vivid + Hue + colorDodge blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> VHD(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("VHD"), frag("VHD"), TARGETMAP, BLENDMAP));
	}
	/**
	 * Vivid + Hue + colorDodge blend program
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> VHD() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("VHD"), frag("VHD"), TARGETMAP, BLENDMAP));
	}
	
	
	/**
	 * Vivid + Overlay + colorDodge blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> VOD(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("VOD"), frag("VOD"), TARGETMAP, BLENDMAP));
	}
	/**
	 * Vivid + Overlay + colorDodge blend program
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> VOD() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("VOD"), frag("VOD"), TARGETMAP, BLENDMAP));
	}
	
	
	/**
	 * cDodge + Vivid + Overlay + cDodge blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> VOD2(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("VOD2"), frag("VOD2"), TARGETMAP, BLENDMAP));
	}
	/**
	 * cDodge + Vivid + Overlay + cDodge blend program
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> VOD2() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("VOD2"), frag("VOD2"), TARGETMAP, BLENDMAP));
	}
	
	
	/**
	 * Color + Exlusion + colorBurn blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> CEB(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("CEB"), frag("CEB"), TARGETMAP, BLENDMAP));
	}
	/**
	 * Color + Exlusion + colorBurn blend program
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> CEB() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("CEB"), frag("CEB"), TARGETMAP, BLENDMAP));
	}
	
	/**
	 * Soft light blend program, but repeated, might be good for colorblind lights
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> strongSoftLight(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("strongSoftLight"), frag("strongSoftLight"), TARGETMAP, BLENDMAP))
				.setAmbientIntensity(0.75f)
				.setLightIntensity(0.2f);
	}
	/**
	 * Soft light blend program, but repeated, might be good for colorblind lights
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> strongSoftLight() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("strongSoftLight"), frag("strongSoftLight"), TARGETMAP, BLENDMAP))
					.setAmbientIntensity(0.75f)
					.setLightIntensity(0.2f);
	}
	/**
	 * cDodge + Softlight + Softlight blend program
	 * @param target {@link EscapyMultiFBO}
	 * @author Henry 
	 */
	public static FBORenderProgram<EscapyMultiFBO> reverseSoftDodge(EscapyMultiFBO target) {
		return new FBOStdBlendProgram(target, 
				new EscapyBlendRenderer(vert("reverseSoftDodge"), frag("reverseSoftDodge"), TARGETMAP, BLENDMAP));
	}
	/**
	 * cDodge + Softlight + Softlight blend program
	 * @author Henry
	 */
	public static FBORenderProgram<EscapyMultiFBO> reverseSoftDodge() {
		return new FBOStdBlendProgram(
				new EscapyBlendRenderer(vert("reverseSoftDodge"), frag("reverseSoftDodge"), TARGETMAP, BLENDMAP));
	}
	
}
