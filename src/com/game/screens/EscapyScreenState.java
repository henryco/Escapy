package com.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.GameEnter;
import com.game.render.camera.EscapyGdxCamera;
import com.game.update_loop.Updatable;
import com.game.userState.settings.GameSettings;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyScreenState.
 */
public abstract class EscapyScreenState implements Screen, Updatable {

	/** The game state. */
	protected GameEnter gameState;
	/** The settings. */
	protected GameSettings settings;
	/** The game. */
	protected Game game;
	/** The initialization ended. */
	protected boolean initializationEnded;

	protected final int SCREEN_WIDTH;
	protected final int SCREEN_HEIGHT;
	protected final int SCREEN_DEFAULT_WIDTH;
	protected final int SCREEN_DEFAULT_HEIGHT;

	/** The escapy camera. */
	protected EscapyGdxCamera escapyCamera;

	/**
	 * Instantiates a new escapy screen state.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 * @param gameState
	 *            the game state
	 */
	public EscapyScreenState(EscapyGdxCamera escapyCamera, GameEnter gameState) 
	{
		this.escapyCamera = escapyCamera;
		this.gameState = gameState;
		this.settings = gameState.getSettings();
		this.game = gameState.getGame();

		this.SCREEN_DEFAULT_WIDTH = gameState.SCREEN_DEFAULT_WIDTH;
		this.SCREEN_DEFAULT_HEIGHT = gameState.SCREEN_DEFAULT_HEIGHT;

		this.SCREEN_WIDTH = gameState.SCREEN_WIDTH;
		this.SCREEN_HEIGHT = gameState.SCREEN_HEIGHT;

		System.out.println(SCREEN_WIDTH+ ":: W:H ::" +SCREEN_HEIGHT);

		this.initializationEnded = false;
	}

	public void syncUpDelta(float delta) {
		gameState.getStatesContainer().getUpdLoopedQueue().syncDelta(delta);
	}
}
