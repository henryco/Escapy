package com.x.game.executable;

import java.util.HashMap;

import cern.colt.matrix.ObjectMatrix3D;

public class EscapyExecutableBase {

	private static ObjectMatrix3D map;
	
	private static boolean pressedInArea = false;
	private static int[] mXY = new int[]{0,0};
	protected static boolean uCanShow = false;
	
	private static HashMap<Integer, String[]> optionsMap;
	private static HashMap<Integer, Short> typeMap;
	protected static int actualOptionType = 0;
	private static short actualID = 0;

	protected static float frameWidth, frameHeight, scale;
	
	protected static int actualLocation = 0;
	protected static boolean actionIsPossible = false;
	
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
	
	public static int getActualLocation() {
		return actualLocation;
	}

	public static void setActualLocation(int actualLocation) {
		EscapyExecutableBase.actualLocation = actualLocation;
	}

	public static boolean isInteractionsPossible() 
	{
		if (actionIsPossible)
		{
			actionIsPossible = false;
			return true;
		}
		return false;
	}

	public static void setActionIsPossible(boolean actionIsPossible) {
		EscapyExecutableBase.actionIsPossible = actionIsPossible;
	}

}
