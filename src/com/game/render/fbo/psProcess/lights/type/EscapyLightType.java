package com.game.render.fbo.psProcess.lights.type;

import com.game.render.shader.lightSrc.EscapyLightSrcRenderer;
import com.game.utils.translationVec.TransVec;

public class EscapyLightType {

	public final int ID;
	public EscapyLightSrcRenderer scrRenderer;
	
	private float width, height;
	private TransVec position;
	{
		this.position = new TransVec();
		this.width = 0;
		this.height = 0;
	}
	

	public EscapyLightType(int id, float height, float width) {
		this.ID = id;
		this.setSize(width, height);
	}
	public EscapyLightType(int id, float WH) {
		this.ID = id;
		this.setSize(WH);
	}
	public EscapyLightType(float height, float width) {
		this.ID = this.hashCode();
		this.setSize(width, height);
	}
	public EscapyLightType(float WH) {
		this.ID = this.hashCode();
		this.setSize(WH);
	}
	public EscapyLightType(float x, float y, float height, float width) {
		this.ID = this.hashCode();
		this.setSize(width, height);
		this.setPosition(x, y);

	}
	public EscapyLightType(int id, float x, float y, float height, float width) {
		this.ID = id;
		this.setSize(width, height);
		this.setPosition(x, y);
	}
	
	public TransVec getPosition() {
		return position;
	}
	public float getX() {
		return this.position.getX();
	}
	public float getY() {
		return this.position.getY();
	}
	public EscapyLightType setPosition(TransVec position) {
		return this.setPosition(position.x, position.y);
	}
	public EscapyLightType setPosition(float[] position) {
		return this.setPosition(position[0], position[1]);
	}
	public EscapyLightType setPosition(float x, float y) {
		this.position.setTransVec(x, y);
		return this;
	}
	public EscapyLightType setSize(float WH) {
		return setSize(WH, WH);
	}
	public EscapyLightType setSize(float width, float height) {
		this.width = (width);
		this.height = (height);
		return this;
	}
	public EscapyLightType scale(float scale) {
		return this.setSize(this.width * scale, this.height * scale);
	}

	public float getHeight() {
		return height;
	}
	public float getWidth() {
		return width;
	}
	
	public EscapyLightType setLightSrcRenderer(EscapyLightSrcRenderer renderer) {
		this.scrRenderer = renderer;
		return this;
	}
	@Override
	public String toString() {
		return ID+"| "+getX()+" : "+getY()+" | "+getWidth()+" : "+getHeight()+" <EscapyLightType>";
	}
	

}
