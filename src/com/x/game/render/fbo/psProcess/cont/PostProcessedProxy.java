package com.x.game.render.fbo.psProcess.cont;

import com.x.game.render.fbo.psProcess.EscapyPostProcessed;
import com.x.game.utils.absContainer.EscapyContainer;
import com.x.game.utils.proxyContainer.EscapyProxy;

public class PostProcessedProxy <T extends EscapyPostProcessed, U extends EscapyContainer<T>>
	extends EscapyProxy <T, U> {


	public PostProcessedProxy() {
	}
	
	public PostProcessedProxy(int id, U holder) {
		super.holder = holder;
		super.id = id;
	}
	
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
	
	@Override
	public EscapyProxy<T, U> apply() 
	{
		super.holder.removeSourceByID(super.id);
		super.holder.addSource(super.source());
		return this;
	}
	
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
