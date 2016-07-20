package com.game.utils.observ;

public interface SimpleObserver<T> {
	
	public abstract void stateUpdated(T state);
}
