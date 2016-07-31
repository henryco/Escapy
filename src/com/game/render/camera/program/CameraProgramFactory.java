package com.game.render.camera.program;

import com.game.characters.states.AbstractCharacters;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating CameraProgram objects.
 */
public class CameraProgramFactory {

	/**
	 * Standart character program.
	 *
	 * @param programTarget
	 *            the program target
	 * @param mapSizeX
	 *            the map size X
	 * @param mapSizeY
	 *            the map size Y
	 * @return the camera program
	 */
	public static CameraProgram<AbstractCharacters> standartCharacterProgram(AbstractCharacters programTarget,
			int mapSizeX, int mapSizeY) {
		return new StandartCharacterCamProgram(programTarget, mapSizeX, mapSizeY);
	}

	/**
	 * Standart character program.
	 *
	 * @param programTarget
	 *            the program target
	 * @return the camera program
	 */
	public static CameraProgram<AbstractCharacters> stdCharacterProgram(AbstractCharacters programTarget) {
		return new StandartCharacterCamProgram(programTarget);
	}

	/**
	 * Standart character program.
	 *
	 * @return the camera program
	 */
	public static CameraProgram<AbstractCharacters> standartCharacterProgram() {
		return new StandartCharacterCamProgram();
	}

}
