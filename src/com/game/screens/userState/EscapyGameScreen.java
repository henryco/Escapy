package com.game.screens.userState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.game.GameEnter;
import com.game.animator.EscapyAnimatorBase;
import com.game.characters.InitCharacters;
import com.game.controlls.PlayerControl;
import com.game.map.InitMap;
import com.game.physics_temp.EscapyPhysicsBase;
import com.game.render.EscapyGdxCamera;
import com.game.render.camera.program.CameraProgramFactory;
import com.game.render.extra.container.ExtraRenderContainer;
import com.game.render.extra.lightMap.EscapyLightMapRenderer;
import com.game.render.extra.lightMap.LightMapRenderer;
import com.game.render.extra.normalMap.EscapyNormalMapRender;
import com.game.render.extra.normalMap.NormalRenderer;
import com.game.render.extra.std.StdRenderer;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.cont.LightMaskContainer;
import com.game.render.fbo.psProcess.cont.init.InitLights;
import com.game.render.fbo.psProcess.lights.volLight.VolumeLightsExecutor;
import com.game.render.fbo.psProcess.mask.EscapyMask;
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
	
	/** The player camera program ID. */
	protected int playerCameraProgramID;

	private EscapyFBO stdFBO, nrmlFBO, bgrFBO,
           lightBuffFBO, lightMapFBO, MAINFBO;
	private EscapyMask mask, bgrMask;
	
	private LightMaskContainer lightMask;
	private VolumeLightsExecutor volumeLights;
	private ExtraRenderContainer stdContainer, normalsContainer,
           bgrContainer, lightsMapContainer;
	
	private TransVec otherTranslationVec;
	
	
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
		System.out.println("@constructor");

	}
	

	@Override
	public void show() {
		System.out.println("@show");
		if (!super.initializationEnded)
			this.initState();
	}

	

	@Override
	public Screen initState() {
		System.out.println("@init state");

		
		this.controlls = PlayerControl.playerController();
		this.mapContainer = new InitMap(super.settings.Location(), super.settings.getFrameWIDHT(),
				super.settings.getFrameHEIGHT(), super.settings.scaleRatio());
		this.charactersContainer = new InitCharacters();
		this.physics = new EscapyPhysicsBase(mapContainer.map()).startPhysics();
		this.charactersContainer.player().getPhysicalBody().setPosition(new float[] { 400, 10 });
		this.playerCameraProgramID = super.escapyCamera.getCameraProgramHolder()
				.addCameraProgram(CameraProgramFactory.stdCharacterProgram(this.charactersContainer.player()));
		this.animator = EscapyAnimatorBase.createAnimator().initAnimator().startAnimator();

		this.lightBuffFBO = new StandartFBO();
		this.MAINFBO = new StandartFBO();
		this.bgrFBO = new StandartFBO(); 
		this.stdFBO = new StandartFBO();
		this.nrmlFBO = new StandartFBO();
		this.lightMapFBO = new StandartFBO();
		
		this.lightMask = new LightMaskContainer();
		this.bgrContainer = new ExtraRenderContainer(); 
		this.stdContainer = new ExtraRenderContainer();
		this.normalsContainer = new ExtraRenderContainer();
		this.lightsMapContainer = new ExtraRenderContainer();
		this.volumeLights = new VolumeLightsExecutor();

      this.lightContainer = new InitLights(stdFBO, lightMapFBO, super.settings.Location());

		this.otherTranslationVec = new TransVec();

		this.bgrContainer.addSource(new StdRenderer(mapContainer.backGround())); 
		 for (int i = 0; i < 4; i++)
			for (int j = 0; j < mapContainer.objectSize()[mapContainer.indexTab()[i]]; j++) {
				this.stdContainer.addSource(new StdRenderer(mapContainer.gameObjects()[mapContainer.indexTab()[i]][j]));
				if (mapContainer.gameObjects()[mapContainer.indexTab()[i]][j] instanceof EscapyNormalMapRender)
					this.normalsContainer.addSource(new NormalRenderer((EscapyNormalMapRender) mapContainer.gameObjects()[mapContainer.indexTab()[i]][j]));
				if (mapContainer.gameObjects()[mapContainer.indexTab()[i]][j] instanceof EscapyLightMapRenderer)
					this.lightsMapContainer.addSource(new LightMapRenderer((EscapyLightMapRenderer) mapContainer.gameObjects()[mapContainer.indexTab()[i]][j]));
			}

		 for (int i = 0; i < this.charactersContainer.npc().length; i++)
			this.stdContainer.addSource(new StdRenderer(charactersContainer.npc()[i]));
		
		 this.stdContainer.addSource(new StdRenderer(charactersContainer.player()));
		 this.normalsContainer.addSource(new NormalRenderer(charactersContainer.player()));
		 this.lightsMapContainer.addSource(new LightMapRenderer(charactersContainer.player()));
		
       for (int i = 0; i < mapContainer.objectSize()[mapContainer.indexTab()[4]]; i++) /* FRONT PARALLAX */
			this.stdContainer.addSource(new StdRenderer(mapContainer.gameObjects()[mapContainer.indexTab()[4]][i]).setTranslationVec(otherTranslationVec.getVecArray()));


       this.mask = lightMask.standartMask().setMaskPosition(0, 0, Gdx.graphics.getWidth(),
               Gdx.graphics.getHeight()).setMode(EscapyMask.MULTIPLY).addMaskTarget(stdFBO.getFrameBuffer()
       );

       this.mask.setColor(new Color((40f/255f), (40f/255f), (40f/255f), 1f));

       this.bgrMask = lightMask.standartMask().setMaskPosition(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       this.bgrMask.setMode(EscapyMask.MULTIPLY).addMaskTarget(bgrFBO.getFrameBuffer());



       super.initializationEnded = true;

		System.gc();	
		return this;
	}

	
	
	/**
	 * Upd dist.
	 */
   private void updDist() {
		
		if (Gdx.input.isKeyPressed(Input.Keys.C)) {
			this.lightContainer.getSourceByID(this.lightContainer.lightID[1]).setPosition(
					this.charactersContainer.player().getPhysicalBody().getBodyPosition());
		}
		if (Gdx.input.isTouched(0)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[1]).setPosition(
					Gdx.input.getX() + escapyCamera.getShiftVec().x,
					Gdx.input.getY() + escapyCamera.getShiftVec().y);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setPosition(
					Gdx.input.getX() + escapyCamera.getShiftVec().x, 
					Gdx.input.getY() + escapyCamera.getShiftVec().y);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).rotAngle(0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setMinRadius(r -> r - 0.01f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).rotAngle(-0.01f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.O)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).addAngle(0.01f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.L)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).addAngle(-0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setMinRadius(r -> r + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.T)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setMaxRadius(r -> r + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.G)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setMaxRadius(r -> r - 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setUmbraCoeff(c -> c + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.H)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setUmbraCoeff(c -> c - 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.U)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setUmbraRecess(rc -> rc + 1);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setUmbraRecess(rc -> rc - 1);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.I)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setVisible(true);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
          this.lightContainer.getSourceByID(this.lightContainer.lightID[0]).setVisible(false);
		}
		
		
	}
	
	

	@Override
	public void update() {
		
		this.controlls.baseKeyboard_upd();

		this.charactersContainer.player().updateControlls(controlls.down_A(),controlls.down_D(),
				controlls.down_SPACE(), controlls.down_KEY_LSHIFT(), controlls.IS_MOVING(), false);
	}

	

	@Override
	public void renderGameObjects(EscapyGdxCamera escapyCamera) {

		this.bgrFBO.begin().wipeFBO();
			this.bgrContainer.renderGraphic(escapyCamera);
		this.bgrFBO.end(); 
		
		this.stdFBO.begin().wipeFBO();
			this.stdContainer.renderGraphic(escapyCamera);
		this.stdFBO.end();
		
		this.lightMapFBO.begin().wipeFBO().clearFBO(1, 1, 1, 1);
			this.lightsMapContainer.renderGraphic(escapyCamera);
		this.lightMapFBO.end();
	
		this.nrmlFBO.begin().clearFBO(0.502f, 0.502f, 1f, 1f);
			this.normalsContainer.renderGraphic(escapyCamera);
		this.nrmlFBO.end().mergeBuffer();
	}

	
	

	@Override
	public void render(float delta) {
		
		this.updDist();
		super.escapyCamera.clear();
		this.MAINFBO.forceWipeFBO();
		this.lightBuffFBO.forceWipeFBO();

       this.lightContainer.lights.wipeMultiBuffers();
		
		super.escapyCamera.holdCamera();
		
		this.renderGameObjects(escapyCamera);
		
		this.bgrMask.postRender(MAINFBO, escapyCamera.getTranslationVec());
		this.mask.postRender(MAINFBO, escapyCamera.getTranslationVec()); 
		
		this.MAINFBO.renderFBO();

       lightContainer.lights.lights[0].mergeContainedFBO(escapyCamera, 3);
       lightContainer.lights.lights[1].mergeContainedFBO(escapyCamera, 3);
       lightContainer.lights.lights[0].postRender(lightBuffFBO, escapyCamera.getTranslationVec(), 1);
       lightContainer.lights.lights[1].postRender(lightBuffFBO, escapyCamera.getTranslationVec(), 1);

		this.MAINFBO.renderFBO();
		this.MAINFBO.forceRenderToFBO(lightBuffFBO);
			
		this.MAINFBO.renderFBO();
		this.volumeLights.postRenderLights(MAINFBO, nrmlFBO, lightMapFBO, lightBuffFBO,
              lightContainer.lights.lights[1].getLightIntensity(),
              lightContainer.lights.lights[1].getAmbientIntensity());
	

		this.MAINFBO.renderFBO();


		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			this.pause();
			super.gameState.setScreen(super.gameState.getStatesContainer().getMenuScreen());
		}  
			
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
	public void hide() {
	}

	@Override
	public void resize(int width, int height) {
	}


	@Override
	public void dispose() {
	}

}
