package com.x.game.utils.absContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class EscapyAbsContainer<T extends EscapyContainerable> 
	implements EscapyContainer<T> {

	protected List<T> targetsList, buffer;
	
	public EscapyAbsContainer() {
		
		this.targetsList = new ArrayList<>();
		this.buffer = new ArrayList<>();
	}

	
	
	@Override
	public int addSource(T source) {
		this.targetsList.add(source);
		return targetsList.get(targetsList.size() - 1).getID();
	}

	@Override
	public T getSourceByID(int ID) {
		for (T targtBuff : buffer)
			if (targtBuff.getID() == ID)
				return targtBuff;
			
		for (T targt : targetsList)
			if (targt.getID() == ID) {
				buffer.add(targt);
				return targt;
			}
		return null;
	}

	@Override
	public boolean removeSourceByID(int ID) {
		for (T targt : targetsList)
			if (targt.getID() == ID) {
				buffer.remove(targt);
				return targetsList.remove(targt);
			}
		return false;
	}

	@Override
	public boolean removeSource(T source) {
		buffer.remove(source);
		return targetsList.remove(source);
	}
	
	

}
