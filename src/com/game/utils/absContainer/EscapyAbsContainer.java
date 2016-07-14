package com.game.utils.absContainer;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyAbsContainer.
 *
 * @param <T>
 *            the generic type
 */
public abstract class EscapyAbsContainer<T extends EscapyContainerable> 
	implements EscapyContainer<T> {

	/** The buffer. */
	protected List<T> targetsList, buffer;
	
	/**
	 * Instantiates a new escapy abs container.
	 */
	public EscapyAbsContainer() {
		
		this.targetsList = new ArrayList<>();
		this.buffer = new ArrayList<>();
	}

	
	
	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#addSource(java.lang.Object)
	 */
	@Override
	public int addSource(T source) {
		this.targetsList.add(source);
		return targetsList.get(targetsList.size() - 1).getID();
	}

	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#getSourceByID(int)
	 */
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

	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#removeSourceByID(int)
	 */
	@Override
	public boolean removeSourceByID(int ID) {
		for (T targt : targetsList)
			if (targt.getID() == ID) {
				buffer.remove(targt);
				return targetsList.remove(targt);
			}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.game.utils.absContainer.EscapyContainer#removeSource(java.lang.Object)
	 */
	@Override
	public boolean removeSource(T source) {
		buffer.remove(source);
		return targetsList.remove(source);
	}
	
	

}
