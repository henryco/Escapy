package com.x.game.animator;

public interface EscapyAnimatorCharacter extends EscapyAnimator {

	
	public void defineStandAnimation();
	
	public void defineMovAnimation();
	
	public void defineRunAnimation();
	
	public void defineJumpAnimation();
	
	public void defineInteractAnimation();
	
	public void defineOtherAnimation();
	
	public void InterruptAnimator(EscapyAnimatorCharacter character);

}
