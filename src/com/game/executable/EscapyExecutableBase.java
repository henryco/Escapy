package com.game.executable;

import java.util.HashMap;

import cern.colt.matrix.ObjectMatrix3D;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyExecutableBase.
 */
public class EscapyExecutableBase {

	private static ObjectMatrix3D map;
	
	private static boolean pressedInArea = false;
	private static int[] mXY = new int[]{0,0};
	
	/** The u can show. */
	protected static boolean uCanShow = false;
	
	private static HashMap<Integer, String[]> optionsMap;
	private static HashMap<Integer, Short> typeMap;
	
	/** The actual option type. */
	protected static int actualOptionType = 0;
	private static short actualID = 0;

	/** The scale. */
	protected static float frameWidth, frameHeight, scale;
	
	/** The actual location. */
	protected static int actualLocation = 0;
	
	/** The action is possible. */
	protected static boolean actionIsPossible = false;
	
	/**
	 * Inits the triggers.
	 *
	 * @param m
	 *            the m
	 * @param frameW
	 *            the frame W
	 * @param frameH
	 *            the frame H
	 * @param scaleRatio
	 *            the scale ratio
	 */
	public static void initTriggers(ObjectMatrix3D m, float frameW, float frameH, float scaleRatio)
	{
		frameWidth = frameW;
		frameHeight = frameH;
		scale = scaleRatio;
		map = m;
		
		optionsMap = EscapyExecutableUtils.createHashMapOfOptionArray();
		typeMap = EscapyExecutableUtils.createHashMapOfOptionTypes();

	}
	
	/*
	
	public static void updTriggers(int translationX, int translationY, boolean mouseReleased, 
			int[] playerPos, int[] mousePos)
	{
		try 
		{
			if ((map.get((int)((mousePos[0]/scale) + translationX), 
					(int) ((mousePos[1]/scale) + translationY), 0)) != null
					&& Byte.toUnsignedInt((byte)map.get((int)((mousePos[0]/scale)+translationX),
							(int)((mousePos[1]/scale)+translationY), 0)) == 2  && !pressedInArea)
			{
				EscapyExecutableMenu.overTheArea = true;
				if (mouseInpt.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				{
					pressedInArea = true;
					
					mXY[0] = (int) ((mouseInpt.getMouseX()/scale) + translationX);
					mXY[1] = (int) ((mouseInpt.getMouseY()/scale) + translationY);
					
					actualID = (short)map.get((int) ((mouseInpt.getMouseX()/scale) + translationX),
							(int) ((mouseInpt.getMouseY()/scale) + translationY), 1);
					
					EscapyExecutableMenu.overTheArea = false;
				}
				actualOptionType = Byte.toUnsignedInt((byte)map.get((int) ((mouseInpt.getMouseX()/scale)
						+ translationX), (int) ((mouseInpt.getMouseY()/scale) + translationY), 2));
			}
			else EscapyExecutableMenu.overTheArea = false;
			
		} catch (IndexOutOfBoundsException exxs) {}
		
		if (mouseReleased)
		{
			//cont.setMouseGrabbed(false);
			pressedInArea = false;
			
			mXY[0] = 0;
			mXY[1] = 0;
			
			if (uCanShow && EscapyExecutableMenu.selectedOption != Integer.MAX_VALUE)
				EscapyExecutableObjects.action(typeMap.get(actualOptionType),
						optionsMap.get(actualOptionType)[EscapyExecutableMenu.selectedOption],
						graphics, actualID);
			
			uCanShow = false;
		}
		if (pressedInArea)
		{
			//cont.setMouseGrabbed(true);
			EscapyExecutableMenu.contextMenu(optionsMap.get(actualOptionType), mXY, 
					(int) (mouseInpt.getMouseY()/scale), (int) (mouseInpt.getMouseX()/scale), imgFont, 
					translationX, translationY, playerPos);
			
			uCanShow = true;
		}
	}
	
	*/
	
	/**
	 * Gets the actual location.
	 *
	 * @return the actual location
	 */
	public static int getActualLocation() {
		return actualLocation;
	}

	/**
	 * Sets the actual location.
	 *
	 * @param actualLocation
	 *            the new actual location
	 */
	public static void setActualLocation(int actualLocation) {
		EscapyExecutableBase.actualLocation = actualLocation;
	}

	/**
	 * Checks if is interactions possible.
	 *
	 * @return true, if is interactions possible
	 */
	public static boolean isInteractionsPossible() 
	{
		if (actionIsPossible)
		{
			actionIsPossible = false;
			return true;
		}
		return false;
	}

	/**
	 * Sets the action is possible.
	 *
	 * @param actionIsPossible
	 *            the new action is possible
	 */
	public static void setActionIsPossible(boolean actionIsPossible) {
		EscapyExecutableBase.actionIsPossible = actionIsPossible;
	}

}
