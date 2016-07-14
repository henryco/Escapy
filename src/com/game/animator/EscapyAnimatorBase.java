package com.game.animator;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyAnimatorBase.
 */
public class EscapyAnimatorBase extends EscapyAnimatorSuper {

	/** The Objects THREAD. */
	private Thread Objects_THREAD;
	
	/** The Characters THREAD. */
	private Thread Characters_THREAD;

	/** The ended. */
	private boolean ended;

	/**
	 * Instantiates a new escapy animator base.
	 */
	protected EscapyAnimatorBase() {
		return;
	}

	/**
	 * Creates the animator.
	 *
	 * @return the escapy animator base
	 */
	public static EscapyAnimatorBase createAnimator() {
		return new EscapyAnimatorBase();
	}

	/**
	 * Inits the animator.
	 *
	 * @return the escapy animator base
	 */
	public EscapyAnimatorBase initAnimator() {
		Objects_THREAD = new Thread(new EscapyAnimatorThreadObject());
		Characters_THREAD = new Thread(new EscapyAnimatorThreadCharacter());

		finished[0] = false;
		finished[1] = false;
		stopItNow[0] = false;
		stopItNow[1] = false;
		ended = false;
		return this;
	}

	/**
	 * Start animator.
	 *
	 * @return the escapy animator base
	 */
	public EscapyAnimatorBase startAnimator() {
		Objects_THREAD.start();
		Characters_THREAD.start();
		return this;
	}

	/**
	 * Close animator.
	 *
	 * @return the escapy animator base
	 */
	public EscapyAnimatorBase closeAnimator() {
		if (!ended) {
			stopItNow[0] = true;
			stopItNow[1] = true;
			finished[0] = true;
			finished[1] = true;
			while (!Objects_THREAD.isInterrupted() && !Characters_THREAD.isInterrupted()) {
			}

			ended = true;
		}
		return this;
	}

	/**
	 * End animator.
	 */
	public void endAnimator() {
		closeAnimator();
		animatedList.clear();
		indexOfList.clear();
		System.gc();
	}

	/**
	 * Checks if is ended.
	 *
	 * @return true, if is ended
	 */
	public boolean isEnded() {
		return ended;
	}

}
