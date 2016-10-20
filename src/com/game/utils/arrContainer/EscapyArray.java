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
		container = addObjToArray(obClass, container, ob);
		return this;
	}

	public EscapyArray removeLast(){
		container = removeLast(obClass, container);
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

	public T getLast(){
		return container[container.length - 1];
	}

	public EscapyArray clear() {
		container = instanceArray(obClass, 0);
		return this;
	}

	@SuppressWarnings("unchecked")
	private static <U> U[] instanceArray(Class<U> obClass, int length) {
		return (U[]) Array.newInstance(obClass, length);
	}

	protected static <U> U[] addObjToArray(Class<U> obClass, U[] superArray, U ob) {
		U[] tmp = instanceArray(obClass, superArray.length + 1);
		System.arraycopy(superArray, 0, tmp, 0, superArray.length);
		tmp[tmp.length - 1] = ob;
		return tmp;
	}

	protected static <U> U[] removeLast(Class<U> obClass, U[] superArray){
		U[] tmp = instanceArray(obClass, superArray.length - 1);
		System.arraycopy(superArray, 0, tmp, 0, tmp.length);
		return tmp;
	}
}
