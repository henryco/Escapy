package com.game.render.fbo.psProcess.lights.stdLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.shader.colorize.userState.EscapyStdColorizeRenderer;
import com.game.utils.absContainer.EscapyContainerable;
import com.game.utils.observ.SimpleObserver;
import com.game.utils.translationVec.TransVec;

public abstract class AbsStdLight implements EscapyContainerable, EscapyPostProcessed, 
	 SimpleObserver<TransVec> {

	protected Sprite lightSprite;
	protected Texture lightTexture;
	protected TransVec position;
	
	private EscapyStdColorizeRenderer colorizer;
	
	
	protected Color color;
	protected EscapyFBO fbo, lightMap;
	protected EscapyGdxCamera cam;

	
	private int id;
	
	{
		this.id = this.hashCode();
		this.position = new TransVec();
		this.lightTexture = new Texture(new FileHandle(getDefaultTexure()));
		this.lightSprite = new Sprite(lightTexture);
		this.position.setObservedObj(this);
		this.color = new Color(1, 1, 1, 1);
		this.colorizer = new EscapyStdColorizeRenderer(id);
		
		this.fbo = new StandartFBO(lightSprite.getX(), lightSprite.getY(), 
				lightSprite.getWidth(), lightSprite.getHeight()); 
		
		this.cam = new EscapyGdxCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
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
		fbo.begin().wipeFBO();
		this.colorizer.drawSprite(lightSprite, escapyCamera.getCamera());
		this.colorizer.renderColorized(lightSprite, escapyCamera.getCamera(),
				color.r, color.g, color.b);
		this.colorizer.renderColorized(lightSprite, escapyCamera.getCamera(),
						color.r, color.g, color.b);
		fbo.end();
		return this;
	}

	public AbsStdLight preRender() {
		return this.preRender(cam);
	}
	
	
	@Override
	public void stateUpdated(TransVec state) {
		float tempX = state.x - (this.lightSprite.getWidth() / 2.f);
		float tempY = state.y - (this.lightSprite.getHeight() / 2.f);
		this.lightSprite.setPosition(tempX, tempY);
	}
	
	public AbsStdLight setLightSource(String lightFile) {
		this.lightTexture = new Texture(new FileHandle(lightFile));
		this.lightSprite = new Sprite(lightTexture);
		return this;
	}
	
	public AbsStdLight scale(float scale) {
		this.lightSprite.setSize(lightTexture.getWidth() * scale,
				lightTexture.getHeight() * scale);
		return this;
	}
	
	public AbsStdLight setSize(float WH) {
		this.lightSprite.setSize(WH, WH);
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
	
	public abstract String getDefaultTexure();

	@Override
	public int getID() {
		return this.id;
	}
	
	@Override
	public void setID(int id) {
		this.id = id;
	}
	
	public Sprite getLightSprite() {
		return lightSprite;
	}
	public Texture getLightTexture() {
		return lightTexture;
	}
	
	public TransVec getPosition() {
		return position;
	}
	public float[] getPositionArray() {
		return position.getTransVecArray();
	}
	public Vector2 getPositionVec() {
		return position.getTransVec();
	}

	public Color getColor() {
		return color;
	}

	public AbsStdLight setColor(Color color) {
		this.color = color;
		return this;
	}
	public AbsStdLight setColor(float r, float g, float b) {
		this.color.r = r;
		this.color.g = g;
		this.color.b = b;
		return this;
	}
	public AbsStdLight setColor(int r255, int g255, int b255) {
		this.color.r = ((float)(((float)r255)/255f));
		this.color.g = ((float)(((float)g255)/255f));
		this.color.b = ((float)(((float)b255)/255f));
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

}

