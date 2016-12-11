package com.game.characters.states.stdCharacter.stdPlayer;

import java.util.ArrayList;

import com.game.characters.states.stdCharacter.StdCharacter;
import com.game.controlls.EscapyPlayerControlls;
import com.game.render.camera.program.CameraProgramOwner;
import com.game.render.camera.program.CameraVector;

public class Player extends StdCharacter implements EscapyPlayerControlls, CameraProgramOwner {
	
	@SuppressWarnings("unused")
	private boolean downLeft = false, downRight = false, 
			pressedJump = false, downLShift = false, 
			pressedF = false, isMoving = false;
	

	private CameraVector cameraVector = new CameraVector();

	public Player(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		super(urls, times, zoom, new int[]{100, 100});
	}
	public Player(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom, int[] pos){
		super(urls, times, zoom, pos);
	}
	
	
	
	
	@Override
	public void updateControlls(boolean downA, boolean downD, boolean downSpace, boolean downLShift, boolean isMoving,
			boolean downF) {
		this.downLeft = downA;
		this.downRight = downD;
		this.downLShift = downLShift;
		this.pressedF = downF;
		this.pressedJump = downSpace;
		this.isMoving = isMoving;


		if (downA) bodyCharacter.setMoveSign(-1);
		if (downD) bodyCharacter.setMoveSign(+1);
	}
	

	

	@Override
	public void defineStandAnimation() {
		if (!isMoving) {
			if (!isLastStand()) {
				actualFrame = 0;
			}
			super.setLastStand(true);
			super.actualTexture = super.animation(standImg, stand);
			super.actualNRMLTexture = super.animation(standImgNRML, stand);
			super.actualLTMPTexture = super.animation(standImgLTMP, stand);
			cameraVector.setMoveNVector(0,0);
		} else {
			super.setLastStand(false);
		}
	}


	@Override
	public void defineMovAnimation() {
		if (!lastFall && downRight & !downLShift) {
			if (!isLastMov()) {
				super.actualFrame = 0;
			}
			super.setLastMov(true);
			super.actualTexture = super.animation(walkImg, walk);
			super.actualNRMLTexture = super.animation(walkImgNRML, walk);
			super.actualLTMPTexture = super.animation(walkImgLTMP, walk);
			super.setRightlast();
			cameraVector.setMoveNVector(1,0);

		} else if (!downRight && downLShift) {
			super.setLastMov(false);
		}

		if (!lastFall && downLeft && !downLShift) {
			if (!isLastMov()) {
				super.actualFrame = 0;
			}
			super.setLastMov(true);
			super.actualTexture = super.animation(walkImg, walk);
			super.actualNRMLTexture = super.animation(walkImgNRML, walk);
			super.actualLTMPTexture = super.animation(walkImgLTMP, walk);
			super.setLeftlast();
			cameraVector.setMoveNVector(-1,0);
		} else if (!downLeft && downLShift) {
			super.setLastMov(false);
		}
	}

	@Override
	public void defineRunAnimation() {
		if (!lastFall && downRight && downLShift) {
			if (!isLastRun()) {
				super.actualFrame = 0;
			}
			super.setLastRun(true);
			super.actualTexture = super.animation(runImg, run);
			super.actualNRMLTexture = super.animation(runImgNRML, run);
			super.actualLTMPTexture = super.animation(runImgLTMP, run);
			super.setRightlast();
			cameraVector.setMoveNVector(1,0);

		} else if (!downRight && !downLShift) {
			super.setLastRun(false);
		}

		if (!lastFall && downLeft && downLShift) {
			if (!isLastRun()) {
				super.actualFrame = 0;
			}
			super.setLastRun(true);
			super.actualTexture = super.animation(runImg, run);
			super.actualNRMLTexture = super.animation(runImgNRML, run);
			super.actualLTMPTexture = super.animation(runImgLTMP, run);
			super.setLeftlast();
			cameraVector.setMoveNVector(-1,0);
		} else if (!downLeft && !downLShift) {
			super.setLastRun(false);
		}
	}

	@Override
	public CameraVector getCameraVector() {
		return cameraVector;
	}

	@Override
	public float[] getOwnerPosition() {
		return getPosition();
	}
}
