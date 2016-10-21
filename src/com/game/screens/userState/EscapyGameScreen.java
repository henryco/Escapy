package com.game.screens.userState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.game.GameEnter;
import com.game.animator.EscapyAnimatorBase;
import com.game.characters.InitCharacters;
import com.game.controlls.PlayerControl;
import com.game.map.InitMap;
import com.game.map.objects.MapGameObjects;
import com.game.map.objects.layers.ObjectLayer;
import com.game.physics_temp.EscapyPhysicsBase;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.camera.program.program.stdProgram.StdCameraProgram;
import com.game.render.fbo.psProcess.lights.stdLIght.AbsStdLight;
import com.game.screens.EscapyMainState;
import com.game.screens.EscapyScreenState;
import com.game.update_loop.Updatable;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyGameScreen.
 */
public class EscapyGameScreen extends EscapyScreenState implements Updatable, EscapyMainState {

	private InitCharacters charactersContainer;
	private PlayerControl controlls;
	private EscapyPhysicsBase physics;
	private EscapyAnimatorBase animator;
	private MapGameObjects mapObjects;

	/** The player camera program ID. */
	protected int playerCameraProgramID;

	private int [][] id;

	/**
	 * Instantiates a new escapy game screen.
	 *
	 * @param escapyCamera
	 *            the escapy camera
	 * @param gameState
	 *            the game state
	 */
	public EscapyGameScreen(EscapyGdxCamera escapyCamera, GameEnter gameState) {
		super(escapyCamera, gameState);
	}


    @Override
    public void show() {
        if (!super.initializationEnded) this.initState();
    }




    @Override
    public Screen initState(Object ... vars) {

		int[] dim = new int[]{0, 0, super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT};

        this.init_base(dim);
		System.out.println(super.SCREEN_WIDTH+"::"+super.SCREEN_HEIGHT);

        super.initializationEnded = true;
		this.resize(super.SCREEN_WIDTH, super.SCREEN_HEIGHT);
        System.gc();
        return this;
    }
    public void init_base(Object ... vars) {

        this.controlls = PlayerControl.playerController();
		InitMap mapContainer = new InitMap(super.settings.Location(), super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT, super.SCREEN_SCALE);
		this.charactersContainer = new InitCharacters();
        this.physics = new EscapyPhysicsBase(mapContainer.map()).startPhysics();
        this.charactersContainer.player().getPhysicalBody().setPosition(new float[] { 400, 10 });

		this.playerCameraProgramID = super.escapyCamera.getCameraProgramHolder().
				addCameraProgram(new StdCameraProgram(this.charactersContainer.player(), super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT, 0.35f, 0.35f).
				setXProgram(StdCameraProgram.program.followCam).setMinTranslations(0.4f, 0.4f));

		this.mapObjects = new MapGameObjects(new int[]{super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT},
				super.settings.getSourceDir(), super.settings.getObjectsCfgName(), charactersContainer);

		id = new int[][]{{1, 0}, {1, 1}, {1, 2}};
		this.animator = EscapyAnimatorBase.createAnimator().initAnimator().startAnimator();

		mapObjects.forEach(c -> c.weatherCons(w -> w.setFunc(e -> e.getEmitters().forEach(a -> a.getWind().setHigh(-100, -100)))));
    }

	

    @Override
    public void update(float delta) {
        this.controlls.baseKeyboard_upd();
        this.charactersContainer.player().updateControlls(controlls.down_A(),controlls.down_D(),
                controlls.down_SPACE(), controlls.down_KEY_LSHIFT(), controlls.IS_MOVING(), false);
		this.mapObjects.forEach(contianer -> {
			contianer.forEach(ObjectLayer::shift);
			if (contianer.lights != null) contianer.lights.forEach(l -> l.forEach(AbsStdLight::shift));
		});
    }
    private void updDist() {

        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            this.mapObjects.getSourceByID(id[0]).setPosition(
                    Gdx.input.getX() + escapyCamera.getShiftVec().x,
                    Gdx.input.getY() + escapyCamera.getShiftVec().y);
        }
        if (Gdx.input.isTouched(0)) {
            this.mapObjects.getSourceByID(id[1]).setPosition(
                    Gdx.input.getX() + escapyCamera.getShiftVec().x,
                    Gdx.input.getY() + escapyCamera.getShiftVec().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            this.mapObjects.getSourceByID(id[2]).setPosition(
                    Gdx.input.getX() + escapyCamera.getShiftVec().x,
                    Gdx.input.getY() + escapyCamera.getShiftVec().y);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            this.mapObjects.getSourceByID(id[2]).rotAngle(0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            this.mapObjects.getSourceByID(id[2]).setMinRadius(r -> r - 0.01f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.mapObjects.getSourceByID(id[2]).rotAngle(-0.01f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            this.mapObjects.getSourceByID(id[2]).addAngle(0.01f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            this.mapObjects.getSourceByID(id[2]).addAngle(-0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            this.mapObjects.getSourceByID(id[2]).setMinRadius(r -> r + 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            this.mapObjects.getSourceByID(id[2]).setMaxRadius(r -> r + 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            this.mapObjects.getSourceByID(id[2]).setMaxRadius(r -> r - 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            this.mapObjects.getSourceByID(id[2]).setUmbraCoeff(c -> c + 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            this.mapObjects.getSourceByID(id[2]).setUmbraCoeff(c -> c - 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            this.mapObjects.getSourceByID(id[2]).setUmbraRecess(rc -> rc + 1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            this.mapObjects.getSourceByID(id[2]).setUmbraRecess(rc -> rc - 1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            this.mapObjects.getSourceByID(id[2]).setVisible(true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            this.mapObjects.getSourceByID(id[2]).setVisible(false);
        }


    }


    @Override
    public void render(float delta) {
		super.escapyCamera.holdCamera(delta);
        this.updDist();
		super.escapyCamera.wipe();

		this.mapObjects.forEach(container
				->
				container
				.prepareContained(escapyCamera)
				.prepareMask()
				.renderMasked()
				.makeAndPrepareLights(escapyCamera)
				.renderLights(escapyCamera)
		);

		this.ESCAPE();
    }


    @Override
    public void pause() {
		
        this.animator.closeAnimator();
        this.physics.closePhysic();

        /* TEST */
        // super.escapyCamera.getCameraProgramHolder().removeCameraProgram(playerCameraProgramID);
        super.gameState.getStatesContainer().getUpdLoopedQueue().removeFromUpdQueueLast();
    }
    @Override
    public void resume() {
        this.animator.initAnimator().startAnimator();
        this.physics.reInit().startPhysics();

        super.gameState.getStatesContainer().getUpdLoopedQueue().addToUpdQueue(this);
    }
    @Override
    public void hide() {}
    @Override
    public void resize(int width, int height) {
		System.out.println("RESIZE: " +width +" : "+height);
		mapObjects.forEach(container -> container.postExecutorFunc(p -> p.setFrameDim(width, height)));
	}
    @Override
    public void dispose() {}

    public void ESCAPE(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause();
            super.gameState.setScreen(super.gameState.getStatesContainer().getMenuScreen());
        }
    }
}
