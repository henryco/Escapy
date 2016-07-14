package com.game.render.extra;

import com.game.render.EscapyRenderable;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtraRendererSuper.
 *
 * @param <T>
 *            the generic type
 */
public abstract class ExtraRendererSuper <T extends EscapyRenderable> 
	implements EscapyExtraRenderer<T> {
	
	/** The render target. */
	protected T renderTarget;
	
	/** The translation vec. */
	protected float[] translationVec;
	private int ID;
	
	/**
	 * Instantiates a new extra renderer super.
	 *
	 * @param target
	 *            the target
	 */
	public ExtraRendererSuper(T target) {
		this.renderTarget = target;
		this.setTranslationVec(new float[2]);
		this.setID(this.hashCode());
	}


	/* (non-Javadoc)
	 * @see com.game.render.extra.EscapyExtraRenderer#getRenderTarget()
	 */
	@Override
	public T getRenderTarget() {
		return renderTarget;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.extra.EscapyExtraRenderer#setRenderTarget(com.game.render.EscapyRenderable)
	 */
	@Override
	public EscapyExtraRenderer<T> setRenderTarget(T renderTarget) {
		this.renderTarget = renderTarget;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.extra.EscapyExtraRenderer#getTranslationVec()
	 */
	@Override
	public float[] getTranslationVec() {
		return translationVec;
	}
	
	/* (non-Javadoc)
	 * @see com.game.render.extra.EscapyExtraRenderer#setTranslationVec(float[])
	 */
	@Override
	public EscapyExtraRenderer<T> setTranslationVec(float[] translationVec) {
		this.translationVec = translationVec;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainerable#setID(int)
	 */
	@Override
	public void setID(int id) {
		this.ID = id;
	}

	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainerable#getID()
	 */
	@Override
	public int getID() {
		return this.ID;
	}

}
