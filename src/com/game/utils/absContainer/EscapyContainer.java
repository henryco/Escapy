package com.game.utils.absContainer;

public interface EscapyContainer <T> {
	
	public abstract int addSource(T source);
	public abstract T getSourceByID(int ID);
	public abstract boolean removeSourceByID(int ID);
	public abstract boolean removeSource(T source);
}
