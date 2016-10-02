package com.game.update_loop;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateLoopQueue.
 */
public class UpdateLoopQueue implements Runnable {

	private ArrayList<Updatable> updList;
	private Thread loopThread;
	private volatile boolean inLoop; 
	private long sleep;

	private volatile float delta, syncTime;

	/**
	 * Instantiates a new update loop queue.
	 */
	public UpdateLoopQueue() {
		this.updList = new ArrayList<>();
		this.inLoop = false;
		this.sleep = 1;
	}

	/**
	 * Adds the to upd queue.
	 *
	 * @param updatableClass
	 *            the updatable class
	 * @return the update loop queue
	 */
	public UpdateLoopQueue addToUpdQueue(Updatable updatableClass) {
		updList.add(updatableClass);
		return this;
	}

	/**
	 * Removes the from upd queue.
	 *
	 * @param updatableClass
	 *            the updatable class
	 * @return the update loop queue
	 */
	public UpdateLoopQueue removeFromUpdQueue(Updatable updatableClass) {
		if (updList.contains(updatableClass))
			updList.remove(updatableClass);
		return this;
	}

	/**
	 * Removes the from upd queue last.
	 *
	 * @return the update loop queue
	 */
	public UpdateLoopQueue removeFromUpdQueueLast() {
		if (!updList.isEmpty())
			updList.remove(updList.size() - 1);
		return this;
	}

	/**
	 * Gets the last from upd queue.
	 *
	 * @return the last from upd queue
	 */
	public Updatable getLastFromUpdQueue() {
		if (!updList.isEmpty())
			return updList.get(updList.size() - 1);
		return null;
	}

	/**
	 * Start update loop.
	 *
	 * @return the update loop queue
	 */
	public UpdateLoopQueue startUpdateLoop() {
		this.stopUpdateLoop();

		inLoop = true;
		loopThread = new Thread(this);
		loopThread.start();
		return this;
	}

	/**
	 * Stop update loop.
	 *
	 * @return the update loop queue
	 */
	public UpdateLoopQueue stopUpdateLoop() {
		if (loopThread != null) {
			inLoop = false;
			loopThread.interrupt();
			while (loopThread.isAlive())
				loopThread.interrupt();
		}
		return this;
	}

	/**
	 * Sets the sleep time.
	 *
	 * @param miliseconds
	 *            the miliseconds
	 * @return the update loop queue
	 */
	public UpdateLoopQueue setSleepTime(long miliseconds) {
		this.sleep = miliseconds;
		return this;
	}

	public UpdateLoopQueue setSyncTime(float syncTime) {
		this.syncTime = syncTime;
		return this;
	}

	public synchronized UpdateLoopQueue syncDelta(float delta) {
		this.delta = delta;
		return this;
	}

	private float updSynced(float ud, float dt, float eq) {
		if ((ud += dt) >= eq) {
			updList.forEach(Updatable::update);
			ud = 0;
		}	return ud;
	}

	@Override
	public void run() {
		float upd_dt = 0;
		while (inLoop) {
			//upd_dt = updSynced(upd_dt, delta, syncTime);
			updList.forEach(Updatable::update);
			if (inLoop) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Thread.currentThread().interrupt();
	}

}
