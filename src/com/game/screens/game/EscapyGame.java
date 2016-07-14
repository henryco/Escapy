package com.game.screens.game;

import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.screens.EscapyMainState;
import com.game.screens.EscapyScreenState;
import com.game.update_loop.Updatable;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyGame.
 */
public abstract class EscapyGame extends EscapyScreenState implements Updatable, EscapyMainState {

	/**
	 * Instantiates a new escapy game.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 * @param gameState
	 *            the game state
	 */
	public EscapyGame(EscapyGdxCamera escapyCamera, GameEnter gameState) {
		super(escapyCamera, gameState);
		
	} 

	//TODO 

}
