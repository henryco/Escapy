package com.game.screens.userState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.GameEnter;
import com.game.phys.PhysExecutor;
import com.game.phys.PhysPolygon;
import com.game.phys.shape.EscapyPolygon;
import com.game.render.camera.EscapyGdxCamera;
import com.game.screens.EscapyScreenState;

// TODO: Auto-generated Javadoc

/**
 * The Class EscapyMainMenuScreen.
 */
public class EscapyMainMenuScreen extends EscapyScreenState {


	private SpriteBatch batcher = new SpriteBatch();
	private EscapyGdxCamera privGdxCamera;

	private PhysExecutor physExecutor;
	private float wait = 0;

	/**
	 * Instantiates a new escapy main menu screen.
	 *
	 * @param escapyCamera the escapy camera
	 * @param game         the game
	 */
	public EscapyMainMenuScreen(EscapyGdxCamera escapyCamera, GameEnter game) {
		super(escapyCamera, game);
		this.privGdxCamera = new EscapyGdxCamera(super.SCREEN_WIDTH, super.SCREEN_HEIGHT);

		this.physExecutor = new PhysExecutor(0.15f);
		physExecutor.meter = 0.5f;
		physExecutor.gravity_a = 5;
		EscapyPolygon poly1 = new EscapyPolygon(50, 100, 150, 100, 150, 300, 50, 300);
		EscapyPolygon poly2 = new EscapyPolygon(50, 600, 900, 600, 900, 700, 50, 700);
		EscapyPolygon poly3 = new EscapyPolygon(0,0, 100,0, 100,100, 0,100);

		physExecutor.addPhysObjectToQueue(new PhysPolygon(poly1, "poly1"));
		physExecutor.addPhysObjectToQueue(new PhysPolygon(poly2, true, "poly2"));
		physExecutor.addPhysObjectToQueue(new PhysPolygon(poly3, true, "poly3").setPosition(300, 300).setBounding(3000));
		physExecutor.getPhysPolygon("poly1").setSpeedX(0).setMass(100f);
		physExecutor.getPhysPolygon("poly2").setMass(1000).setBounding(1000000000);

		physExecutor.addPhysObjectToQueue(new PhysPolygon(new EscapyPolygon(0,0, 50,0, 50,50, 0,50), "tmps").setMinSpeed(0f,0f).setMass(100f));
	}


	@Override
	public void show() {


	}

	@Override
	public void render(float delta) {

		this.privGdxCamera.clear();

		physExecutor.wait_ms(delta).executePhysics();
		physExecutor.draw(privGdxCamera);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) /* Back to game */ {
			super.gameState.setScreen(super.gameState.getStatesContainer().getGameScreen().getScreen());
			super.gameState.getStatesContainer().getGameScreen().getScreen().resume();
		}
		wait += delta;
		if (Gdx.input.isTouched(0) && wait > 0.5f) {
			PhysPolygon tmp = new PhysPolygon(new EscapyPolygon(0,0, 50,0, 50,50, 0,50));
			tmp.setPosition(Gdx.input.getX(), Gdx.input.getY());
			tmp.setMass(100);
			tmp.setSpeedY(-20);
			//tmp.setLiveHits(110);
			tmp.setMinSpeed(0f, 0f);
			physExecutor.addPhysObjectToQueue(tmp);
			wait = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.C)) {
			physExecutor.getPhysPolygon("tmps").setPosition(Gdx.input.getX(), Gdx.input.getY()).setSpeedY(0).setSpeedY(5).setSpeedX(-15);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			physExecutor.getPhysPolygon("tmps").speed_vec[0] -=5;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			physExecutor.getPhysPolygon("tmps").speed_vec[0] +=5;
		}
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
	public void update(float delta) {

	}
}
