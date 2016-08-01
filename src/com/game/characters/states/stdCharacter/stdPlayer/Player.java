package com.game.characters.states.stdCharacter.stdPlayer;

import java.util.ArrayList;

import com.game.characters.states.stdCharacter.StdCharacter;
import com.game.controlls.EscapyPlayerControlls;
import com.game.physics_temp.EscapyPhysics;
import com.game.physics_temp.EscapyPhysicsObjectSuper;
import com.game.physics_temp.TEMP_EscapyPhysicsPlayerControls;

public class Player extends StdCharacter implements EscapyPlayerControlls {
	
	@SuppressWarnings("unused")
	private boolean downLeft = false, downRight = false, 
			pressedJump = false, downLShift = false, 
			pressedF = false, isMoving = false;
	
	
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
	

	/* (non-Javadoc)
	 * @see com.game.physics_temp.EscapyPhysicsEvent#definePhysicalSystem(com.game.physics_temp.EscapyPhysicsObjectSuper)
	 */
	@Override
	public void definePhysicalSystem(EscapyPhysicsObjectSuper physObject) {
		physObject = EscapyPhysics.initDefaultGravityAcceleration(physObject);
		physObject = EscapyPhysics.initDefaultMov(physObject, super.movSpeed, super.runSpeed, 120);
		physObject = EscapyPhysics.initDefaultPhysicalMap(physObject, 0.52f, 0.875f);
	}

	/* (non-Javadoc)
	 * @see com.game.physics_temp.EscapyPhysicsEvent#physicalCalculations(com.game.physics_temp.EscapyPhysicsObjectSuper)
	 */
	@Override
	public void physicalCalculations(EscapyPhysicsObjectSuper physObject) {
		physObject = EscapyPhysics.defaultGravity(physObject);
		physObject = EscapyPhysics.defaultMovement(physObject, this.downLeft, this.downRight, this.downLShift, false);
	}

	/* (non-Javadoc)
	 * @see com.game.physics_temp.EscapyPhysicsEvent#physicalEvent(float, float, float, float, com.game.physics_temp.EscapyPhysicsObjectSuper)
	 */
	@Override
	public void physicalEvent(float xpos, float ypos, float mass, float tetha, EscapyPhysicsObjectSuper physObject) {
		super.setXPos(xpos);
		super.setYPos(ypos);
	}
	
	

	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineStandAnimation()
	 */
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

	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineMovAnimation()
	 */
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

	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineRunAnimation()
	 */
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


}
