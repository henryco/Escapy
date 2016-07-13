package com.x.game.render.camera.program;

import com.x.game.characters.AbstractCharacters;

public class CameraProgramFactory {

	public static CameraProgram<AbstractCharacters> standartCharacterProgram(AbstractCharacters programTarget,
			int mapSizeX, int mapSizeY) {
		return new StandartCharacterCamProgram(programTarget, mapSizeX, mapSizeY);
	}

	public static CameraProgram<AbstractCharacters> standartCharacterProgram(AbstractCharacters programTarget) {
		return new StandartCharacterCamProgram(programTarget);
	}

	public static CameraProgram<AbstractCharacters> standartCharacterProgram() {
		return new StandartCharacterCamProgram();
	}

}
