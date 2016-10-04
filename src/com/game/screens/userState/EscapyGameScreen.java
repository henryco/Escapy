package com.game.screens.userState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.game.GameEnter;
import com.game.animator.EscapyAnimatorBase;
import com.game.characters.InitCharacters;
import com.game.controlls.PlayerControl;
import com.game.map.InitMap;
import com.game.map.objectsAlt.MapGameObjects;
import com.game.physics_temp.EscapyPhysicsBase;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.camera.program.program.stdProgram.StdCameraProgram;
import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.extra.std.StdRenderer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.cont.init.InitLights;
import com.game.render.mask.LightMask;
import com.game.screens.EscapyMainState;
import com.game.screens.EscapyScreenState;
import com.game.update_loop.Updatable;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyGameScreen.
 */
public class EscapyGameScreen extends EscapyScreenState implements Updatable, EscapyMainState {

	private InitMap mapContainer;
    private InitLights lightContainer;
	private InitCharacters charactersContainer;
	private PlayerControl controlls;
	private EscapyPhysicsBase physics;
	private EscapyAnimatorBase animator;
	private MapGameObjects mapObjects;

	/** The player camera program ID. */
	protected int playerCameraProgramID;

	private EscapyFBO stdFBO, bgrFBO, lightBuffFBO;
	private ExtraRenderContainer stdContainer, bgrContainer;

	private TransVec otherTranslationVec;
	private LightMask stdMask, bgrMask;
	
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
        if (!super.initializationEnded)
            this.initState();
    }





    @Override
    public Screen initState(Object ... vars) {

		int[] dim = new int[]{0, 0, super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT};

        this.init_base(dim);
        this.init_fbo(dim);
		this.init_mask(dim);
        this.init_containers(dim);
		System.out.println(super.settings.getFrameWIDHT()+"::"+super.settings.getFrameHEIGHT());

        super.initializationEnded = true;
        System.gc();
        return this;
    }
    public void init_base(Object ... vars) {

        this.controlls = PlayerControl.playerController();
        this.mapContainer = new InitMap(super.settings.Location(), super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT, super.settings.scaleRatio());
		this.charactersContainer = new InitCharacters();

        this.physics = new EscapyPhysicsBase(mapContainer.map()).startPhysics();
        this.charactersContainer.player().getPhysicalBody().setPosition(new float[] { 400, 10 });

		this.playerCameraProgramID = super.escapyCamera.getCameraProgramHolder().
				addCameraProgram(new StdCameraProgram(this.charactersContainer.player(), super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT, 0.5f, 0.5f).
				setXProgram(StdCameraProgram.program.followCam).setMinTranslations(0.4f, 0.4f));

		this.mapObjects = new MapGameObjects(new int[]{super.SCREEN_DEFAULT_WIDTH, super.SCREEN_DEFAULT_HEIGHT},
				super.settings.getSourceDir(), super.settings.getObjectsCfgName(), charactersContainer);

		this.animator = EscapyAnimatorBase.createAnimator().initAnimator().startAnimator();
    }
    public void init_fbo(Object ... vars) {

        this.lightBuffFBO = new StandartFBO((int[])vars[0], "<LIGHT_FBUFFER>");
        this.bgrFBO = new StandartFBO((int[])vars[0], "<BGR_FBUFFER>");
        this.stdFBO = new StandartFBO((int[])vars[0], "<STD_FBUFFER>");
    }
    public void init_mask(Object ... vars) {

		stdMask = new LightMask((int[])vars[0], true, "<STD_MASK_FBUFFER>").setColor(40,40,40,250);
		bgrMask = new LightMask((int[])vars[0], true, "<BGR_MASK_FBUFFER>").setColor(60,60,60,250);
	}
    public void init_containers(Object ... vars) {

        this.bgrContainer = new ExtraRenderContainer();
        this.stdContainer = new ExtraRenderContainer();

        this.bgrContainer.addSource(new StdRenderer(mapContainer.backGround()));
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < mapContainer.objectSize()[mapContainer.indexTab()[i]]; j++) {
                this.stdContainer.addSource(new StdRenderer(mapContainer.gameObjects()[mapContainer.indexTab()[i]][j]));
            }

        for (int i = 0; i < this.charactersContainer.npc().length; i++)
            this.stdContainer.addSource(new StdRenderer(charactersContainer.npc()[i]));

        this.stdContainer.addSource(new StdRenderer(charactersContainer.player()));

        this.lightContainer = new InitLights(
        		(camera -> this.mapObjects.renderLightMap(camera)),
				(camera -> this.mapObjects.renderNormals(camera)),
				super.settings.getLightCfgUrl(), (int[])vars[0]);
		this.renderBgr(escapyCamera);
		this.render(0);
    }






    @Override
    public void update() {
        this.controlls.baseKeyboard_upd();
        this.charactersContainer.player().updateControlls(controlls.down_A(),controlls.down_D(),
                controlls.down_SPACE(), controlls.down_KEY_LSHIFT(), controlls.IS_MOVING(), false);
    }
    private void updDist() {

        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setPosition(
                    Gdx.input.getX() + escapyCamera.getShiftVec().x,
                    Gdx.input.getY() + escapyCamera.getShiftVec().y);
        }
        if (Gdx.input.isTouched(0)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[1]).setPosition(
                    Gdx.input.getX() + escapyCamera.getShiftVec().x,
                    Gdx.input.getY() + escapyCamera.getShiftVec().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setPosition(
                    Gdx.input.getX() + escapyCamera.getShiftVec().x,
                    Gdx.input.getY() + escapyCamera.getShiftVec().y);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).rotAngle(0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setMinRadius(r -> r - 0.01f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).rotAngle(-0.01f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).addAngle(0.01f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).addAngle(-0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setMinRadius(r -> r + 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.T)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setMaxRadius(r -> r + 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setMaxRadius(r -> r - 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setUmbraCoeff(c -> c + 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setUmbraCoeff(c -> c - 0.01f);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setUmbraRecess(rc -> rc + 1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setUmbraRecess(rc -> rc - 1);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setVisible(true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            this.lightContainer.getSourceByID(this.lightContainer.lightID[2]).setVisible(false);
        }


    }





    @Override
    public void render(float delta) {

		super.escapyCamera.holdCamera(delta);
        this.updDist();
        this.resetFBO();
        this.renderStd(escapyCamera);
        this.renderMasks();
        this.renderFBO();

        this.ESCAPE();
    }
    public void resetFBO(){
		super.escapyCamera.wipe();
        this.lightBuffFBO.forceWipeFBO();
    }

	public void renderBgr(EscapyGdxCamera escapyCamera){

		this.bgrFBO.begin().wipeFBO();
		this.bgrContainer.renderGraphic(escapyCamera);
		this.bgrFBO.end();
	}
	public void renderStd(EscapyGdxCamera escapyCamera){

		this.stdFBO.begin().wipeFBO();
		this.stdContainer.renderGraphic(escapyCamera);
		this.stdFBO.end();
	}


    public void renderMasks() {

		bgrMask.renderMaskBuffered(bgrFBO.getTextureRegion().getTexture()).renderFBO();
		stdMask.renderMaskBuffered(stdFBO.getTextureRegion().getTexture()).renderFBO();
	}
	public void renderFBO() {

		lightContainer.lights.forEach(l -> l.makeLights().renderBlendedLights(escapyCamera, stdFBO.getSpriteRegion(), lightBuffFBO));
		lightContainer.postExecutor.processLightBuffer(lightBuffFBO.getSpriteRegion(), escapyCamera);

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
    public void resize(int width, int height) {}
    @Override
    public void dispose() {}

    public void ESCAPE(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause();
            super.gameState.setScreen(super.gameState.getStatesContainer().getMenuScreen());
        }
    }
}
