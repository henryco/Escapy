package com.game.physics_temp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * TEMP CLASS, IN FUTURE THERE WILL NO JUMP IN GAME.
 *
 * @author Henry
 */

public class TEMP_EscapyPhysicsPlayerControls implements Runnable{

	private static boolean isEnd = false;
	private static boolean jumping = false;
	private static boolean flyin = false;
	private static boolean space = false;
	
	/** The inp tr. */
	protected Thread inpTr; 
	
	/**
	 * Instantiates a new TEM P escapy physics player controls.
	 */
	protected TEMP_EscapyPhysicsPlayerControls()
	{
		return;
	}
	
	/**
	 * Creates the controlls.
	 *
	 * @return the TEM P escapy physics player controls
	 */
	public static TEMP_EscapyPhysicsPlayerControls createControlls()
	{
		return new TEMP_EscapyPhysicsPlayerControls();
	}
	
	/**
	 * Inits the inputs.
	 *
	 * @return the TEM P escapy physics player controls
	 */
	public TEMP_EscapyPhysicsPlayerControls initInputs()
	{
		inpTr = new Thread(this);
		return this;
	}
	
	/**
	 * Start thread.
	 */
	public void startThread()
	{
		isEnd = false;
		inpTr.start();
	}
	
	/**
	 * Stop thread.
	 */
	public void stopThread()
	{
		isEnd = true;
		while (inpTr.isInterrupted()){};
	}

	/**
	 * Check space.
	 *
	 * @return true, if successful
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
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
	
	/**
	 * Checks if is space.
	 *
	 * @return true, if is space
	 */
	public static boolean isSpace() {
		return space;
	}
	
	/**
	 * Sets the space.
	 *
	 * @param space
	 *            the new space
	 */
	public static void setSpace(boolean space) {
		TEMP_EscapyPhysicsPlayerControls.space = space;
	}
	
	/**
	 * Checks if is jumping.
	 *
	 * @return true, if is jumping
	 */
	public static boolean isJumping() {
		return jumping;
	}
	
	/**
	 * Sets the jumping.
	 *
	 * @param jumping
	 *            the new jumping
	 */
	public static void setJumping(boolean jumping) {
		TEMP_EscapyPhysicsPlayerControls.jumping = jumping;
	}
	
	/**
	 * Checks if is flyin.
	 *
	 * @return true, if is flyin
	 */
	public static boolean isFlyin() {
		return flyin;
	}
	
	/**
	 * Sets the flyin.
	 *
	 * @param flyin
	 *            the new flyin
	 */
	public static void setFlyin(boolean flyin) {
		TEMP_EscapyPhysicsPlayerControls.flyin = flyin;
	}

}
