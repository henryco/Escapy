package com.game.render.camera.program;

import com.game.characters.AbstractCharacters;
import com.game.render.EscapyGdxCamera;

// TODO: Auto-generated Javadoc
/**
 * The Class StandartCharacterCamProgram.
 */
public class StandartCharacterCamProgram 
	extends CameraProgram <AbstractCharacters> {

	/** The map size Y. */
	protected int mapSizeX, mapSizeY;

	/**
	 * Sets the map size.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the standart character cam program
	 */
	public StandartCharacterCamProgram setMapSize(int x, int y) {
		this.mapSizeX = x;
		this.mapSizeY = y;
		return this;
	}

	/**
	 * Instantiates a new standart character cam program.
	 */
	public StandartCharacterCamProgram() {

	}

	/**
	 * Instantiates a new standart character cam program.
	 *
	 * @param programTarget
	 *            the program target
	 */
	public StandartCharacterCamProgram(AbstractCharacters programTarget) {
		super(programTarget);
	}

	/**
	 * Instantiates a new standart character cam program.
	 *
	 * @param programTarget
	 *            the program target
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public StandartCharacterCamProgram(AbstractCharacters programTarget, int x, int y) {
		super(programTarget);
		this.setMapSize(x, y);
	}

	/* (non-Javadoc)
	 * @see com.game.render.camera.program.CameraProgram#programTranslate(int, int, float, com.game.render.EscapyGdxCamera)
	 */
	@Override
	public float[] programTranslate(int sWidth, int sHeight, float scale_optional, EscapyGdxCamera escapyCamera) {
		float[] translationMatrix = new float[2];
		int valX = 0;
		int camMoveTargetX = 0;

		float dlx = 0;
		float dl_Xmax = 0;

		if (!super.programTarget.isLastStand()
				&& (super.programTarget.isLastMov() || super.programTarget.isLastRun())) {
			if (super.programTarget.lastWasLeft()) {
				dl_Xmax = Math.abs(escapyCamera.getxIntervalLenght()[0]);
				camMoveTargetX = escapyCamera.getHorizontalBorders()[1];
				valX = (-1);
				if (super.programTarget.getPhysicalBody().getBodyPosition()[0] == camMoveTargetX)
					valX = 0;
				else if (super.programTarget.getPhysicalBody().getBodyPosition()[0] > camMoveTargetX)
					valX = 1;
			} else if (super.programTarget.lastWasRight()) {
				dl_Xmax = Math.abs(escapyCamera.getxIntervalLenght()[1]);
				camMoveTargetX = escapyCamera.getHorizontalBorders()[0];
				valX = 1;
				if (super.programTarget.getPhysicalBody().getBodyPosition()[0] == camMoveTargetX)
					valX = 0;
				else if (super.programTarget.getPhysicalBody().getBodyPosition()[0] < camMoveTargetX)
					valX = (-1);
			}
			dlx = Math.abs(super.programTarget.getPhysicalBody().getBodyPosition()[0] - camMoveTargetX);
		}

		if (super.programTarget.isLastStand())// ||
		// super.programTarget.getBodyPosition()[0] <
		// escapyCamera.getxIntervalLenght()[0])// (sWidth / 2.f))
		{
			if (super.programTarget.isLastStand())
				camMoveTargetX = (int) super.programTarget.getPhysicalBody().getBodyPosition()[0];

			// if (super.programTarget.getBodyPosition()[0] <
			// escapyCamera.getxIntervalLenght()[0])// (sWidth / 2.f))
			// camMoveTargetX = (int) (sWidth / 2.f);

			/** TODO __RIGHT_EDGE_END__ TODO **/

			if (escapyCamera.getCamera().position.x == camMoveTargetX)
				valX = 0;
			else if (escapyCamera.getCamera().position.x < camMoveTargetX)
				valX = 1;
			else if (escapyCamera.getCamera().position.x > camMoveTargetX)
				valX = (-1);

			dl_Xmax = Math.abs(escapyCamera.getxIntervalLenght()[0]) + Math.abs(escapyCamera.getxIntervalLenght()[1]);

			dlx = Math.abs(escapyCamera.getCamera().position.x - camMoveTargetX);
		}

		int camMoveSpeedX = (int) (super.programTarget.getMovSpeed() + 4);
		if (this.programTarget.isLastRun())
			camMoveSpeedX = (int) (super.programTarget.getRunSpeed() + 4);

		translationMatrix[0] = (float) ((camMoveSpeedX * valX) * 1. * (dlx / dl_Xmax));

		/**
		 * TODO translationMatrix[1] = y
		 **/

		return translationMatrix;
	}

}
