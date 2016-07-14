package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.render.EscapyGdxCamera;
import com.game.screens.userState.EscapyStateContainer;
import com.game.userState.settings.GameSettings;

// TODO: Auto-generated Javadoc
/**
 * The Class GameEnter.
 */
public class GameEnter extends Game {

	/** The settings. */
	private GameSettings settings;
	
	/** The escapy camera. */
	private EscapyGdxCamera escapyCamera;

	/** The states container. */
	private EscapyStateContainer statesContainer;

	/**
	 * Instantiates a new game enter.
	 *
	 * @param settings the settings
	 */
	public GameEnter(GameSettings settings) {
		this.settings = settings;
		return;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
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

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#render()
	 */
	@Override
	public void render() {
		super.render();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#pause()
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#resume()
	 */
	@Override
	public void resume() {
		super.resume();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Game#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/**
	 * Gets the states container.
	 *
	 * @return the states container
	 */
	public EscapyStateContainer getStatesContainer() {
		return statesContainer;
	}

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public GameSettings getSettings() {
		return settings;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return this;
	}

	/**
	 * Gets the escapy camera.
	 *
	 * @return the escapy camera
	 */
	public EscapyGdxCamera getEscapyCamera() {
		return escapyCamera;
	}

	/**
	 * Sets the escapy camera.
	 *
	 * @param escapyCamera the new escapy camera
	 */
	public void setEscapyCamera(EscapyGdxCamera escapyCamera) {
		this.escapyCamera = escapyCamera;
	}

}
