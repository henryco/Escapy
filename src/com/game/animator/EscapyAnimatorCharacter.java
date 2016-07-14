package com.game.animator;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyAnimatorCharacter.
 */
public interface EscapyAnimatorCharacter extends EscapyAnimator {

	
	/**
	 * Define stand animation.
	 */
	public void defineStandAnimation();
	
	/**
	 * Define mov animation.
	 */
	public void defineMovAnimation();
	
	/**
	 * Define run animation.
	 */
	public void defineRunAnimation();
	
	/**
	 * Define jump animation.
	 */
	public void defineJumpAnimation();
	
	/**
	 * Define interact animation.
	 */
	public void defineInteractAnimation();
	
	/**
	 * Define other animation.
	 */
	public void defineOtherAnimation();
	
	/**
	 * Interrupt animator.
	 *
	 * @param character the character
	 */
	public void InterruptAnimator(EscapyAnimatorCharacter character);

}
