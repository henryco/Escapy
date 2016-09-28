package com.game.update_loop.userState;

import com.game.GameEnter;
import com.game.render.camera.EscapyGdxCamera;
import com.game.screens.userState.EscapyGameScreen;
import com.game.screens.userState.EscapySplashScreen;
import com.game.update_loop.UpdatableScreen;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating UpdatableScreen objects.
 */
public class UpdatableScreenFactory {

	/**
	 * Splash screen.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 * @param gameState
	 *            the game state
	 * @return the updatable screen
	 */
	public static UpdatableScreen SplashScreen(EscapyGdxCamera escapyCamera, GameEnter gameState){
		return new UpdatableScreen(new EscapySplashScreen(escapyCamera, gameState));
	}
	
	/**
	 * Game screen.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 * @param gameState
	 *            the game state
	 * @return the updatable screen
	 */
	public static UpdatableScreen GameScreen(EscapyGdxCamera escapyCamera, GameEnter gameState){
		return new UpdatableScreen(new EscapyGameScreen(escapyCamera, gameState).initState());
	}
	

}
