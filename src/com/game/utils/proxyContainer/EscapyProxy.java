package com.game.utils.proxyContainer;

import com.game.utils.simpleMemento.SImpleCaretaker;
import com.game.utils.simpleMemento.SimpleStateHolder;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyProxy.
 *
 * @param <T>
 *            the generic type
 * @param <U>
 *            the generic type
 */
public abstract class EscapyProxy <T, U> {

	/** The true state. */
	protected T trueState;
	
	/** The holder. */
	protected U holder;
	
	/** The id. */
	protected int id;
	
	private SimpleStateHolder<T> trueStateHolder = new SimpleStateHolder<>();
	private SImpleCaretaker<T> trueStateCarer = new SImpleCaretaker<>();
	
	/**
	 * Instantiates a new escapy proxy.
	 */
	public EscapyProxy() {
	}
	
	/**
	 * Instantiates a new escapy proxy.
	 *
	 * @param id
	 *            the id
	 * @param holder
	 *            the holder
	 */
	public EscapyProxy(int id, U holder) {
		this.holder = holder;
		this.id = id;
	}
	
	/**
	 * Hold.
	 *
	 * @return the escapy proxy
	 */
	public abstract EscapyProxy<T, U> hold();
	
	/**
	 * Apply.
	 *
	 * @return the escapy proxy
	 */
	public abstract EscapyProxy<T, U> apply();
	
	/**
	 * Source.
	 *
	 * @return the t
	 */
	public	T source() {
		if (trueStateHolder.getObjectState() == null)
			return null;
		this.snapSource();
		return this.trueStateHolder.getObjectState();
	}
	
	/**
	 * Snap source.
	 *
	 * @return the escapy proxy
	 */
	public EscapyProxy<T, U> snapSource() {
		this.trueStateCarer.setMemento(trueStateHolder.saveObjectState());
		return this;
	}
	
	/**
	 * Back state.
	 *
	 * @return the escapy proxy
	 */
	public EscapyProxy<T, U> backState()
	{
		this.trueStateHolder.restoreObjectState(trueStateCarer.getMemento());
		this.setState();
		return this;
	}
	
	/**
	 * Sets the state.
	 */
	protected void setState() {
		this.trueStateHolder.setObjectState(trueState);
	}
	
	
	

}
