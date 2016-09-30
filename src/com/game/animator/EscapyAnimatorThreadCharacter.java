package com.game.animator;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyAnimatorThreadCharacter.
 */
public class EscapyAnimatorThreadCharacter extends EscapyAnimatorSuper implements Runnable {

	/**
	 * Instantiates a new escapy animator thread program.
	 */
	public EscapyAnimatorThreadCharacter() {
		/** VOID **/
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while (!stopItNow[1]) {
			while (!finished[1]) {
				// PlayerControl.keyboard_upd();
				for (int i = 0; i < animatedList.size(); i++) {
					if (animatedList.get(i) instanceof EscapyAnimatorCharacter) {
						((EscapyAnimatorCharacter) animatedList.get(i))
								.InterruptAnimator((EscapyAnimatorCharacter) animatedList.get(i));
						if (indexOfList.get(i).intValue() == EscapyAnimatorSuper.ACTIVE) {
							((EscapyAnimatorCharacter) animatedList.get(i)).defineStandAnimation();
							((EscapyAnimatorCharacter) animatedList.get(i)).defineMovAnimation();
							((EscapyAnimatorCharacter) animatedList.get(i)).defineRunAnimation();
							((EscapyAnimatorCharacter) animatedList.get(i)).defineJumpAnimation();
							((EscapyAnimatorCharacter) animatedList.get(i)).defineInteractAnimation();
							((EscapyAnimatorCharacter) animatedList.get(i)).defineOtherAnimation();
						}
					}
				}
				if (stopItNow[0] && !stopItNow[1])
					Thread.currentThread().interrupt();
				try {
					if (!stopItNow[0] && !stopItNow[1]) {
						// Thread.sleep(1);
						Thread.sleep(0, 200000);
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
