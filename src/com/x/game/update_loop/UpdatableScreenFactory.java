package com.x.game.update_loop;

import com.x.game.GameEnter;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.screens.states.EscapyGameScreen;
import com.x.game.screens.states.EscapySplashScreen;

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
