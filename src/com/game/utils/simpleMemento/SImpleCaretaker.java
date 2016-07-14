package com.game.utils.simpleMemento;

// TODO: Auto-generated Javadoc
/**
 * The Class SImpleCaretaker.
 *
 * @param <T>
 *            the generic type
 */
public class SImpleCaretaker<T> {

	private SimpleMemento<T> memento;
	
	/**
	 * Gets the memento.
	 *
	 * @return the memento
	 */
	public SimpleMemento<T> getMemento()
	{
		return memento;
	}
	
	/**
	 * Sets the memento.
	 *
	 * @param memento
	 *            the new memento
	 */
	public void setMemento(SimpleMemento<T> memento)
	{
		this.memento = memento;
	}

}
