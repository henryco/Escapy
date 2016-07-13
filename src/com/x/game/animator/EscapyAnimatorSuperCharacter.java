package com.x.game.animator;

public abstract class EscapyAnimatorSuperCharacter extends EscapyAnimatorSuper {

	public EscapyAnimatorSuperCharacter() {

	}

	public void addAnimatedCharacter(EscapyAnimatorCharacter character) {
		addAnimated(character);
	}

	public void removeEscapyCharacterAnimator(EscapyAnimatorCharacter character) {
		removeAnimated(character);
	}

	public void initCharacterAnimator(EscapyAnimatorCharacter character) {
		launchAnimated(character);
	}

	public void interruptCharacterAnimation(EscapyAnimatorCharacter character) {
		interruptAnimated(character);
	}

	public void startCharacterAnimation(EscapyAnimatorCharacter character) {
		interruptAnimated(character);
		launchAnimated(character);
	}
}
