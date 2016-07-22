package com.game.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.animator.EscapyAnimatorCharacter;
import com.game.render.EscapyGdxCamera;


// TODO: Auto-generated Javadoc
/**
 * The Class NPC.
 */
public class NPC extends AbstractCharacters implements EscapyAnimatorCharacter{

	/**
	 * Instantiates a new npc.
	 *
	 * @param urls
	 *            the urls
	 * @param times
	 *            the times
	 * @param zoom
	 *            the zoom
	 */
	public NPC(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) 
	{
		super(urls, times, zoom);
		addAnimatedCharacter(this);
		initCharacterAnimator(this);
		
	}
	
	/* (non-Javadoc)
	 * @see com.game.characters.AbstractCharacters#initializeGraphic()
	 */
	@Override
	protected void initializeGraphic() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.game.render.EscapyRenderable#renderGraphic(float[], com.game.render.EscapyGdxCamera)
	 */
	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineStandAnimation()
	 */
	@Override
	public void defineStandAnimation() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineMovAnimation()
	 */
	@Override
	public void defineMovAnimation() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorCharacter#defineRunAnimation()
	 */
	@Override
	public void defineRunAnimation() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.game.characters.AbstractCharacters#setFrame180(com.badlogic.gdx.graphics.Texture)
	 */
	@Override
	protected Sprite setFrame180(Texture texture) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.game.render.extra.normalMap.EscapyNormalMapRender#renderNormals(float[], com.game.render.EscapyGdxCamera)
	 */
	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		// TODO Auto-generated method stub
		
	}



}
