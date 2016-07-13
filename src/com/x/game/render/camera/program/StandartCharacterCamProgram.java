package com.x.game.render.camera.program;

import com.x.game.characters.AbstractCharacters;
import com.x.game.render.EscapyGdxCamera;

public class StandartCharacterCamProgram extends CameraProgram<AbstractCharacters> {

	protected int mapSizeX, mapSizeY;

	public StandartCharacterCamProgram setMapSize(int x, int y) {
		this.mapSizeX = x;
		this.mapSizeY = y;
		return this;
	}

	public StandartCharacterCamProgram() {

	}

	public StandartCharacterCamProgram(AbstractCharacters programTarget) {
		super(programTarget);
	}

	public StandartCharacterCamProgram(AbstractCharacters programTarget, int x, int y) {
		super(programTarget);
		this.setMapSize(x, y);
	}

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
				if (super.programTarget.getBodyPosition()[0] == camMoveTargetX)
					valX = 0;
				else if (super.programTarget.getBodyPosition()[0] > camMoveTargetX)
					valX = 1;
			} else if (super.programTarget.lastWasRight()) {
				dl_Xmax = Math.abs(escapyCamera.getxIntervalLenght()[1]);
				camMoveTargetX = escapyCamera.getHorizontalBorders()[0];
				valX = 1;
				if (super.programTarget.getBodyPosition()[0] == camMoveTargetX)
					valX = 0;
				else if (super.programTarget.getBodyPosition()[0] < camMoveTargetX)
					valX = (-1);
			}
			dlx = Math.abs(super.programTarget.getBodyPosition()[0] - camMoveTargetX);
		}

		if (super.programTarget.isLastStand())// ||
		// super.programTarget.getBodyPosition()[0] <
		// escapyCamera.getxIntervalLenght()[0])// (sWidth / 2.f))
		{
			if (super.programTarget.isLastStand())
				camMoveTargetX = super.programTarget.getBodyPosition()[0];

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
