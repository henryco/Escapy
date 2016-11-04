package com.game.render.fbo.psProcess.lights.type;


/**
 * @author Henry on 04/11/16.
 */
@FunctionalInterface
public interface EscapyPeriodicAction <T> {

	T action(float delta, T obj);
}
