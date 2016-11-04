package com.game.utils.periodic;


/**
 * @author Henry on 04/11/16.
 */
@FunctionalInterface
public interface EscapyPeriodicAction <T> {

	T action(float delta, float actMax, T obj);
}
