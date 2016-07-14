package com.game.utils.simpleMemento;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleMemento.
 *
 * @param <T>
 *            the generic type
 */
public class SimpleMemento<T> {

	private final T objectState;

	/**
	 * Instantiates a new simple memento.
	 *
	 * @param objectState
	 *            the object state
	 */
	public SimpleMemento(T objectState) {
		this.objectState = objectState;
	}

	/**
	 * Gets the object state.
	 *
	 * @return the object state
	 */
	public T getObjectState() {
		return objectState;
	}
}
