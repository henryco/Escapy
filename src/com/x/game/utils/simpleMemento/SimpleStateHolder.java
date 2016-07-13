package com.x.game.utils.simpleMemento;

public class SimpleStateHolder<T> implements SimpleStateSaver<T> {

	private T objectState;

	@Override
	public void setObjectState(T state) 
	{
		this.objectState = state;
	}

	@Override
	public T getObjectState() {
		return objectState;
	}

	@Override
	public SimpleMemento<T> saveObjectState() {
		return new SimpleMemento<T>(objectState);
	}

	@Override
	public void restoreObjectState(SimpleMemento<T> memento)
	{
		this.objectState = memento.getObjectState();
	}

}
