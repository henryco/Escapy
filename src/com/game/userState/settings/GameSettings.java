package com.game.userState.settings;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

// TODO: Auto-generated Javadoc
/**
 * The Class GameSettings.
 */
public final class GameSettings {
	private static int gameXsize, gameYsize;


	public static final int DEFAULT_WIDTH = 1280;
	public static final int DEFAULT_HEIGHT = 720;


	private static String location = "";
	public static int SCREEN_WIDTH, SCREEN_HEIGHT;
	private static double scaleRatio = 1;
	private static double defaultScale = 1;// 1;
	private static boolean mapVisible = false;
	private static boolean vSync = false;

	public static String CFG_DIR_NAME ="/Config";
	public static String LIGHT_CFG_NAME = "/LightCFG.struct";
	public static String OBJECTS_CFG_NAME = "/ObjectsCFG.struct";

	/**
	 * Instantiates a new game settings.
	 */
	public GameSettings() {

	}

	public String getSourceDir() {
		return location.substring(0, location.lastIndexOf("/"));
	}

	public String getObjectsCfgName() {
		return getMapDir() + CFG_DIR_NAME + OBJECTS_CFG_NAME;
	}

	public String getLightCfgUrl() {
		return getMapDir() + CFG_DIR_NAME + LIGHT_CFG_NAME;
	}

	public String getMapDir() {
		String tmp = getSourceDir();
		return tmp.substring(0, tmp.lastIndexOf("/"));
	}

	/**
	 * Sets the default scale.
	 *
	 * @param scalez
	 *            the new default scale
	 */
	public void setDefaultScale(double scalez) {
		defaultScale = scalez;
	}

	/**
	 * Zoom scale ratio.
	 *
	 * @return the double
	 */
	public double zoomScaleRatio() {
		return scaleRatio;
	}

	/**
	 * Sets the zoom scale ratio.
	 *
	 * @param scl
	 *            the new zoom scale ratio
	 */
	public void setZoomScaleRatio(double scl) {
		scaleRatio = scl;
	}

	/**
	 * Scale ratio.
	 *
	 * @return the float
	 */
	public static float scaleRatio() {
		return (float) (scaleRatio * defaultScale);
	}

	/**
	 * Gets the frame WIDHT.
	 *
	 * @return the frame WIDHT
	 */
	public int getFrameWIDHT() {
		return (int)(DEFAULT_WIDTH * scaleRatio());
	}

	/**
	 * Gets the frame HEIGHT.
	 *
	 * @return the frame HEIGHT
	 */
	public int getFrameHEIGHT() {
		return (int)(DEFAULT_HEIGHT * scaleRatio());
	}

	/**
	 * Define screen res.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void defineScreenRes(int x, int y) {
		SCREEN_WIDTH = x;
		SCREEN_HEIGHT = y;
	}

	/**
	 * Gets the location xsize.
	 *
	 * @return the location xsize
	 */
	public int getLocationXsize() {
		return gameXsize;
	}

	/**
	 * Sets the location xsize.
	 *
	 * @param gameXsize
	 *            the new location xsize
	 */
	public void setLocationXsize(int gameXsize) {
		this.gameXsize = gameXsize;
	}

	/**
	 * Gets the location ysize.
	 *
	 * @return the location ysize
	 */
	public int getLocationYsize() {
		return gameYsize;
	}

	/**
	 * Sets the location ysize.
	 *
	 * @param gameYsize
	 *            the new location ysize
	 */
	public void setLocationYsize(int gameYsize) {
		this.gameYsize = gameYsize;
	}

	/**
	 * Location.
	 *
	 * @return the string
	 */
	public String Location() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location
	 *            the new location
	 */
	public void setLocation(String location) {
		GameSettings.location = location;
	}

	/**
	 * Sets the game size FF.
	 */
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

	/**
	 * Checks if is map visible.
	 *
	 * @return true, if is map visible
	 */
	public boolean isMapVisible() {
		return mapVisible;
	}

	/**
	 * Sets the map visible.
	 *
	 * @param mapVisible
	 *            the new map visible
	 */
	public void setMapVisible(boolean mapVisible) {
		GameSettings.mapVisible = mapVisible;
	}

	/**
	 * Checks if is v sync.
	 *
	 * @return true, if is v sync
	 */
	public boolean isvSync() {
		return vSync;
	}

	/**
	 * Sets the v sync.
	 *
	 * @param vSync
	 *            the new v sync
	 */
	public void setvSync(boolean vSync) {
		GameSettings.vSync = vSync;
	}

}
