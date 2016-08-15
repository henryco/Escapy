package com.game.render.fbo.psProcess.lights.stdLS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.render.EscapyGdxCamera;
import com.game.render.fbo.EscapyFBO;
import com.game.render.fbo.StandartFBO;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.render.shader.EscapyStdShaderRenderer;
import com.game.render.shader.colorize.userState.EscapyStdColorizeRenderer;
import com.game.utils.absContainer.EscapyContainerable;
import com.game.utils.observ.SimpleObserver;
import com.game.utils.translationVec.TransVec;

public abstract class AbsStdLight implements EscapyContainerable, EscapyPostProcessed, 
	 SimpleObserver<TransVec> {

	protected Sprite lightSprite;
	protected Texture lightTexture;
	protected TransVec position;
	
	protected EscapyStdColorizeRenderer colorizer;
	protected EscapyStdShaderRenderer stdRenderer;
	
	protected Color color;
	protected EscapyFBO fbo, lightMap;
	
	protected Vector2 resolution;
	protected float scale;
	protected float coeff;
	
	private int id;
	
	{
		this.id = this.hashCode();
		this.position = new TransVec();
		this.position.setObservedObj(this);
		this.color = new Color(1, 1, 1, 1);
		this.colorizer = new EscapyStdColorizeRenderer(id);
		
		this.fbo = new StandartFBO(id); 
		
		this.resolution = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.scale = 1.f;
		this.coeff = 1.f;
		this.stdRenderer = new EscapyStdShaderRenderer(id);
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
		this.stdRenderer.drawSprite(lightSprite, escapyCamera.getCamera());

		this.colorizer.renderColorized(lightSprite, lightMap.getSpriteRegion(), 
			escapyCamera.getCamera(), color.r, color.g, color.b, 
			getPosition(), resolution, coeff);
		
		this.colorizer.renderColorized(lightSprite, lightMap.getSpriteRegion(), 
			escapyCamera.getCamera(), color.r, color.g, color.b, 
			getPosition(), resolution, coeff);
	
		fbo.renderFBO();
		fbo.end();
		return this;
	}

	
	@Override
	public void stateUpdated(TransVec state) {
		float tempX = state.x - (this.lightSprite.getWidth() / 2.f);
		float tempY = state.y - (this.lightSprite.getHeight() / 2.f);
		this.lightSprite.setPosition(tempX, tempY);
	}
	
	public AbsStdLight setLightSource(String lightFile) {
		try {
			this.lightTexture = new Texture(new FileHandle(lightFile));
			this.lightTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			this.lightSprite = new Sprite(lightTexture);
		} catch (Exception e) {}
		return this;
	}
	
	public AbsStdLight scale(float scale) {
		this.lightSprite.setSize(lightTexture.getWidth() * scale,
				lightTexture.getHeight() * scale);
		this.scale = scale;
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
	public AbsStdLight setCoeff(float cf) {
		this.coeff = cf;
		if (coeff > 1 || coeff < 0) coeff = 0.5f;
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
		return position.getVecArray();
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

