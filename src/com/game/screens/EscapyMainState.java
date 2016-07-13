package com.game.screens;

import com.badlogic.gdx.Screen;
import com.game.render.EscapyGdxCamera;

public interface EscapyMainState 
{

	public Screen initState();
	
	public void renderGameObjects(EscapyGdxCamera escapyCamera);
}
