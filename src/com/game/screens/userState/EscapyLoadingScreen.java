package com.game.screens.userState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.GameEnter;
import com.game.render.EscapyGdxCamera;
import com.game.screens.EscapyScreenState;
import com.game.update_loop.userState.UpdatableScreenFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyLoadingScreen.
 */
public class EscapyLoadingScreen extends EscapyScreenState implements Runnable {

	private SpriteBatch batcher;
	private Sprite splashSprite;
	private volatile boolean loaded;

	/**
	 * Instantiates a new escapy loading screen.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 * @param gameState
	 *            the game state
	 */
	public EscapyLoadingScreen(EscapyGdxCamera escapyCamera, GameEnter gameState) {
		super(escapyCamera, gameState);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		this.batcher = new SpriteBatch();
		this.splashSprite = new Sprite(new Texture("img\\splash\\BgLogo.png"));
		this.splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.splashSprite.flip(false, true);

		this.loaded = false;

		Thread loadThread = new Thread(this);
		loadThread.start();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batcher.setProjectionMatrix(super.escapyCamera.getCamera().combined);
		batcher.begin();
		splashSprite.draw(batcher);
		batcher.end();

		if (loaded) {
			super.gameState.setScreen(gameState.getStatesContainer().getGameScreen().getScreen());
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		EscapyLoadingScreen ldscr = this;
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {

				ldscr.gameState.getStatesContainer()
						.setNewLocation(UpdatableScreenFactory.GameScreen(ldscr.escapyCamera, ldscr.gameState), true);
				ldscr.loaded = true;
			}
		});
		Thread.currentThread().interrupt();
	}

}
