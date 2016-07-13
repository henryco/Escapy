package com.x.game.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.x.game.animator.EscapyAnimatorCharacter;
import com.x.game.render.EscapyGdxCamera;


public class NPC extends AbstractCharacters implements EscapyAnimatorCharacter{

	public NPC(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) 
	{
		super(urls, times, zoom);
		addAnimatedCharacter(this);
		initCharacterAnimator(this);
		
	}
	
	@Override
	protected void initializeGraphic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderGraphic(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defineStandAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defineMovAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defineRunAnimation() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Sprite setFrame180(Texture texture) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renderNormals(float[] translationMatrix, EscapyGdxCamera escapyCamera) {
		// TODO Auto-generated method stub
		
	}



}
