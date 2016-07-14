package com.game.utils.simpleMemento;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleStateHolder.
 *
 * @param <T>
 *            the generic type
 */
public class SimpleStateHolder<T> implements SimpleStateSaver<T> {

	private T objectState;

	/* (non-Javadoc)
	 * @see com.game.utils.simpleMemento.SimpleStateSaver#setObjectState(java.lang.Object)
	 */
	@Override
	public void setObjectState(T state) 
	{
		this.objectState = state;
	}

	/* (non-Javadoc)
	 * @see com.game.utils.simpleMemento.SimpleStateSaver#getObjectState()
	 */
	@Override
	public T getObjectState() {
		return objectState;
	}

	/* (non-Javadoc)
	 * @see com.game.utils.simpleMemento.SimpleStateSaver#saveObjectState()
	 */
	@Override
	public SimpleMemento<T> saveObjectState() {
		return new SimpleMemento<T>(objectState);
	}

	/* (non-Javadoc)
	 * @see com.game.utils.simpleMemento.SimpleStateSaver#restoreObjectState(com.game.utils.simpleMemento.SimpleMemento)
	 */
	@Override
	public void restoreObjectState(SimpleMemento<T> memento)
	{
		this.objectState = memento.getObjectState();
	}

}
