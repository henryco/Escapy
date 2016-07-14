package com.game.animator;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyAnimatorSuperObject.
 */
public abstract class EscapyAnimatorSuperObject extends EscapyAnimatorSuper {

	/**
	 * Instantiates a new escapy animator super object.
	 */
	public EscapyAnimatorSuperObject() {

	}

	/**
	 * Inits the object animator.
	 *
	 * @param object the object
	 */
	public void initObjectAnimator(EscapyAnimatorObject object) {
		launchAnimated(object);
	}

	/**
	 * Adds the escapy object animator.
	 *
	 * @param object the object
	 */
	public void addEscapyObjectAnimator(EscapyAnimatorObject object) {
		addAnimated(object);
	}

	/**
	 * Removes the escapy object animator.
	 *
	 * @param object the object
	 */
	public void removeEscapyObjectAnimator(EscapyAnimatorObject object) {
		removeAnimated(object);
	}

	/**
	 * Interrupt object animation.
	 *
	 * @param object the object
	 */
	public void interruptObjectAnimation(EscapyAnimatorObject object) {
		interruptAnimated(object);
	}

	/**
	 * Start object animator.
	 *
	 * @param object the object
	 */
	public void startObjectAnimator(EscapyAnimatorObject object) {
		interruptAnimated(object);
		launchAnimated(object);
	}
}
