package com.game.utils.arrContainer;

/**
 * @author Henry on 19/10/16.
 */
public class EscapyNamedArray <T> extends EscapyArray <T> {


	private String[] names;
	private String[] namesCopy;

	public EscapyNamedArray(Class<T> obClass) {
		super(obClass);
		this.names = new String[0];
	}

	@Override
	public EscapyArray addSource(T ob) {
		return this.addSource(ob, Integer.toString(ob.hashCode()));
	}

	public EscapyArray addSource(T ob, String name) {

		this.names = addObjToArray(String.class, names, name);
		copyNames();
		return super.addSource(ob);
	}

	public T get(String name) {
		for (int i = 0; i < names.length; i++) if (names[i].equalsIgnoreCase(name)) return container[i];
		return null;
	}

	public String[] getNames() {
		return namesCopy;
	}

	@Override
	public EscapyArray clear() {
		this.names = new String[0];
		copyNames();
		return super.clear();
	}

	@Override
	public EscapyArray removeLast() {
		names = removeLast(String.class, names);
		copyNames();
		return super.removeLast();
	}

	private void copyNames(){
		namesCopy = new String[names.length];
		System.arraycopy(names, 0, namesCopy, 0, namesCopy.length);
	}
}
