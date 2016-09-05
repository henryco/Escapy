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
import com.game.render.fbo.StandartMultiFBO;
import com.game.render.fbo.psProcess.cont.LightContainer;
import com.game.render.fbo.psProcess.cont.LightMaskContainer;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyProxyShadowLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyShadedLight;
import com.game.render.fbo.psProcess.lights.stdLIght.userState.EscapyStdLight;
import com.game.render.fbo.psProcess.lights.type.EscapyLightSrcFactory;
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
	private InitCharacters charactersContainer;
	private PlayerControl controlls;
	private EscapyPhysicsBase physics;
	private EscapyAnimatorBase animator;
	
	/** The player camera program ID. */
	protected int playerCameraProgramID;
	

	private int mouseLight;
	private int testLight;

	private EscapyFBO stdFBO, nrmlFBO, bgrFBO, lightStdFBO,
				lightBuffFBO, lightMapFBO, lightShadeFBO, MAINFBO;
	private EscapyMask mask, bgrMask;
	
	private LightMaskContainer lightMask;
	private LightContainer shadedLights, stdLights;
	private VolumeLightsExecutor volumeLights;
	private ExtraRenderContainer stdContainer, normalsContainer, bgrContainer, lightsMapContainer;
	
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

		return;
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
		this.lightShadeFBO = new StandartMultiFBO(stdFBO);
		this.lightStdFBO = new StandartMultiFBO(stdFBO);
		this.lightMapFBO = new StandartFBO();
		
		this.lightMask = new LightMaskContainer();
		this.bgrContainer = new ExtraRenderContainer(); 
		this.stdContainer = new ExtraRenderContainer();
		this.normalsContainer = new ExtraRenderContainer();
		this.lightsMapContainer = new ExtraRenderContainer();
		this.volumeLights = new VolumeLightsExecutor();
		this.shadedLights = new LightContainer(lightShadeFBO, LightContainer.light.screenDodge(), true);
		this.stdLights = new LightContainer(lightStdFBO, LightContainer.light.strongSoftLight(), false);

//		this.mouseLight = this.shadedLights.addSource(new EscapyShadedLight(
//				lightMapFBO, 4, EscapyLightSrcFactory.RND_256()).setMaxRadius(1.5f).
//				setPosition(400, 450).setColor(0, 0, 0).
//				setAngle(0.125f).setVisible(true).setScale(5.5f));

       this.mouseLight = this.shadedLights.addSource(new EscapyProxyShadowLight(
               lightMapFBO, 4, EscapyLightSrcFactory.RND_256()).setMaxRadius(1.5f).
               setPosition(400, 450).setColor(0, 0, 0).
               setAngle(0.125f).setVisible(true).setScale(5.5f));

		this.testLight = this.stdLights.addSource(new EscapyStdLight(lightMapFBO,
				EscapyLightSrcFactory.RND_512()).setPosition(600, 420).
				setColor(205, 107, 107).setVisible(true).setScale(3f));
		
		this.mask = lightMask.standartMask().setMaskPosition(0, 0, Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight()).setMode(EscapyMask.MULTIPLY).addMaskTarget(stdFBO.getFrameBuffer());
		this.mask.setColor(new Color((40f/255f), (40f/255f), (40f/255f), 1f));
		
		this.bgrMask = lightMask.standartMask().setMaskPosition(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.bgrMask.setMode(EscapyMask.MULTIPLY).addMaskTarget(bgrFBO.getFrameBuffer());
		
		

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
		
		
		super.initializationEnded = true;

		System.gc();	
		return this;
	}

	
	
	/**
	 * Upd dist.
	 */
   private void updDist() {
		
		if (Gdx.input.isKeyPressed(Input.Keys.C)) {
			this.stdLights.getSourceByID(this.testLight).setPosition(
					this.charactersContainer.player().getPhysicalBody().getBodyPosition());
		}
		if (Gdx.input.isTouched(0)) {
			this.stdLights.getSourceByID(this.testLight).setPosition(
					Gdx.input.getX() + escapyCamera.getShiftVec().x, 
					Gdx.input.getY() + escapyCamera.getShiftVec().y);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
			this.shadedLights.getSourceByID(this.mouseLight).setPosition(
					Gdx.input.getX() + escapyCamera.getShiftVec().x, 
					Gdx.input.getY() + escapyCamera.getShiftVec().y);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			this.shadedLights.getSourceByID(this.mouseLight).rotAngle(0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			this.shadedLights.getSourceByID(this.mouseLight).setMinRadius(r -> r - 0.01f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			this.shadedLights.getSourceByID(this.mouseLight).rotAngle(-0.01f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.O)) {
			this.shadedLights.getSourceByID(this.mouseLight).addAngle(0.01f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.L)) {
			this.shadedLights.getSourceByID(this.mouseLight).addAngle(-0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
			this.shadedLights.getSourceByID(this.mouseLight).setMinRadius(r -> r + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.T)) {
			this.shadedLights.getSourceByID(this.mouseLight).setMaxRadius(r -> r + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.G)) {
			this.shadedLights.getSourceByID(this.mouseLight).setMaxRadius(r -> r - 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
			this.shadedLights.getSourceByID(this.mouseLight).setUmbraCoeff(c -> c + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.H)) {
			this.shadedLights.getSourceByID(this.mouseLight).setUmbraCoeff(c -> c - 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.U)) {
			this.shadedLights.getSourceByID(this.mouseLight).setUmbraRecess(rc -> rc + 1);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
			this.shadedLights.getSourceByID(this.mouseLight).setUmbraRecess(rc -> rc - 1);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
			this.stdLights.setAmbientIntesity(am -> am + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.M)) {
			this.stdLights.setAmbientIntesity(am -> am - 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.V)) {
			this.stdLights.setLightIntensity(am -> am + 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.B)) {
			this.stdLights.setLightIntensity(am -> am - 0.01f);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.I)) {
			this.shadedLights.getSourceByID(this.mouseLight).setVisible(true);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
			this.shadedLights.getSourceByID(this.mouseLight).setVisible(false);
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
		this.lightShadeFBO.forceWipeFBO();
		this.lightBuffFBO.forceWipeFBO();
		this.lightStdFBO.forceWipeFBO();
		
		super.escapyCamera.holdCamera();
		
		this.renderGameObjects(escapyCamera);
		
		this.bgrMask.postRender(MAINFBO, escapyCamera.getTranslationVec());
		this.mask.postRender(MAINFBO, escapyCamera.getTranslationVec()); 
		
		this.MAINFBO.renderFBO();
		this.shadedLights.mergeContainedFBO(escapyCamera, 5);
		this.stdLights.mergeContainedFBO(escapyCamera, 3);
//	/*	
		
		this.shadedLights.postRender(lightBuffFBO, escapyCamera.getTranslationVec(), 1);
		this.stdLights.postRender(lightBuffFBO, escapyCamera.getTranslationVec(), 1);
		
		this.MAINFBO.renderFBO();
		this.MAINFBO.forceRenderToFBO(lightBuffFBO);//.forceRenderToFBO(lightBuffFBO);
			
		this.MAINFBO.renderFBO();
		this.volumeLights.postRenderLights(MAINFBO, nrmlFBO, lightMapFBO, lightBuffFBO, 
				stdLights.getLightIntensity(), stdLights.getAmbientIntensity());
	
//		this.MAINFBO.renderFBO();
		this.MAINFBO.renderFBO();
//	*/

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
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
