package com.x.game.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x.game.animator.EscapyAnimatorCharacter;
import com.x.game.controlls.EscapyPlayerControlls;
import com.x.game.physics_temp.EscapyPhysics;
import com.x.game.physics_temp.EscapyPhysicsEvent;
import com.x.game.physics_temp.EscapyPhysicsObjectDefault;
import com.x.game.physics_temp.EscapyPhysicsObjectSuper;
import com.x.game.physics_temp.TEMP_EscapyPhysicsPlayerControls;
import com.x.game.render.EscapyGdxCamera;

public class Player extends AbstractCharacters
		implements EscapyAnimatorCharacter, EscapyPhysicsEvent, EscapyPlayerControlls {

	@SuppressWarnings("unused")
	private boolean downLeft = false, downRight = false, pressedJump = false, downLShift = false, pressedF = false,
			isMoving = false;

	private Sprite characterSprite, NRMLSprite;
	private Texture[] actualTexture, actualNRMLTexture;

	private EscapyPhysicsObjectDefault physBody;


	public Player(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		super(urls, times, zoom);

		super.setXPos(100);
		super.setWidht(characterSprite.getWidth());
		super.setHeight(characterSprite.getHeight());

		this.physBody = new EscapyPhysicsObjectDefault(widht(), height(), mass(), xPos(), yPos(), this);
		this.physBody.setCalculation(true);

		addAnimatedCharacter(this);
		initCharacterAnimator(this);

	}

	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();

		this.characterSprite = new Sprite(super.standImg[0]);
		this.characterSprite.flip(false, true);
		this.characterSprite.setSize(characterSprite.getWidth() * (float) zoom(),
				characterSprite.getHeight() * (float) zoom());
		
		this.NRMLSprite = new Sprite(super.standImgNRML[0]);
		this.NRMLSprite.flip(false, true);
		this.NRMLSprite.setSize(NRMLSprite.getWidth() * (float) zoom(),
				NRMLSprite.getHeight() * (float) zoom());
		
	}

	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) 
	{
		super.spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);

		if (lastWasRight())
			this.characterSprite = this.setFrame0(actualTexture[actualFrame]);
		else if (lastWasLeft())
			this.characterSprite = this.setFrame180(actualTexture[actualFrame]);

		super.spriteBatcher.begin();
			this.characterSprite.setPosition(super.xPos(), super.yPos());
			this.characterSprite.draw(spriteBatcher);
		super.spriteBatcher.end();

	}
	
	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		super.spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);

		try {
			if (lastWasRight())
				this.NRMLSprite = this.setFrame0(actualNRMLTexture[actualFrame]);
			else if (lastWasLeft())
				this.NRMLSprite = this.setFrame180(actualNRMLTexture[actualFrame]);
		} catch (IndexOutOfBoundsException e) {}
		
		super.spriteBatcher.begin();
			this.NRMLSprite.setPosition(super.xPos(), super.yPos());
			this.NRMLSprite.draw(spriteBatcher);
		super.spriteBatcher.end();
		
	}
	

	@Override
	public void defineStandAnimation() {
		if (!isMoving) {
			if (!isLastStand()) {
				actualFrame = 0;
			}
			setLastStand(true);
			this.actualTexture = super.animation(standImg, stand);
			this.actualNRMLTexture = super.animation(standImgNRML, stand);
		} else {
			setLastStand(false);
		}
	}

	@Override
	public void defineMovAnimation() {
		if (!lastFall && downRight & !downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastMov()) {
					actualFrame = 0;
				}
				setLastMov(true);
				actualTexture = super.animation(walkImg, walk);
				setRightlast();
			}
		} else if (!downRight && downLShift) {
			setLastMov(false);
		}

		if (!lastFall && downLeft && !downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastMov()) {
					actualFrame = 0;
				}
				setLastMov(true);
				actualTexture = super.animation(walkImg, walk);
				setLeftlast();
			}
		} else if (!downLeft && downLShift) {
			setLastMov(false);
		}
	}

	@Override
	public void defineRunAnimation() {
		if (!lastFall && downRight && downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastRun()) {
					actualFrame = 0;
				}
				setLastRun(true);
				actualTexture = super.animation(runImg, run);
				setRightlast();
			}
		} else if (!downRight && !downLShift) {
			setLastRun(false);
		}

		if (!lastFall && downLeft && downLShift) {
			if (!TEMP_EscapyPhysicsPlayerControls.isFlyin()) {
				if (!isLastRun()) {
					actualFrame = 0;
				}
				setLastRun(true);
				actualTexture = super.animation(runImg, run);
				setLeftlast();
			}
		} else if (!downLeft && !downLShift) {
			setLastRun(false);
		}
	}

	@Override
	public void defineJumpAnimation() {
		// TODO Auto-generated method stub
	}

	@Override
	public void defineInteractAnimation() {
		// TODO Auto-generated method stub
	}

	@Override
	public void defineOtherAnimation() {
		// TODO Auto-generated method stub
	}

	@Override
	public void InterruptAnimator(EscapyAnimatorCharacter character) {
		// TODO Auto-generated method stub
	}

	
	
	@Override
	protected Sprite setFrame0(Texture texture) {
		
		Sprite characterSp = new Sprite(texture);
		characterSp.flip(false, true);
		characterSp.setSize(characterSp.getWidth() * (float) zoom(),
				characterSp.getHeight() * (float) zoom());
		return characterSp;
		
	}
	
	@Override
	protected Sprite setFrame180(Texture texture) {
		Sprite characterSp = new Sprite(texture);
		characterSp.flip(true, true);
		characterSp.setSize(characterSp.getWidth() * (float) zoom(),
				characterSp.getHeight() * (float) zoom());
		return characterSp;
	}

	@Override
	public void definePhysicalSystem(EscapyPhysicsObjectSuper physObject) {
		physObject = EscapyPhysics.initDefaultGravityAcceleration(physObject);
		physObject = EscapyPhysics.initDefaultMov(physObject, super.movSpeed, super.runSpeed, 120);
		physObject = EscapyPhysics.initDefaultPhysicalMap(physObject, 0.52f, 0.875f);
	}

	@Override
	public void physicalCalculations(EscapyPhysicsObjectSuper physObject) {
		physObject = EscapyPhysics.defaultGravity(physObject);
		physObject = EscapyPhysics.defaultMovement(physObject, this.downLeft, this.downRight, this.downLShift, false);
	}

	@Override
	public void physicalEvent(float xpos, float ypos, float mass, float tetha, EscapyPhysicsObjectSuper physObject) {
		super.setXPos(xpos);
		super.setYPos(ypos);
	}

	@Override
	public EscapyPhysicsObjectSuper getPhysicalBody() {
		return physBody;
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

	

}
