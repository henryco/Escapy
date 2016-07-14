package com.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.userState.settings.GameSettings;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyScreenState.
 */
public abstract class EscapyScreenState implements Screen {

	/** The game state. */
	protected GameEnter gameState;
	
	/** The settings. */
	protected GameSettings settings;
	
	/** The game. */
	protected Game game;
	
	/** The initialization ended. */
	protected boolean initializationEnded;
	
	/** The screen width. */
	protected final int SCREEN_WIDTH;
	
	/** The screen height. */
	protected final int SCREEN_HEIGHT;
	
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
		
		this.SCREEN_WIDTH = settings.getFrameWIDHT();
		this.SCREEN_HEIGHT = settings.getFrameHEIGHT();
		
		this.initializationEnded = false;
	}

}
