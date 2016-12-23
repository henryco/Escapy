package com.game.render.fbo.psProcess.lights.stdLIght;

import java.util.Arrays;
import java.util.function.Function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.game.map.objects.objects.utils.PositionTranslator;
import com.game.map.objects.objects.utils.translators.GameObjTranslators;
import com.game.render.EscapyUniTrans;
import com.game.render.camera.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.cont.EscapyFBOContainer;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.render.fbo.psProcess.lights.type.EscapyPeriodicLight;
import com.game.utils.periodic.EscapyPeriodicAction;
import com.game.render.shader.EscapyStdShaderRenderer;
import com.game.render.shader.lightSrc.EscapyLightSrcRenderer;
import com.game.render.shader.lightSrc.userState.EscapyStdLightSrcRenderer;
import com.game.utils.absContainer.EscapyContainerable;
import com.game.utils.observer.SimpleObservated;
import com.game.utils.observer.SimpleObserver;
import com.game.utils.translationVec.TransVec;

public abstract class AbsStdLight implements EscapyContainerable,
		SimpleObserver<TransVec>, SimpleObservated, EscapyUniTrans {

	private PositionTranslator positionTranslator = (width, height, position1) -> position1;
	private EscapyPeriodicAction<AbsStdLight>[] periodicActions;

	protected SimpleObserver<EscapyFBOContainer> observer;
	protected Sprite lightSprite, bgSprite;

	protected EscapyStdShaderRenderer stdRenderer;
	protected EscapyLightSrcRenderer srcRenderer;

	public EscapyGdxCamera interCam;
	public EscapyLightType lightSource;

	protected Color color;
	protected EscapyFBO fbo;
	protected TransVec position, umbra, resolution, lightAngles, radius;

	protected float coeff, correct, scale, threshold, actualPeriodTime, alpha;
	protected float[] optTranslation;
	protected boolean visible, needUpdate;
	protected int periodsNumb, id, actualPeriod;
	protected float[] period;


	{
		this.id = this.hashCode();

		this.position = new TransVec().setObservedObj(this);
		this.resolution = new TransVec();
		this.lightAngles = new TransVec(1f, 0f);
		this.radius = new TransVec(0, 1f);
		this.umbra = new TransVec();

		this.color = new Color(0, 0, 0, 1);

		this.stdRenderer = new EscapyStdShaderRenderer(id);
		this.srcRenderer = new EscapyStdLightSrcRenderer(id);

		this.scale = 1.f;
		this.coeff = 1.f;
		this.alpha = 1.f;
		this.correct = 0.5f;
		this.periodsNumb = 0;
		this.actualPeriod = 0;
		this.actualPeriodTime = 0;

		this.optTranslation = new float[2];
		this.period = new float[]{Float.MAX_VALUE};
		this.periodicActions = new EscapyPeriodicAction[]{((delta, max, obj) -> obj)};

		this.visible = true;
	}

	public AbsStdLight() {}
	public AbsStdLight(int id) {
		this.setID(id);
	}
	public AbsStdLight(TransVec position) {
		this.setPosition(position);
	}
	public AbsStdLight(int id, TransVec position) {
		this.setID(id);
		this.setPosition(position);
	}

	@Override
	public SimpleObservated addObserver(SimpleObserver observer) {
		this.observer = observer;
		return this;
	}

	@Override
	public void stateUpdated(TransVec state) {
		float tempX = state.x - (this.lightSource.getWidth() / 2.f);
		float tempY = state.y - (this.lightSource.getHeight() / 2.f);
		this.lightSource.setPosition(tempX, tempY);
	}

	@Override
	public AbsStdLight shift() {
		if (positionTranslator != null) {
			float[] newVec = positionTranslator.translatePosition(resolution.x, resolution.y, position.getVecArray().clone());
			if (newVec[0] != position.x || newVec[1] != position.y)
				this.position.setTransVec(positionTranslator.translatePosition(resolution.x, resolution.y, newVec));
		}
		return this;
	}

	public AbsStdLight updAction(float delta) {

		if ((actualPeriodTime -= (delta)) <= 0) {
			if ((actualPeriod += 1) >= periodsNumb) actualPeriod = 0;
			actualPeriodTime = period[actualPeriod];
		}
		if (periodicActions[actualPeriod] != null) {
			periodicActions[actualPeriod].action(delta, (actualPeriodTime / period[actualPeriod]), this);
		}
		return this;
	}

	public AbsStdLight preRender(EscapyGdxCamera escapyCamera) {

		lightSprite.setPosition(lightSource.getX(), lightSource.getY());
		bgSprite.setPosition(lightSource.getX(), lightSource.getY());

		fbo.begin().wipeFBO();
		this.srcRenderer.renderLightSrc(lightSprite, bgSprite,
				escapyCamera.getCamera(), color, lightAngles, resolution,
				coeff, correct, radius, umbra);
		this.srcRenderer.renderLightSrc(lightSprite, bgSprite,
				escapyCamera.getCamera(), color, lightAngles, resolution,
				coeff, correct, radius, umbra);
		fbo.end();
		System.out.println("render: "+getID());
		return this;
	}

	public AbsStdLight lazyRender(EscapyGdxCamera escapyCamera) {
		interCam.setCameraPosition(lightSource.getX() + (lightSource.getWidth() / 2.f),
				lightSource.getY() + (lightSource.getHeight() / 2f));
		interCam.update();
		setUpdate(false);
		return preRender(interCam);
	}

	public AbsStdLight setLightSource(EscapyLightType light) {

		this.updState();
		this.lightSource = light;
		this.interCam = new EscapyGdxCamera((int) this.lightSource.getWidth(), (int) this.lightSource.getHeight());
		this.fbo = new StandartFBO(id, (int) this.lightSource.getWidth(), (int) this.lightSource.getHeight(), "<AbsLightTexture_FBUFFER>");
		FrameBuffer tmpBuff = new FrameBuffer(Pixmap.Format.RGBA8888, (int) this.lightSource.getWidth(),(int) this.lightSource.getHeight(), false);
		tmpBuff.begin();
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tmpBuff.end();
		FrameBuffer bgBuff = new FrameBuffer(Pixmap.Format.RGBA8888, (int) this.lightSource.getWidth(),(int) this.lightSource.getHeight(), false);
		bgBuff.begin();
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		bgBuff.end();

		this.lightSprite = new Sprite(tmpBuff.getColorBufferTexture());
		this.bgSprite = new Sprite(bgBuff.getColorBufferTexture());

		System.out.println(lightSource.setName(Integer.toString(getID())));
		return this;
	}

	public AbsStdLight setScale(float scale) {
		this.updState();
		this.scale = scale;
		return this;
	}
	public AbsStdLight setPosition(float x, float y) {
		this.position.setTransVec(x, y);
		return this;
	}
	public AbsStdLight setPosition(float[] xy) {
		this.setPosition(xy[0], xy[1]);
		return this;
	}
	public AbsStdLight setPosition(Vector2 vec) {
		this.setPosition(vec.x, vec.y);
		return this;
	}
	public AbsStdLight setPosition(TransVec vec) {
		this.setPosition(vec.x, vec.y);
		return this;
	}
	public AbsStdLight setCoeff(float cf) {
		this.updState();
		this.coeff = cf;
		if (coeff > 1 || coeff < 0) coeff = 0.5f;
		return this;
	}
	public AbsStdLight setAngle(float srcAngle, float shiftAngle, float corr) {
		this.updState();
		this.lightAngles.setTransVec(srcAngle, 0);
		this.setAngleCorrection(corr);
		this.rotAngle(shiftAngle);
		return this;
	}
	public AbsStdLight setAngle(float angle) {
		return this.setAngle(angle, 0, 0.5f);
	}
	public AbsStdLight setAngle(float[] angles) {
		return this.setAngle(angles[0], angles[1], 0.5f);
	}
	public AbsStdLight setAngle(TransVec angles) {
		return this.setAngle(angles.x, angles.y, 0.5f);
	}
	public AbsStdLight rotAngle(float shiftAngle) {
		this.updState();
		this.lightAngles.add(shiftAngle, shiftAngle);
		if (lightAngles.x > 0.5f + correct) lightAngles.sub(1, 0);
		if (lightAngles.y > 0.5f + correct) lightAngles.sub(0, 1);
		if (lightAngles.x < -0.5f + correct) lightAngles.add(1, 0);
		if (lightAngles.y < -0.5f + correct) lightAngles.add(0, 1);
		System.out.println(lightAngles);
		return this;
	}
	public AbsStdLight addAngle(float shiftAngle) {
		this.updState();
		this.lightAngles.add(shiftAngle, 0);
		if (lightAngles.x > 0.5f + correct) lightAngles.sub(1, 0);
		if (lightAngles.y > 0.5f + correct) lightAngles.sub(0, 1);
		if (lightAngles.x < -0.5f + correct) lightAngles.add(1, 0);
		if (lightAngles.y < -0.5f + correct) lightAngles.add(0, 1);
		System.out.println(lightAngles);
		return this;
	}
	public AbsStdLight setAngleCorrection(float corr) {
		this.updState();
		this.correct = corr;
		return this;
	}
	/**
	 * @param minRadius - minimal radius, value range from 0.0 to 1.0;
	 * @return {@link AbsStdLight}
	 */
	public AbsStdLight setMinRadius(float minRadius) {
		this.updState();
		this.radius.setX((minRadius <= 0) ? 0 : minRadius);
		return this;
	}
	public AbsStdLight setMinRadius(Function<Float, Float> funct) {
		return this.setMinRadius(funct.apply(this.radius.getX()));
	}
	/**
	 * @param maxRadius - maximal radius, value range from 0.0 to 1.0;
	 * @return {@link AbsStdLight}
	 */
	public AbsStdLight setMaxRadius(float maxRadius) {
		this.updState();
		this.radius.setY((maxRadius <= 0) ? 0 : maxRadius);
		return this;
	}
	public AbsStdLight setMaxRadius(Function<Float, Float> funct) {
		return this.setMaxRadius(funct.apply(this.radius.getY()));
	}
	public AbsStdLight setUmbraCoeff(float umbraCoeff) {
		this.updState();
		this.umbra.setX((umbraCoeff <= 0) ? 0 : umbraCoeff);
		System.out.println(umbra);
		return this;
	}
	public AbsStdLight setUmbraCoeff(Function<Float, Float> funct) {
		return this.setUmbraCoeff(funct.apply(this.umbra.getX()));
	}
	public AbsStdLight setUmbraRecess(float umbraRecess) {
		this.updState();
		this.umbra.setY((umbraRecess <= 0) ? 0 : umbraRecess);
		System.out.println(umbra);
		return this;
	}
	public AbsStdLight setUmbraRecess(Function<Float, Float> funct) {
		return this.setUmbraRecess(funct.apply(this.umbra.getY()));
	}
	//*
	public AbsStdLight setColor(Color color) {
		this.updState();
		this.color = color;
		return this;
	}
	public AbsStdLight setColor(float r, float g, float b) {
		this.updState();
		this.color.r = r;
		this.color.g = g;
		this.color.b = b;
		return this;
	}
	//*/
	public AbsStdLight setColor(int r255, int g255, int b255) {
		this.updState();
		this.color.r = ((float) r255) / 255f;
		this.color.g = ((float) g255) / 255f;
		this.color.b = ((float) b255) / 255f;
		return this;
	}
	public AbsStdLight setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	public AbsStdLight setThreshold(float threshold) {
		this.threshold = threshold;
		return this;
	}
	public AbsStdLight setPositionTranslator(PositionTranslator translator) {
		this.positionTranslator = translator;
		return this;
	}
	public AbsStdLight setPeriods(float... period) {
		this.period = period;
		for (int i = 0; i < period.length; i++)
			this.period[i] *= 0.001f;
		System.out.println("period: "+Arrays.toString(this.period));
		return updPeriodsNumb();
	}
	public AbsStdLight setPeriodicActions(EscapyPeriodicAction<AbsStdLight>... periodicActions) {
		this.periodicActions = periodicActions;
		return updPeriodsNumb();
	}
	public AbsStdLight setAlpha(float a) {
		this.alpha = a;
		return this;
	}


	public AbsStdLight setPositionTranslator(String[] arg) {
		return setPositionTranslator(GameObjTranslators.loadByName(arg));
	}

	public AbsStdLight setPeriodicActions(String[] ... args) {
		EscapyPeriodicAction<AbsStdLight>[] actions = new EscapyPeriodicAction[args.length];
		for (int i = 0; i < actions.length; i++) actions[i] = EscapyPeriodicLight.loadMethod(args[i]);
		return setPeriodicActions(actions);
	}



	public abstract EscapyLightType getDefaultLight();

	@Override
	public int getID() {
		return this.id;
	}
	@Override
	public void setID(int id) {
		this.id = id;
	}
	public TransVec getPosition() {
		return position;
	}
	public float[] getPositionArray() {
		return position.getVecArray();
	}
	public Color getColor() {
		return color;
	}
	public EscapyFBO getFBO() {
		return fbo;
	}
	public boolean isVisible() {
		return visible;
	}
	public float[] getOptTranslation() {
		return optTranslation;
	}
	public float getAlpha() {return alpha;}
	public boolean isNeedUpdate() {
		return needUpdate;
	}
	public AbsStdLight setUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
		return this;
	}
	protected void updState() {
		if (observer != null)
			this.observer.stateUpdated(null);
		setUpdate(true);
	}
	public float getScale() {
		return scale;
	}
	public AbsStdLight updPeriodsNumb(){
		this.periodsNumb = Math.min(period.length, periodicActions.length);
		return this;
	}

}


