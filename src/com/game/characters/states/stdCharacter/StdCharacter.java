package com.game.characters.states.stdCharacter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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


	protected Sprite characterSprite, NRMLSprite, LTMPSprite;
	protected Texture[] actualTexture, actualNRMLTexture, actualLTMPTexture;
	protected EscapyPhysicsObjectDefault physBody;

	private int ID;

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

		this.setID(hashCode());
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


	private void render(Batch batch, Sprite sprite, Texture[] textures){

		try {
			if (lastWasRight()) sprite = this.setFrame0(textures[actualFrame]);
			else if (lastWasLeft()) sprite = this.setFrame180(textures[actualFrame]);
		} catch (Exception ignored) {}

		batch.begin();
		sprite.setPosition(super.xPos(), super.yPos());
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void renderLightMap(Batch batch) {
		render(batch, LTMPSprite, actualLTMPTexture);
	}

	@Override
	public void renderGraphic(Batch batch) {
		render(batch, characterSprite, actualTexture);
	}

	@Override
	public void renderNormals(Batch batch) {
		render(batch, NRMLSprite, actualNRMLTexture);
	}



	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {

		super.spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		render(super.spriteBatcher, characterSprite, actualTexture);
	}
	

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {

		super.spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		render(super.spriteBatcher, NRMLSprite, actualNRMLTexture);
	}
	
	@Override
	public void renderLightMap(float[] translationMatrix, EscapyGdxCamera escapyCamera) {

		super.spriteBatcher.setProjectionMatrix(escapyCamera.getCamera().combined);
		render(super.spriteBatcher, LTMPSprite, actualLTMPTexture);
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


	@Override
	public void setID(int id) {
		this.ID = Integer.hashCode(this.hashCode() + Integer.hashCode(id));
	}

	@Override
	public int getID() {
		return ID;
	}
}
