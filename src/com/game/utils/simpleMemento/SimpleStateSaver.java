package com.game.utils.simpleMemento;

// TODO: Auto-generated Javadoc
/**
 * The Interface SimpleStateSaver.
 *
 * @param <T>
 *            the generic type
 */
public interface SimpleStateSaver<T> {

	/**
	 * Sets the object state.
	 *
	 * @param state
	 *            the new object state
	 */
	public void setObjectState(T state);

	/**
	 * Gets the object state.
	 *
	 * @return the object state
	 */
	public T getObjectState();

	/**
	 * Save object state.
	 *
	 * @return the simple memento
	 */
	public SimpleMemento<T> saveObjectState();

	/**
	 * Restore object state.
	 *
	 * @param memento
	 *            the memento
	 */
	public void restoreObjectState(SimpleMemento<T> memento);
}
