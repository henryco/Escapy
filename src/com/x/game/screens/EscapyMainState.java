package com.x.game.screens;

import com.badlogic.gdx.Screen;
import com.x.game.render.EscapyGdxCamera;

public interface EscapyMainState 
{

	public Screen initState();
	
	public void renderGameObjects(EscapyGdxCamera escapyCamera);
}
