package com.game.utils.absContainer;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyContainer.
 *
 * @param <T>
 *            the generic type
 */
public interface EscapyContainer <T> {
	
	/**
	 * Adds the source.
	 *
	 * @param source
	 *            the source
	 * @return the int
	 */
	int addSource(T source);
	
	/**
	 * Gets the source by ID.
	 *
	 * @param ID
	 *            the id
	 * @return the source by ID
	 */
	T getSourceByID(int ID);
	
	/**
	 * Removes the source by ID.
	 *
	 * @param ID
	 *            the id
	 * @return true, if successful
	 */
	boolean removeSourceByID(int ID);
	
	/**
	 * Removes the source.
	 *
	 * @param source
	 *            the source
	 * @return true, if successful
	 */
	boolean removeSource(T source);
}
