package com.game.update_loop;

import com.badlogic.gdx.Screen;

// TODO: Auto-generated Javadoc

/**
 * The Class UpdatableScreen.
 */
public class UpdatableScreen implements Updatable {

	private Updatable screen;

	/**
	 * Instantiates a new updatable screen.
	 *
	 * @param screen the screen
	 */
	public UpdatableScreen(Screen screen) {
		this.setScreen(screen);
	}


	@Override
	public void update(float delta) {
		screen.update(delta);
	}

	/**
	 * Gets the screen.
	 *
	 * @return the screen
	 */
	public Screen getScreen() {
		return (Screen) screen;
	}

	/**
	 * Sets the screen.
	 *
	 * @param screen the new screen
	 */
	public void setScreen(Screen screen) {
		this.screen = (Updatable) screen;
	}

}
