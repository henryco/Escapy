package com.game.animator;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyAnimatorSuperCharacter.
 */
public abstract class EscapyAnimatorSuperCharacter extends EscapyAnimatorSuper {

	/**
	 * Instantiates a new escapy animator super program.
	 */
	public EscapyAnimatorSuperCharacter() {

	}

	/**
	 * Adds the animated program.
	 *
	 * @param character the program
	 */
	public void addAnimatedCharacter(EscapyAnimatorCharacter character) {
		addAnimated(character);
	}

	/**
	 * Removes the escapy program animator.
	 *
	 * @param character the program
	 */
	public void removeEscapyCharacterAnimator(EscapyAnimatorCharacter character) {
		removeAnimated(character);
	}

	/**
	 * Inits the program animator.
	 *
	 * @param character the program
	 */
	public void initCharacterAnimator(EscapyAnimatorCharacter character) {
		launchAnimated(character);
	}

	/**
	 * Interrupt program animation.
	 *
	 * @param character the program
	 */
	public void interruptCharacterAnimation(EscapyAnimatorCharacter character) {
		interruptAnimated(character);
	}

	/**
	 * Start program animation.
	 *
	 * @param character the program
	 */
	public void startCharacterAnimation(EscapyAnimatorCharacter character) {
		interruptAnimated(character);
		launchAnimated(character);
	}
}
