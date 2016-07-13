package com.x.game.screens.states;

import com.x.game.GameEnter;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.screens.EscapyScreenState;

public class EscapySplashScreen extends EscapyScreenState implements Runnable {

	public EscapySplashScreen(EscapyGdxCamera escapyCamera, GameEnter game) {
		super(escapyCamera, game);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void run() {
		boolean ins = false;
		while (!ins)
			System.out.println("test");
	}

}
