package com.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.settings.GameSettings;

public abstract class EscapyScreenState implements Screen {

	protected GameEnter gameState;
	protected GameSettings settings;
	protected Game game;
	
	protected boolean initializationEnded;
	
	protected final int SCREEN_WIDTH;
	protected final int SCREEN_HEIGHT;
	
	protected EscapyGdxCamera escapyCamera;
	
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
