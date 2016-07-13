package com.game.render.extra;

import com.game.render.EscapyRenderable;

public abstract class ExtraRendererSuper <T extends EscapyRenderable> 
	implements EscapyExtraRenderer<T> {
	
	protected T renderTarget;
	protected float[] translationVec;
	private int ID;
	
	public ExtraRendererSuper(T target) {
		this.renderTarget = target;
		this.setTranslationVec(new float[2]);
		this.setID(this.hashCode());
	}


	@Override
	public T getRenderTarget() {
		return renderTarget;
	}
	@Override
	public EscapyExtraRenderer<T> setRenderTarget(T renderTarget) {
		this.renderTarget = renderTarget;
		return this;
	}
	@Override
	public float[] getTranslationVec() {
		return translationVec;
	}
	@Override
	public EscapyExtraRenderer<T> setTranslationVec(float[] translationVec) {
		this.translationVec = translationVec;
		return this;
	}
	@Override
	public void setID(int id) {
		this.ID = id;
	}

	@Override
	public int getID() {
		return this.ID;
	}

}
