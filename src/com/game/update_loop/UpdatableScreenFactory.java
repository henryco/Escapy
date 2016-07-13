package com.game.update_loop;

import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.screens.states.EscapyGameScreen;
import com.game.screens.states.EscapySplashScreen;

public class UpdatableScreenFactory {

	public static UpdatableScreen SplashScreen(EscapyGdxCamera escapyCamera, GameEnter gameState)
	{
		return new UpdatableScreen(new EscapySplashScreen(escapyCamera, gameState));
	}
	
	public static UpdatableScreen GameScreen(EscapyGdxCamera escapyCamera, GameEnter gameState)
	{
		return new UpdatableScreen(new EscapyGameScreen(escapyCamera, gameState).initState());
	}
	

}
