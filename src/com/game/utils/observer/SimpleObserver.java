package com.game.utils.observer;

public interface SimpleObserver<T> {
	
	void stateUpdated(T state);
}
