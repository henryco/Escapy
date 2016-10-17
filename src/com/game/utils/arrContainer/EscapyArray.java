package com.game.utils.arrContainer;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author Henry on 02/10/16.
 */
public abstract class EscapyArray<T> {

	public T[] container;
	private Class<T> obClass;


	public EscapyArray(Class<T> obClass) {
		this.obClass = obClass;
		this.container = instanceArray(obClass, 0);
	}

	public EscapyArray addSource(T ob) {

		T[] tmp = instanceArray(obClass, container.length + 1);
		System.arraycopy(container, 0, tmp, 0, container.length);
		tmp[tmp.length - 1] = ob;
		container = tmp;
		return this;
	}

	public EscapyArray forEach(Consumer<T> consumer) {
		Arrays.stream(container).forEach(consumer);
		return this;
	}

	public int size(){
		return container.length;
	}

	public T get(int index) {
		if (container.length > index) return container[index];
		return null;
	}

	public EscapyArray clear() {
		container = instanceArray(obClass, 0);
		return this;
	}

	@SuppressWarnings("unchecked")
	private T[] instanceArray(Class<T> obClass, int length) {
		return (T[]) Array.newInstance(obClass, length);
	}
}
