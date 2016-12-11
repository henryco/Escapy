package com.game.characters.states.stdCharacter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.game.animator.EscapyAnimatorCharacter;
import com.game.boxPhysics.body.BodyHolder;
import com.game.boxPhysics.body.IBoxBody;
import com.game.characters.states.AbstractCharacters;

// TODO: Auto-generated Javadoc
/**
 * The Class StdCharacter.
 */
public abstract class StdCharacter extends AbstractCharacters
		implements EscapyAnimatorCharacter, BodyHolder {



	protected Sprite characterSprite, NRMLSprite, LTMPSprite;
	protected Texture[] actualTexture, actualNRMLTexture, actualLTMPTexture;


	protected IBoxBody bodyCharacter;

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
	public BodyDef getBodyDef() {

		BodyDef bodyDefChamp = new BodyDef();
		bodyDefChamp.position.set(xPos(), yPos());
		bodyDefChamp.type = BodyDef.BodyType.DynamicBody;
		bodyDefChamp.fixedRotation = true;
		bodyDefChamp.linearDamping = 1;

		return bodyDefChamp;
	}

	@Override
	public void updateHolder(IBoxBody boxBody) {
		this.bodyCharacter = boxBody;
		float msc = boxBody.getMeterScale();
		Vector2 pos = boxBody.getBody().getPosition();
		setPosition(
				(pos.x / msc) - (characterSprite.getWidth() * 0.42f),
				(pos.y / msc) - (characterSprite.getHeight() * 0.54f)
		);
	}


	private void render(Batch batch, Sprite sprite, Texture[] textures) {

		if (textures[actualFrame] != null) {
			if (lastWasRight()) sprite = this.setFrame0(textures[actualFrame]);
			else if (lastWasLeft()) sprite = this.setFrame180(textures[actualFrame]);
		}
		sprite.setPosition(super.xPos(), super.yPos());
		sprite.draw(batch);
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
	public void setID(int id) {
		this.ID = Integer.hashCode(this.hashCode() + Integer.hashCode(id));
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public IBoxBody getIBoxBody() {
		return bodyCharacter;
	}

	public float[] getPosition(){
		return new float[]{xPos(), yPos()};
	}
}
