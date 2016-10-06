package com.game.utils.arrContainer;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author Henry on 02/10/16.
 */
public abstract class EscapyArrContainer<T> {

	public T[] container;
	private Class<T> obClass;


	public EscapyArrContainer(Class<T> obClass) {
		this.obClass = obClass;
		this.container = instanceArray(obClass, 0);
	}

	public EscapyArrContainer addSource(T ob) {

		T[] tmp = instanceArray(obClass, container.length + 1);
		System.arraycopy(container, 0, tmp, 0, container.length);
		tmp[tmp.length - 1] = ob;
		container = tmp;
		return this;
	}

	public EscapyArrContainer forEach(Consumer<T> consumer) {
		Arrays.stream(container).forEach(consumer);
		return this;
	}

	public int size(){
		return container.length;
	}

	public EscapyArrContainer clear() {
		container = instanceArray(obClass, 0);
		return this;
	}

	@SuppressWarnings("unchecked")
	private T[] instanceArray(Class<T> obClass, int length) {
		return (T[]) Array.newInstance(obClass, length);
	}
}
