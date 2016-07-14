package com.game.animator;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyAnimatorSuper.
 */
public abstract class EscapyAnimatorSuper implements EscapyAnimator {

	/** The finished. */
	protected static volatile boolean[] finished = new boolean[] { false, false };
	
	/** The stop it now. */
	protected static volatile boolean[] stopItNow = new boolean[] { false, false };
	
	/** The animated list. */
	protected static ArrayList<EscapyAnimator> animatedList = new ArrayList<>();
	
	/** The index of list. */
	protected static ArrayList<Integer> indexOfList = new ArrayList<>();

	/**
	 * Instantiates a new escapy animator super.
	 */
	public EscapyAnimatorSuper() {

	}

	/**
	 * Adds the animated.
	 *
	 * @param object the object
	 */
	public void addAnimated(EscapyAnimator object) {
		animatedList.add(object);
		indexOfList.add(new Integer(EscapyAnimator.INTERRUPTED));
		System.gc();
	}

	/**
	 * Removes the animated.
	 *
	 * @param object the object
	 */
	public void removeAnimated(EscapyAnimator object) {
		animatedList.remove(object);
		indexOfList.remove(animatedList.indexOf(object));
		System.gc();
	}

	/**
	 * Launch animated.
	 *
	 * @param object the object
	 */
	public void launchAnimated(EscapyAnimator object) {
		indexOfList.set(animatedList.indexOf(object), EscapyAnimator.ACTIVE);
		System.gc();
	}

	/**
	 * Interrupt animated.
	 *
	 * @param object the object
	 */
	public void interruptAnimated(EscapyAnimator object) {
		indexOfList.set(animatedList.indexOf(object), EscapyAnimator.INTERRUPTED);
		System.gc();
	}

}
