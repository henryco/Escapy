package com.game.screens.game;

import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.screens.EscapyMainState;
import com.game.screens.EscapyScreenState;
import com.game.update_loop.Updatable;

public abstract class EscapyGame extends EscapyScreenState implements Updatable, EscapyMainState {

	public EscapyGame(EscapyGdxCamera escapyCamera, GameEnter gameState) {
		super(escapyCamera, gameState);
		
	} 

	//TODO 

}
