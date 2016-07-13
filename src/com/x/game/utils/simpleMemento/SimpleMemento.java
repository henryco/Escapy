package com.x.game.utils.simpleMemento;

public class SimpleMemento<T> {

	private final T objectState;

	public SimpleMemento(T objectState) {
		this.objectState = objectState;
	}

	public T getObjectState() {
		return objectState;
	}
}
