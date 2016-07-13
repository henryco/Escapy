package com.x.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.x.game.render.EscapyGdxCamera;
import com.x.game.screens.EscapyStateContainer;
import com.x.game.settings.GameSettings;

public class GameEnter extends Game {

	private GameSettings settings;
	private EscapyGdxCamera escapyCamera;

	private EscapyStateContainer statesContainer;

	public GameEnter(GameSettings settings) {
		this.settings = settings;
		return;
	}

	@Override
	public void create() {
		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		this.escapyCamera = new EscapyGdxCamera(camera, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.escapyCamera.setXInterval(0.35f, 0.35f);
		this.statesContainer = new EscapyStateContainer(this, escapyCamera);
		this.statesContainer.getUpdLoopedQueue().setSleepTime(8);

		super.setScreen(statesContainer.getLoadingScreen());

		System.gc();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public EscapyStateContainer getStatesContainer() {
		return statesContainer;
	}

	public GameSettings getSettings() {
		return settings;
	}

	public Game getGame() {
		return this;
	}

	public EscapyGdxCamera getEscapyCamera() {
		return escapyCamera;
	}

	public void setEscapyCamera(EscapyGdxCamera escapyCamera) {
		this.escapyCamera = escapyCamera;
	}

}
