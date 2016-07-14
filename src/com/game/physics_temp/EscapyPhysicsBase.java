package com.game.physics_temp;

import cern.colt.matrix.ObjectMatrix3D;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyPhysicsBase.
 */
public class EscapyPhysicsBase extends EscapyPhysicsSuper {

	private Thread PHYS_THREAD;
	private boolean initialized = false;
	private volatile boolean ended = false; // XXX

	private TEMP_EscapyPhysicsPlayerControls tempControllsHelper;

	private EscapyPhysicsCalculationsThread calcThreadOb;

	/**
	 * Instantiates a new escapy physics base.
	 *
	 * @param areaMap
	 *            the area map
	 */
	public EscapyPhysicsBase(ObjectMatrix3D areaMap) {
		super.addMap(areaMap);
		this.reInit();
		return;
	}

	/**
	 * Re init.
	 *
	 * @return the escapy physics base
	 */
	public EscapyPhysicsBase reInit() {
		tempControllsHelper = TEMP_EscapyPhysicsPlayerControls.createControlls().initInputs();
		calcThreadOb = new EscapyPhysicsCalculationsThread();
		PHYS_THREAD = new Thread(calcThreadOb);
		initialized = true;
		return this;
	}

	/**
	 * Start physics.
	 *
	 * @return the escapy physics base
	 */
	public EscapyPhysicsBase startPhysics() {
		if (initialized) {
			tempControllsHelper.startThread();
			calcThreadOb.start();
			PHYS_THREAD.start();
			ended = false;
			System.out.println("::" + eventObject.size());
		}
		return this;
	}

	/**
	 * Close physic.
	 */
	public void closePhysic() {
		if (!ended) {
			tempControllsHelper.stopThread();
			calcThreadOb.close();
			try {
				PHYS_THREAD.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ended = true;
		}
	}

	/**
	 * End physics.
	 */
	public void endPhysics() {
		closePhysic();
		eventObject.clear();
		physObject.clear();
		initialized = false;
		System.gc();
	}

	/**
	 * Checks if is ended.
	 *
	 * @return true, if is ended
	 */
	public boolean isEnded() {
		return ended;
	}

}
