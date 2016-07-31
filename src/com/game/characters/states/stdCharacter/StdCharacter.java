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
import com.game.render.EscapyGdxCamera;

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
	
	
	/* (non-Javadoc)
	 * @see com.game.characters.AbstractCharacters#initializeGraphic()
	 */
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
		
		this.LTMPSprite = new Sprite(super.standImgLTMP[0]);
		this.LTMPSprite.flip(false, true);
		this.LTMPSprite.setSize(LTMPSprite.getWidth() * (float) zoom(),
				LTMPSprite.getHeight() * (float) zoom());
		
	}

	/* (non-Javadoc)
	 * @see com.game.render.EscapyRenderable#renderGraphic(float[], com.game.render.EscapyGdxCamera)
	 */
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
	
	/* (non-Javadoc)
	 * @see com.game.render.extra.normalMap.EscapyNormalMapRender#renderNormals(float[], com.game.render.EscapyGdxCamera)
	 */
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
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		super.spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);

		try {
			if (lastWasRight())
				this.LTMPSprite = this.setFrame0(actualLTMPTexture[actualFrame]);
			else if (lastWasLeft())
				this.LTMPSprite = this.setFrame180(actualLTMPTexture[actualFrame]);
		} catch (IndexOutOfBoundsException e) {}
		
		super.spriteBatcher.begin();
			this.LTMPSprite.setPosition(super.xPos(), super.yPos());
			this.LTMPSprite.draw(spriteBatcher);
		super.spriteBatcher.end();
	}
	
	
	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineJumpAnimation()
	 */
	@Override
	public void defineJumpAnimation() {
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineInteractAnimation()
	 */
	@Override
	public void defineInteractAnimation() {
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineOtherAnimation()
	 */
	@Override
	public void defineOtherAnimation() {
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#InterruptAnimator(com.game.animator.EscapyAnimatorCharacter)
	 */
	@Override
	public void InterruptAnimator(EscapyAnimatorCharacter character) {
		// TODO Auto-generated method stub
	}

	
	
	/* (non-Javadoc)
	 * @see com.game.characters.AbstractCharacters#setFrame0(com.badlogic.gdx.graphics.Texture)
	 */
	@Override
	protected Sprite setFrame0(Texture texture) {
		
		Sprite characterSp = new Sprite(texture);
		characterSp.flip(false, true);
		characterSp.setSize(characterSp.getWidth() * (float) zoom(),
				characterSp.getHeight() * (float) zoom());
		return characterSp;
		
	}
	
	/* (non-Javadoc)
	 * @see com.game.characters.AbstractCharacters#setFrame180(com.badlogic.gdx.graphics.Texture)
	 */
	@Override
	protected Sprite setFrame180(Texture texture) {
		Sprite characterSp = new Sprite(texture);
		characterSp.flip(true, true);
		characterSp.setSize(characterSp.getWidth() * (float) zoom(),
				characterSp.getHeight() * (float) zoom());
		return characterSp;
	}


	/* (non-Javadoc)
	 * @see com.game.physics_temp.EscapyPhysicsEvent#getPhysicalBody()
	 */
	@Override
	public EscapyPhysicsObjectSuper getPhysicalBody() {
		return physBody;
	}


	

}
