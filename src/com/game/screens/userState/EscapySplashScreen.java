package com.game.screens.userState;

import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.screens.EscapyScreenState;

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
