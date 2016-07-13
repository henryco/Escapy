package com.x.game.physics_temp;

import cern.colt.matrix.ObjectMatrix3D;

public class EscapyPhysicsBase extends EscapyPhysicsSuper {

	private Thread PHYS_THREAD;
	private boolean initialized = false;
	private volatile boolean ended = false; // XXX

	private TEMP_EscapyPhysicsPlayerControls tempControllsHelper;

	private EscapyPhysicsCalculationsThread calcThreadOb;

	public EscapyPhysicsBase(ObjectMatrix3D areaMap) {
		super.addMap(areaMap);
		this.reInit();
		return;
	}

	public EscapyPhysicsBase reInit() {
		tempControllsHelper = TEMP_EscapyPhysicsPlayerControls.createControlls().initInputs();
		calcThreadOb = new EscapyPhysicsCalculationsThread();
		PHYS_THREAD = new Thread(calcThreadOb);
		initialized = true;
		return this;
	}

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

	public void endPhysics() {
		closePhysic();
		eventObject.clear();
		physObject.clear();
		initialized = false;
		System.gc();
	}

	public boolean isEnded() {
		return ended;
	}

}
