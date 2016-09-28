package com.game.characters.states.stdCharacter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.animator.EscapyAnimatorCharacter;
import com.game.characters.states.AbstractCharacters;
import com.game.physics_temp.EscapyPhysicsEvent;
import com.game.physics_temp.EscapyPhysicsObjectDefault;
import com.game.physics_temp.EscapyPhysicsObjectSuper;
import com.game.render.camera.EscapyGdxCamera;

// TODO: Auto-generated Javadoc
/**
 * The Class StdCharacter.
 */
public abstract class StdCharacter extends AbstractCharacters
		implements EscapyAnimatorCharacter, EscapyPhysicsEvent {


	private Sprite characterSprite, NRMLSprite, LTMPSprite;
	protected Texture[] actualTexture;
	protected Texture[] actualNRMLTexture;
	protected Texture[] actualLTMPTexture;

	private EscapyPhysicsObjectDefault physBody;


	/**
	 * Instantiates a new player.
	 *
	 * @param urls
	 *            the urls
	 * @param times
	 *            the times
	 * @param zoom
	 *            the zoom
	 */
	public StdCharacter(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		super(urls, times, zoom);
		this.initCharacter();
		

	}

	public StdCharacter(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom, int[] position) {
		super(urls, times, zoom);
		super.setPosition(position);
		this.initCharacter();
	}
	
	private void initCharacter() {
	
		this.physBody 
			= new EscapyPhysicsObjectDefault(characterSprite.getWidth(),
				characterSprite.getHeight(), 0, xPos(), yPos(), this);
		this.physBody.setCalculation(true);

		super.addAnimatedCharacter(this);
		super.initCharacterAnimator(this);
	}
	
	

	@Override
	protected void initializeGraphic() {
		super.spriteBatcher = new SpriteBatch();

		this.characterSprite = new Sprite(super.standImg[0]);
		this.characterSprite.flip(false, true);
		this.characterSprite.setSize(characterSprite.getWidth() * zoom(),
				characterSprite.getHeight() * zoom());
		
		this.NRMLSprite = new Sprite(super.standImgNRML[0]);
		this.NRMLSprite.flip(false, true);
		this.NRMLSprite.setSize(NRMLSprite.getWidth() * zoom(),
				NRMLSprite.getHeight() * zoom());
		
		this.LTMPSprite = new Sprite(super.standImgLTMP[0]);
		this.LTMPSprite.flip(false, true);
		this.LTMPSprite.setSize(LTMPSprite.getWidth() * zoom(),
				LTMPSprite.getHeight() * zoom());
		
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
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			System.out.println("problem");
		}
		
		super.spriteBatcher.begin();
			this.NRMLSprite.setPosition(super.xPos(), super.yPos());
			this.NRMLSprite.draw(spriteBatcher);
		super.spriteBatcher.end();
		
	}
	
	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		super.spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);

		try {
			if (lastWasRight())
				this.LTMPSprite = this.setFrame0(actualLTMPTexture[actualFrame]);
			else if (lastWasLeft())
				this.LTMPSprite = this.setFrame180(actualLTMPTexture[actualFrame]);
		} catch (IndexOutOfBoundsException | NullPointerException ignored) {
		}
		
		super.spriteBatcher.begin();
			this.LTMPSprite.setPosition(super.xPos(), super.yPos());
			this.LTMPSprite.draw(spriteBatcher);
		super.spriteBatcher.end();
	}
	
	

	@Override
	public void defineJumpAnimation() {
	}

	@Override
	public void defineInteractAnimation() {
	}

	@Override
	public void defineOtherAnimation() {
	}

	@Override
	public void InterruptAnimator(EscapyAnimatorCharacter character) {
	}


	@Override
	protected Sprite setFrame0(Texture texture) {
		
		Sprite characterSp = new Sprite(texture);
		characterSp.flip(false, true);
		characterSp.setSize(characterSp.getWidth() * zoom(),
				characterSp.getHeight() * zoom());
		return characterSp;
		
	}

	@Override
	protected Sprite setFrame180(Texture texture) {
		Sprite characterSp = new Sprite(texture);
		characterSp.flip(true, true);
		characterSp.setSize(characterSp.getWidth() * zoom(),
				characterSp.getHeight() * zoom());
		return characterSp;
	}



	@Override
	public EscapyPhysicsObjectSuper getPhysicalBody() {
		return physBody;
	}


	

}
