package com.game.screens.userState;

import com.badlogic.gdx.Screen;
import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.update_loop.UpdatableScreen;
import com.game.update_loop.UpdateLoopQueue;
import com.game.update_loop.userState.UpdatableScreenFactory;
import com.game.utils.simpleMemento.SImpleCaretaker;
import com.game.utils.simpleMemento.SimpleStateHolder;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyStateContainer.
 */
public class EscapyStateContainer {

	
	private UpdatableScreen splashScreen;
	private UpdatableScreen gameScreen;
	private Screen loadingScreen;
	private Screen menuScreen;
	
	private SimpleStateHolder<UpdatableScreen> gameStateHolder;
	private SImpleCaretaker<UpdatableScreen> gameSavedState;
	
	private UpdateLoopQueue updLoopedQueue;
	
	/**
	 * Instantiates a new escapy state container.
	 *
	 * @param gameState
	 *            the game state
	 * @param escapyCamera
	 *            the escapy camera
	 */
	public EscapyStateContainer(GameEnter gameState, EscapyGdxCamera escapyCamera) 
	{
		this.updLoopedQueue = new UpdateLoopQueue();
		
		this.splashScreen = UpdatableScreenFactory.SplashScreen(escapyCamera, gameState);
		this.loadingScreen = new EscapyLoadingScreen(escapyCamera, gameState);
		this.menuScreen = new EscapyMainMenuScreen(escapyCamera, gameState);
		
		this.gameStateHolder = new SimpleStateHolder<>();
		this.gameSavedState = new SImpleCaretaker<>();
		
		this.updLoopedQueue.startUpdateLoop();
	}
	
	/**
	 * Gets the upd looped queue.
	 *
	 * @return the upd looped queue
	 */
	public UpdateLoopQueue getUpdLoopedQueue() {
		return updLoopedQueue;
	}


	/**
	 * Sets the new location.
	 *
	 * @param newLocScreen
	 *            the new loc screen
	 * @param bufferLastLoc
	 *            the buffer last loc
	 */
	public void setNewLocation(UpdatableScreen newLocScreen, boolean bufferLastLoc)
	{
		if (newLocScreen != null)
		{
			if (gameScreen != null && bufferLastLoc)
			{
				gameStateHolder.setObjectState(gameScreen);
				gameSavedState.setMemento(gameStateHolder.saveObjectState());
				updLoopedQueue.removeFromUpdQueueLast();
			}
			gameScreen = newLocScreen;
			updLoopedQueue.addToUpdQueue(gameScreen);
		}
	}
	
	/**
	 * Gets the last location.
	 *
	 * @return the last location
	 */
	public UpdatableScreen getLastLocation()
	{
		if (gameSavedState.getMemento() != null)
		{
			gameStateHolder.restoreObjectState(gameSavedState.getMemento());
			return gameStateHolder.getObjectState();
		}
		return null;
	}
	
	
	
	
	
	
	/**
	 * Gets the splash screen.
	 *
	 * @return the splash screen
	 */
	public UpdatableScreen getSplashScreen() {
		return splashScreen;
	}

	/**
	 * Sets the splash screen.
	 *
	 * @param splashScreen
	 *            the new splash screen
	 */
	public void setSplashScreen(UpdatableScreen splashScreen) {
		this.splashScreen = splashScreen;
	}

	/**
	 * Gets the menu screen.
	 *
	 * @return the menu screen
	 */
	public Screen getMenuScreen() {
		return menuScreen;
	}

	/**
	 * Sets the menu screen.
	 *
	 * @param menuScreen
	 *            the new menu screen
	 */
	public void setMenuScreen(Screen menuScreen) {
		this.menuScreen = menuScreen;
	}

	/**
	 * Gets the game screen.
	 *
	 * @return the game screen
	 */
	public UpdatableScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Gets the loading screen.
	 *
	 * @return the loading screen
	 */
	public Screen getLoadingScreen() {
		return loadingScreen;
	}

	/**
	 * Sets the loading screen.
	 *
	 * @param loadingScreen
	 *            the new loading screen
	 */
	public void setLoadingScreen(Screen loadingScreen) {
		this.loadingScreen = loadingScreen;
	}

	

}
