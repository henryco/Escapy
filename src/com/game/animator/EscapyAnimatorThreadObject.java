package com.game.animator;

public class EscapyAnimatorThreadObject extends EscapyAnimatorSuper implements Runnable {

	public EscapyAnimatorThreadObject() {
		/** VOID **/
	}

	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while (!stopItNow[0]) {
			while (!finished[0]) {
				for (int i = 0; i < animatedList.size(); i++) {
					if (animatedList.get(i) instanceof EscapyAnimatorObject) {
						((EscapyAnimatorObject) animatedList.get(i))
								.InterruptAnimator((EscapyAnimatorObject) animatedList.get(i));
						if (indexOfList.get(i).intValue() == EscapyAnimatorSuper.ACTIVE)
							((EscapyAnimatorObject) animatedList.get(i)).defineAnimation();
					}
				}
				if (stopItNow[0] && !stopItNow[1])
					Thread.currentThread().interrupt();
				try {
					if (!stopItNow[0] && !stopItNow[1]) {
						Thread.sleep(5);
					} else {
						Thread.currentThread().interrupt();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Thread.currentThread().interrupt();
	}

}