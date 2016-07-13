package com.game.userState.settings;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public final class GameSettings {
	private int gameXsize, gameYsize;

	private static String location = "";
	private static int SCREEN_WIDTH, SCREEN_HEIGHT;
	private static double scaleRatio = 1;
	private static double defaultScale = 1;// 1;
	private static boolean mapVisible = false;
	private static boolean vSync = false;

	public GameSettings() {

	}

	public void setDefaultScale(double scalez) {
		defaultScale = scalez;
	}

	public double zoomScaleRatio() {
		return scaleRatio;
	}

	public void setZoomScaleRatio(double scl) {
		scaleRatio = scl;
	}

	public float scaleRatio() {
		return (float) (scaleRatio * defaultScale);
	}

	public int getFrameWIDHT() {
		if ((gameXsize * scaleRatio()) < SCREEN_WIDTH)
			return (int) (gameXsize * scaleRatio());
		else
			return SCREEN_WIDTH;
	}

	public int getFrameHEIGHT() {
		if ((gameYsize * scaleRatio()) < SCREEN_HEIGHT)
			return (int) (gameYsize * scaleRatio());
		else
			return SCREEN_HEIGHT;
	}

	public void defineScreenRes(int x, int y) {
		SCREEN_WIDTH = x;
		SCREEN_HEIGHT = y;
	}

	public int getLocationXsize() {
		return gameXsize;
	}

	public void setLocationXsize(int gameXsize) {
		this.gameXsize = gameXsize;
	}

	public int getLocationYsize() {
		return gameYsize;
	}

	public void setLocationYsize(int gameYsize) {
		this.gameYsize = gameYsize;
	}

	public String Location() {
		return location;
	}

	public void setLocation(String location) {
		GameSettings.location = location;
	}

	public void setGameSizeFF() {
		try {
			RandomAccessFile raf = new RandomAccessFile(new File(Location() + "_.gmd"), "r");
			raf.seek(0);
			setLocationXsize(raf.readShort());
			setLocationYsize(raf.readShort());
			raf.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isMapVisible() {
		return mapVisible;
	}

	public void setMapVisible(boolean mapVisible) {
		GameSettings.mapVisible = mapVisible;
	}

	public boolean isvSync() {
		return vSync;
	}

	public void setvSync(boolean vSync) {
		GameSettings.vSync = vSync;
	}

}
