package com.game.animator;

public abstract class EscapyAnimatorSuperObject extends EscapyAnimatorSuper {

	public EscapyAnimatorSuperObject() {

	}

	public void initObjectAnimator(EscapyAnimatorObject object) {
		launchAnimated(object);
	}

	public void addEscapyObjectAnimator(EscapyAnimatorObject object) {
		addAnimated(object);
	}

	public void removeEscapyObjectAnimator(EscapyAnimatorObject object) {
		removeAnimated(object);
	}

	public void interruptObjectAnimation(EscapyAnimatorObject object) {
		interruptAnimated(object);
	}

	public void startObjectAnimator(EscapyAnimatorObject object) {
		interruptAnimated(object);
		launchAnimated(object);
	}
}
