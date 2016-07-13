package com.game.utils.simpleMemento;

public interface SimpleStateSaver<T> {

	public void setObjectState(T state);

	public T getObjectState();

	public SimpleMemento<T> saveObjectState();

	public void restoreObjectState(SimpleMemento<T> memento);
}
