package com.x.game.utils.proxyContainer;

import com.x.game.utils.simpleMemento.SImpleCaretaker;
import com.x.game.utils.simpleMemento.SimpleStateHolder;

public abstract class EscapyProxy <T, U> {

	protected T trueState;
	protected U holder;
	protected int id;
	
	private SimpleStateHolder<T> trueStateHolder = new SimpleStateHolder<>();
	private SImpleCaretaker<T> trueStateCarer = new SImpleCaretaker<>();
	
	public EscapyProxy() {
	}
	
	public EscapyProxy(int id, U holder) {
		this.holder = holder;
		this.id = id;
	}
	
	public abstract EscapyProxy<T, U> hold();
	public abstract EscapyProxy<T, U> apply();
	
	public	T source() {
		if (trueStateHolder.getObjectState() == null)
			return null;
		this.snapSource();
		return this.trueStateHolder.getObjectState();
	}
	
	public EscapyProxy<T, U> snapSource() {
		this.trueStateCarer.setMemento(trueStateHolder.saveObjectState());
		return this;
	}
	
	public EscapyProxy<T, U> backState()
	{
		this.trueStateHolder.restoreObjectState(trueStateCarer.getMemento());
		this.setState();
		return this;
	}
	
	protected void setState() {
		this.trueStateHolder.setObjectState(trueState);
	}
	
	
	

}
