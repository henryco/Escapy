package com.game.update_loop;

import java.util.ArrayList;

public class UpdateLoopQueue implements Runnable {

	private ArrayList<Updatable> updList;
	private Thread loopThread;
	private volatile boolean inLoop; 
	private long sleep;

	public UpdateLoopQueue() {
		this.updList = new ArrayList<>();
		this.inLoop = false;
		this.sleep = 1;
		return;
	}

	public UpdateLoopQueue addToUpdQueue(Updatable updatableClass) {
		updList.add(updatableClass);
		return this;
	}

	public UpdateLoopQueue removeFromUpdQueue(Updatable updatableClass) {
		if (updList.contains(updatableClass))
			updList.remove(updatableClass);
		return this;
	}

	public UpdateLoopQueue removeFromUpdQueueLast() {
		if (!updList.isEmpty())
			updList.remove(updList.size() - 1);
		return this;
	}

	public Updatable getLastFromUpdQueue() {
		if (!updList.isEmpty())
			return updList.get(updList.size() - 1);
		return null;
	}

	public UpdateLoopQueue startUpdateLoop() {
		this.stopUpdateLoop();

		inLoop = true;
		loopThread = new Thread(this);
		loopThread.start();
		return this;
	}

	public UpdateLoopQueue stopUpdateLoop() {
		if (loopThread != null) {
			inLoop = false;
			loopThread.interrupt();
			while (loopThread.isAlive()) {
				;
			}
		}
		return this;
	}

	public UpdateLoopQueue setSleepTime(long miliseconds) {
		this.sleep = miliseconds;
		return this;
	}

	@Override
	public void run() {
		while (inLoop) {
			for (int i = 0; i < updList.size(); i++)
				updList.get(i).update();
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
