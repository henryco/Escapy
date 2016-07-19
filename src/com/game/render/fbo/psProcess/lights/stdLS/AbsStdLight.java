package com.game.render.fbo.psProcess.lights.stdLS;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.utils.absContainer.EscapyContainerable;
import com.game.utils.translationVec.TransVec;

public abstract class AbsStdLight implements EscapyContainerable, EscapyPostProcessed {

	protected Sprite lightSprite;
	protected Texture lightTexture;
	protected TransVec position;
	
	private int id;
	
	
	{
		this.id = this.hashCode();
		this.position = new TransVec();
		this.lightTexture = new Texture(new FileHandle(getDefaultTexure()));
		this.lightSprite = new Sprite(lightTexture);
	}
	
	public AbsStdLight() {
	}
	
	public AbsStdLight(int id) {
		this.setID(id);
	}
	public AbsStdLight(TransVec position) {
		this.position = position;
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

}

