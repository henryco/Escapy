package com.game;

import com.badlogic.gdx.Game;
import com.game.render.camera.EscapyGdxCamera;
import com.game.screens.userState.EscapyStateContainer;
import com.game.userState.settings.GameSettings;
import net.henryco.struct.container.tree.StructNode;
import net.henryco.struct.container.tree.StructTree;

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

	public final int SCREEN_WIDTH;
	public final int SCREEN_HEIGHT;
	public final int SCREEN_DEFAULT_WIDTH;
	public final int SCREEN_DEFAULT_HEIGHT;
	public final float SCREEN_SCALE;

	/**
	 * Instantiates a new game enter.
	 *
	 * @param settings the settings
	 */
	public GameEnter(GameSettings settings) {
		this.settings = settings;
		StructNode screenNode
				= new StructTree("data/config/LaunchCFG.struct").
				mainNode.getPath("launcher", "game", "screen", "size");

		this.SCREEN_SCALE = GameSettings.scaleRatio();

		this.SCREEN_DEFAULT_WIDTH = Integer.parseInt(screenNode.getPrimitive("0", "x", "w", "width"));
		this.SCREEN_DEFAULT_HEIGHT = Integer.parseInt(screenNode.getPrimitive("1", "y", "h", "height"));

		this.SCREEN_WIDTH = (int) (SCREEN_DEFAULT_WIDTH * SCREEN_SCALE);
		this.SCREEN_HEIGHT = (int) (SCREEN_DEFAULT_HEIGHT * SCREEN_SCALE);

	}

	@Override
	public void create() {

		this.escapyCamera = new EscapyGdxCamera(SCREEN_DEFAULT_WIDTH, SCREEN_DEFAULT_HEIGHT);
		this.statesContainer = new EscapyStateContainer(this, escapyCamera);
		this.statesContainer.getUpdLoopedQueue().setSleepTime(8);

		super.setScreen(statesContainer.getLoadingScreen());
		resize(SCREEN_DEFAULT_WIDTH, SCREEN_DEFAULT_HEIGHT);
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
