package com.x.game.screens;

import com.badlogic.gdx.Screen;
import com.x.game.GameEnter;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.screens.states.EscapyLoadingScreen;
import com.x.game.screens.states.EscapyMainMenuScreen;
import com.x.game.update_loop.UpdatableScreen;
import com.x.game.update_loop.UpdatableScreenFactory;
import com.x.game.update_loop.UpdateLoopQueue;
import com.x.game.utils.simpleMemento.SImpleCaretaker;
import com.x.game.utils.simpleMemento.SimpleStateHolder;

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
