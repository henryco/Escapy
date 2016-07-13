package com.x.game.animator;

public class EscapyAnimatorBase extends EscapyAnimatorSuper {

	private Thread Objects_THREAD;
	private Thread Characters_THREAD;

	private boolean ended;

	protected EscapyAnimatorBase() {
		return;
	}

	public static EscapyAnimatorBase createAnimator() {
		return new EscapyAnimatorBase();
	}

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

	public EscapyAnimatorBase startAnimator() {
		Objects_THREAD.start();
		Characters_THREAD.start();
		return this;
	}

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

	public void endAnimator() {
		closeAnimator();
		animatedList.clear();
		indexOfList.clear();
		System.gc();
	}

	public boolean isEnded() {
		return ended;
	}

}
