package com.game.render.fbo.psProcess.cont;

import com.game.render.fbo.psProcess.EscapyPostProcessed;
import com.game.utils.absContainer.EscapyContainer;
import com.game.utils.proxyContainer.EscapyProxy;

// TODO: Auto-generated Javadoc
/**
 * The Class PostProcessedProxy.
 *
 * @param <T>
 *            the generic type
 * @param <U>
 *            the generic type
 */
public class PostProcessedProxy <T extends EscapyPostProcessed, U extends EscapyContainer<T>>
	extends EscapyProxy <T, U> {


	/**
	 * Instantiates a new post processed proxy.
	 */
	public PostProcessedProxy() {
	}
	
	/**
	 * Instantiates a new post processed proxy.
	 *
	 * @param id
	 *            the id
	 * @param holder
	 *            the holder
	 */
	public PostProcessedProxy(int id, U holder) {
		super.holder = holder;
		super.id = id;
	}
	
	/* (non-Javadoc)
	 * @see com.game.utils.proxyContainer.EscapyProxy#hold()
	 */
	@Override
	public PostProcessedProxy<T, U> hold()
	{
		if (super.source() != null)
		{
			super.trueState = holder.getSourceByID(id);
			super.setState();
		} 
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.game.utils.proxyContainer.EscapyProxy#apply()
	 */
	@Override
	public EscapyProxy<T, U> apply() 
	{
		super.holder.removeSourceByID(super.id);
		super.holder.addSource(super.source());
		return this;
	}
	
	/**
	 * Force hold.
	 *
	 * @param id
	 *            the id
	 * @return the post processed proxy
	 */
	public PostProcessedProxy<T, U> forceHold(int id) 
	{
		if (super.source() != null)
			super.snapSource();
		
		super.id = id;
		super.trueState = holder.getSourceByID(id);
		super.setState();
		return this;
	}

	
	
	
	
	
}
