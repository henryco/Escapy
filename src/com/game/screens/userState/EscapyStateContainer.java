package com.game.screens.userState;

import com.badlogic.gdx.Screen;
import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.update_loop.UpdatableScreen;
import com.game.update_loop.UpdateLoopQueue;
import com.game.update_loop.userState.UpdatableScreenFactory;
import com.game.utils.simpleMemento.SImpleCaretaker;
import com.game.utils.simpleMemento.SimpleStateHolder;

public class EscapyStateContainer {

	
	private UpdatableScreen splashScreen;
	private UpdatableScreen gameScreen;
	private Screen loadingScreen;
	private Screen menuScreen;
	
	private SimpleStateHolder<UpdatableScreen> gameStateHolder;
	private SImpleCaretaker<UpdatableScreen> gameSavedState;
	
	private UpdateLoopQueue updLoopedQueue;
	
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
	
	public UpdateLoopQueue getUpdLoopedQueue() {
		return updLoopedQueue;
	}


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
	
	public UpdatableScreen getLastLocation()
	{
		if (gameSavedState.getMemento() != null)
		{
			gameStateHolder.restoreObjectState(gameSavedState.getMemento());
			return gameStateHolder.getObjectState();
		}
		return null;
	}
	
	
	
	
	
	
	public UpdatableScreen getSplashScreen() {
		return splashScreen;
	}

	public void setSplashScreen(UpdatableScreen splashScreen) {
		this.splashScreen = splashScreen;
	}

	public Screen getMenuScreen() {
		return menuScreen;
	}

	public void setMenuScreen(Screen menuScreen) {
		this.menuScreen = menuScreen;
	}

	public UpdatableScreen getGameScreen() {
		return gameScreen;
	}

	public Screen getLoadingScreen() {
		return loadingScreen;
	}

	public void setLoadingScreen(Screen loadingScreen) {
		this.loadingScreen = loadingScreen;
	}

	

}
