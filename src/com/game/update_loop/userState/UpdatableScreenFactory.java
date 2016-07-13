package com.game.update_loop.userState;

import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.screens.userState.EscapyGameScreen;
import com.game.screens.userState.EscapySplashScreen;
import com.game.update_loop.UpdatableScreen;

public class UpdatableScreenFactory {

	public static UpdatableScreen SplashScreen(EscapyGdxCamera escapyCamera, GameEnter gameState){
		return new UpdatableScreen(new EscapySplashScreen(escapyCamera, gameState));
	}
	
	public static UpdatableScreen GameScreen(EscapyGdxCamera escapyCamera, GameEnter gameState){
		return new UpdatableScreen(new EscapyGameScreen(escapyCamera, gameState).initState());
	}
	

}
