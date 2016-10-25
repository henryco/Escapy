package com.game.screens.userState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

	private Sprite testShaderSprite;

	private SpriteBatch batcher;
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

		this.testShaderSprite = new Sprite(new Texture(new FileHandle("data/logo/splash/BgLogo.png")));
		this.testShaderSprite.setSize(super.SCREEN_WIDTH, super.SCREEN_HEIGHT);
		this.testShaderSprite.flip(false, true);

		this.batcher = new SpriteBatch();

		this.physExecutor = new PhysExecutor(Gdx.graphics.getRawDeltaTime());
		physExecutor.meter = 5;
		physExecutor.gravity_a = 60;
		EscapyPolygon poly1 = new EscapyPolygon(new float[]{50, 100, 150, 100, 150, 300, 50, 300});
		EscapyPolygon poly2 = new EscapyPolygon(new float[]{50, 700, 900, 700, 900, 800, 50, 800});

		physExecutor.addPhysObjectToQueue(new PhysPolygon(poly1, "poly1"));
		physExecutor.addPhysObjectToQueue(new PhysPolygon(poly2, true, "poly2"));
		physExecutor.getPhysPolygon("poly1").setSpeedX(10).setMass(100f);
		physExecutor.getPhysPolygon("poly2").setMass(1000).setBounding(1000000000);

	}


	@Override
	public void show() {


	}

	@Override
	public void render(float delta) {

		this.privGdxCamera.clear();

		physExecutor.executePhysics(delta);
		physExecutor.draw(privGdxCamera);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) /* Back to game */ {
			super.gameState.setScreen(super.gameState.getStatesContainer().getGameScreen().getScreen());
			super.gameState.getStatesContainer().getGameScreen().getScreen().resume();
		}
		wait += delta;
		if (Gdx.input.isTouched(0) && wait > 0.5f) {
			PhysPolygon tmp = new PhysPolygon(new EscapyPolygon(new float[]{0,0, 50,0, 50,50, 0,50}));
			tmp.setPosition(Gdx.input.getX(), Gdx.input.getY());
			tmp.setMass(200);
			physExecutor.addPhysObjectToQueue(tmp);
			wait = 0;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.C) && wait > 0.5f) {
			PhysPolygon tmp = new PhysPolygon(new EscapyPolygon(new float[]{0,0, 50,0, 50,50, 0,50}));
			tmp.setPosition(Gdx.input.getX(), Gdx.input.getY());
			tmp.setMass(200);
			tmp.setSpeedX(-170f);
			physExecutor.addPhysObjectToQueue(tmp);
			wait = 0;
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
