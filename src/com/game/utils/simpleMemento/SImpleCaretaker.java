package com.game.utils.simpleMemento;

public class SImpleCaretaker<T> {

	private SimpleMemento<T> memento;
	
	public SimpleMemento<T> getMemento()
	{
		return memento;
	}
	public void setMemento(SimpleMemento<T> memento)
	{
		this.memento = memento;
	}

}
