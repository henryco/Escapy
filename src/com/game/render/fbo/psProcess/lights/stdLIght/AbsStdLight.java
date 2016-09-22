package com.game.render.fbo.psProcess.lights.stdLIght;

import java.util.function.Function;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.fbo.psProcess.cont.EscapyFBOContainer;
import com.game.render.fbo.psProcess.lights.type.EscapyLightType;
import com.game.render.shader.EscapyStdShaderRenderer;
import com.game.render.shader.lightSrc.EscapyLightSrcRenderer;
import com.game.render.shader.lightSrc.userState.EscapyStdLightSrcRenderer;
import com.game.utils.absContainer.EscapyContainerable;
import com.game.utils.observ.SimpleObservated;
import com.game.utils.observ.SimpleObserver;
import com.game.utils.translationVec.TransVec;

public abstract class AbsStdLight implements EscapyContainerable, EscapyPostProcessed,
		SimpleObserver<TransVec>, SimpleObservated {

	protected SimpleObserver<EscapyFBOContainer> observer;
	protected Sprite lightSprite;

	protected Sprite finLightSprite;
	protected EscapyStdShaderRenderer stdRenderer;
	protected EscapyLightSrcRenderer srcRenderer;

	public EscapyGdxCamera interCam;
	public EscapyLightType lightSource;

	protected Color color;
	protected EscapyFBO fbo, lightMap, lightFBO;

	protected TransVec position;
	protected TransVec resolution;
	protected TransVec lightAngles;
	protected TransVec radius;
	protected TransVec umbra;

	protected float coeff;
	protected float correct;


	protected float scale;
	protected float threshold;

	protected float[] optTranslation;

	protected boolean visible;
	protected boolean needUpdate;

	private int id;


	{
		this.id = this.hashCode();

		this.position = new TransVec().setObservedObj(this);
		this.resolution = new TransVec(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.lightAngles = new TransVec(1f, 0f);
		this.radius = new TransVec(0, 1f);
		this.umbra = new TransVec();

		this.color = new Color(0, 0, 0, 1);

		this.stdRenderer = new EscapyStdShaderRenderer(id);
		this.srcRenderer = new EscapyStdLightSrcRenderer(id);

		this.interCam = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		this.scale = 1;
		this.coeff = 1.f;
		this.correct = 0.5f;

		this.optTranslation = new float[2];

		this.visible = true;
	}

	public AbsStdLight() {
		this.setLightMapFBO(new StandartFBO(this.getID()).forceClearFBO(1f, 1f, 1f, 1f));
	}
	public AbsStdLight(EscapyFBO lightMap) {
		this.setLightMapFBO(lightMap);
	}
	public AbsStdLight(int id, EscapyFBO lightMap) {
		this.setID(id);
		this.setLightMapFBO(lightMap);
	}
	public AbsStdLight(int id) {
		this.setID(id);
		this.setLightMapFBO(new StandartFBO(this.getID()).forceClearFBO(1f, 1f, 1f, 1f));
	}
	public AbsStdLight(TransVec position) {
		this.setPosition(position);
		this.setLightMapFBO(new StandartFBO(this.getID()).forceClearFBO(1f, 1f, 1f, 1f));
	}
	public AbsStdLight(TransVec position, EscapyFBO lightMap) {
		this.setPosition(position);
		this.setLightMapFBO(lightMap);
	}
	public AbsStdLight(int id, TransVec position, EscapyFBO lightMap) {
		this.setID(id);
		this.setPosition(position);
		this.setLightMapFBO(lightMap);
	}
	public AbsStdLight(int id, TransVec position) {
		this.setID(id);
		this.setPosition(position);
		this.setLightMapFBO(new StandartFBO(this.getID()).forceClearFBO(1f, 1f, 1f, 1f));
	}

	public AbsStdLight preRender(EscapyGdxCamera escapyCamera) {

		lightSprite.setPosition(lightSource.getX(), lightSource.getY());

		fbo.begin().wipeFBO();
		this.srcRenderer.renderLightSrc(lightSprite, lightMap.getSpriteRegion(),
				escapyCamera.getCamera(), color, lightAngles, resolution,
				coeff, correct, radius, umbra);
		this.srcRenderer.renderLightSrc(lightSprite, lightMap.getSpriteRegion(),
				escapyCamera.getCamera(), color, lightAngles, resolution,
				coeff, correct, radius, umbra);
		fbo.end();
		return this;
	}


	public AbsStdLight lazyRender(EscapyGdxCamera escapyCamera) {
		interCam.setCameraPosition(lightSource.getX() + (lightSource.getWidth() / 2.f),
				lightSource.getY() + (lightSource.getHeight() / 2f));
		interCam.update();
		return preRender(interCam);
	}

	public SimpleObservated addObserver(SimpleObserver observer) {
		this.observer = observer;
		return this;
	}

	@Override
	public void stateUpdated(TransVec state) {
		this.updState();
		float tempX = state.x - (this.lightSource.getWidth() / 2.f);
		float tempY = state.y - (this.lightSource.getHeight() / 2.f);
		this.lightSource.setPosition(tempX, tempY);
	}

	public AbsStdLight setLightSource(EscapyLightType light) {

		this.updState();
		this.lightSource = light;

		this.lightFBO = new StandartFBO(id, (int) this.lightSource.getWidth(),
				(int) this.lightSource.getHeight());
		this.fbo = new StandartFBO(id, (int) this.lightSource.getWidth(),
				(int) this.lightSource.getHeight());
		this.finLightSprite = new Sprite(fbo.forceWipeFBO().getTextureRegion());
		this.finLightSprite.setScale(scale);

		EscapyFBO lightFBO = new StandartFBO(this.getID(), (int) this.lightSource.getWidth(),
				(int) this.lightSource.getHeight());
		this.lightSprite = new Sprite(lightFBO.forceWipeFBO().getTextureRegion());

		System.out.println(lightSource);
		return this;
	}

	public Sprite getLihgtSprite() {
		return finLightSprite;
	}

	public AbsStdLight setScale(float scale) {
		this.updState();
		this.scale = scale;
		this.finLightSprite.setScale(scale);
		return this;
	}

	public AbsStdLight setPosition(float x, float y) {
		this.updState();
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

	public AbsStdLight setColor(int r255, int g255, int b255) {
		this.updState();
		this.color.r = ((float) r255) / 255f;
		this.color.g = ((float) g255) / 255f;
		this.color.b = ((float) b255) / 255f;
		return this;
	}

	public EscapyFBO getFBO() {
		return fbo;
	}

	public EscapyFBO getLightMapFBO() {
		return lightMap;
	}

	public void setLightMapFBO(EscapyFBO lightMap) {
		this.lightMap = lightMap;
	}

	public boolean isVisible() {
		return visible;
	}

	public AbsStdLight setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public abstract AbsStdLight get();


	public float[] getOptTranslation() {
		return optTranslation;
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public AbsStdLight setUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
		if (needUpdate) this.updState();
		return this;
	}

	protected void updState() {
		if (observer != null) this.observer.stateUpdated(null);
	}

	public AbsStdLight setThreshold(float threshold) {
		this.threshold = threshold;
		return this;
	}

	public float getScale() {
		return scale;
	}

}


