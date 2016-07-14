package com.game.animator;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyAnimatorSuperCharacter.
 */
public abstract class EscapyAnimatorSuperCharacter extends EscapyAnimatorSuper {

	/**
	 * Instantiates a new escapy animator super character.
	 */
	public EscapyAnimatorSuperCharacter() {

	}

	/**
	 * Adds the animated character.
	 *
	 * @param character the character
	 */
	public void addAnimatedCharacter(EscapyAnimatorCharacter character) {
		addAnimated(character);
	}

	/**
	 * Removes the escapy character animator.
	 *
	 * @param character the character
	 */
	public void removeEscapyCharacterAnimator(EscapyAnimatorCharacter character) {
		removeAnimated(character);
	}

	/**
	 * Inits the character animator.
	 *
	 * @param character the character
	 */
	public void initCharacterAnimator(EscapyAnimatorCharacter character) {
		launchAnimated(character);
	}

	/**
	 * Interrupt character animation.
	 *
	 * @param character the character
	 */
	public void interruptCharacterAnimation(EscapyAnimatorCharacter character) {
		interruptAnimated(character);
	}

	/**
	 * Start character animation.
	 *
	 * @param character the character
	 */
	public void startCharacterAnimation(EscapyAnimatorCharacter character) {
		interruptAnimated(character);
		launchAnimated(character);
	}
}
