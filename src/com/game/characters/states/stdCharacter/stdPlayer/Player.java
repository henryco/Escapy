package com.game.characters.states.stdCharacter.stdPlayer;

import java.util.ArrayList;

import com.game.characters.states.stdCharacter.StdCharacter;
import com.game.controlls.EscapyPlayerControlls;
import com.game.physics_temp.EscapyPhysics;
import com.game.physics_temp.EscapyPhysicsObjectSuper;
import com.game.physics_temp.TEMP_EscapyPhysicsPlayerControls;
import com.game.render.camera.program2_0.CameraProgramOwner;

public class Player extends StdCharacter implements EscapyPlayerControlls, CameraProgramOwner {
	
	@SuppressWarnings("unused")
	private boolean downLeft = false, downRight = false, 
			pressedJump = false, downLShift = false, 
			pressedF = false, isMoving = false;
	

	private int[] mvNVector = new int[2];

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
	}
	


	@Override
	public void definePhysicalSystem(EscapyPhysicsObjectSuper physObject) {
		EscapyPhysics.initDefaultGravityAcceleration(physObject);
		EscapyPhysics.initDefaultMov(physObject, super.movSpeed, super.runSpeed, 120);
		EscapyPhysics.initDefaultPhysicalMap(physObject, 0.52f, 0.875f);
	}


	@Override
	public void physicalCalculations(EscapyPhysicsObjectSuper physObject) {
		EscapyPhysics.defaultGravity(physObject);
		EscapyPhysics.defaultMovement(physObject, this.downLeft, this.downRight, this.downLShift, false);
	}


	@Override
	public void physicalEvent(float xpos, float ypos, float mass, float tetha, EscapyPhysicsObjectSuper physObject) {
		setMoveNVector(defNVector(xpos, xPos()), defNVector(ypos, yPos()));
		super.setXPos(xpos);
		super.setYPos(ypos);
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
		} else {
			super.setLastStand(false);
		}
	}


	@Override
	public void defineMovAnimation() {
		if (!lastFall && downRight & !downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastMov()) {
					super.actualFrame = 0;
				}
				super.setLastMov(true);
				super.actualTexture = super.animation(walkImg, walk);
				super.actualNRMLTexture = super.animation(walkImgNRML, walk);
				super.actualLTMPTexture = super.animation(walkImgLTMP, walk);
				super.setRightlast();
			}
		} else if (!downRight && downLShift) {
			super.setLastMov(false);
		}

		if (!lastFall && downLeft && !downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastMov()) {
					super.actualFrame = 0;
				}
				super.setLastMov(true);
				super.actualTexture = super.animation(walkImg, walk);
				super.actualNRMLTexture = super.animation(walkImgNRML, walk);
				super.actualLTMPTexture = super.animation(walkImgLTMP, walk);
				super.setLeftlast();
			}
		} else if (!downLeft && downLShift) {
			super.setLastMov(false);
		}
	}

	@Override
	public void defineRunAnimation() {
		if (!lastFall && downRight && downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastRun()) {
					super.actualFrame = 0;
				}
				super.setLastRun(true);
				super.actualTexture = super.animation(runImg, run);
				super.actualNRMLTexture = super.animation(runImgNRML, run);
				super.actualLTMPTexture = super.animation(runImgLTMP, run);
				super.setRightlast();
			}
		} else if (!downRight && !downLShift) {
			super.setLastRun(false);
		}

		if (!lastFall && downLeft && downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastRun()) {
					super.actualFrame = 0;
				}
				super.setLastRun(true);
				super.actualTexture = super.animation(runImg, run);
				super.actualNRMLTexture = super.animation(runImgNRML, run);
				super.actualLTMPTexture = super.animation(runImgLTMP, run);
				super.setLeftlast();
			}
		} else if (!downLeft && !downLShift) {
			super.setLastRun(false);
		}
	}


	private static int defNVector(float _pos, float _last_pos) {
		if (_pos - _last_pos > 0) return 1;
		if (_pos - _last_pos < 0) return -1;
		return 0; //FIXME TODO NOT SYNCHRONIZED WITH CAMERA PROGRAM IN RENDER LOOP!
	}

	@Override
	public CameraProgramOwner setOwnerPosition(float... xy) {
		return this;
	}

	@Override
	public CameraProgramOwner setMoveNVector(int... xy) {
		mvNVector[0] = xy[0];
		mvNVector[1] = xy[1];
		return this;
	}

	@Override
	public int[] getMoveNVector() {
		return mvNVector;
	}

	@Override
	public float[] getOwnerPosition() {
		return getPhysicalBody().getBodyPosition();
	}
}
