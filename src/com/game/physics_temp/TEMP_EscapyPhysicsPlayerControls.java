package com.game.physics_temp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/** 
 * 
 * TEMP CLASS, IN FUTURE THERE WILL NO JUMP IN GAME
 * @author Henry
 * 
 **/

public class TEMP_EscapyPhysicsPlayerControls implements Runnable{

	private static boolean isEnd = false;
	private static boolean jumping = false;
	private static boolean flyin = false;
	private static boolean space = false;
	protected Thread inpTr; 
	
	protected TEMP_EscapyPhysicsPlayerControls()
	{
		return;
	}
	
	public static TEMP_EscapyPhysicsPlayerControls createControlls()
	{
		return new TEMP_EscapyPhysicsPlayerControls();
	}
	
	public TEMP_EscapyPhysicsPlayerControls initInputs()
	{
		inpTr = new Thread(this);
		return this;
	}
	
	public void startThread()
	{
		isEnd = false;
		inpTr.start();
	}
	public void stopThread()
	{
		isEnd = true;
		while (inpTr.isInterrupted()){};
	}

	public boolean checkSpace()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
		{
			if(!flyin && !space)
				setSpace(true);
			else 
				setSpace(false);
		}
		else 
			setSpace(false);		
		return isSpace();
	}

	@Override
	public void run() 
	{
		while(!isEnd)
		{
		//	checkSpace();
			try {
				Thread.sleep(0, 20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Thread.currentThread().interrupt();	
	}
	
	public static boolean isSpace() {
		return space;
	}
	public static void setSpace(boolean space) {
		TEMP_EscapyPhysicsPlayerControls.space = space;
	}
	public static boolean isJumping() {
		return jumping;
	}
	public static void setJumping(boolean jumping) {
		TEMP_EscapyPhysicsPlayerControls.jumping = jumping;
	}
	public static boolean isFlyin() {
		return flyin;
	}
	public static void setFlyin(boolean flyin) {
		TEMP_EscapyPhysicsPlayerControls.flyin = flyin;
	}

}
