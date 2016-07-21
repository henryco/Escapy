package com.game.render.fbo.psProcess.program.userState;

import com.game.render.fbo.EscapyMultiFBO;
import com.game.render.fbo.excp.EscapyFBOtypeException;
import com.game.render.fbo.psProcess.program.FBORenderProgram;
import com.game.render.fbo.userState.NormalMapFBO;

public class FBOStdProgramFactory {

	public static FBORenderProgram<EscapyMultiFBO> colorDodge(EscapyMultiFBO target){
		return new FBOColorDodgeProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> hardDodge(EscapyMultiFBO target) {
		return new FBOHardDodgeProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> multiply(EscapyMultiFBO target) {
		return new FBOMultiplyProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> screenDodge(EscapyMultiFBO target) {
		return new FBOScreenDodgeProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> softDodge(EscapyMultiFBO target) {
		return new FBOSoftDodgeProgram(target);
	}
	public static FBORenderProgram<EscapyMultiFBO> softLight(EscapyMultiFBO target) {
		return new FBOSoftLightProgram(target);
	}
	public static FBORenderProgram<?> volumeLight(EscapyMultiFBO target) throws EscapyFBOtypeException {
		if (target instanceof NormalMapFBO) return new FBOVolumeLightProgram((NormalMapFBO) target);
		else throw new EscapyFBOtypeException(); 
	}
}
