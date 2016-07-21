package com.game.physics_temp;

import com.badlogic.gdx.Gdx;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyPhysicsCalculationsThread.
 */
public class EscapyPhysicsCalculationsThread extends EscapyPhysicsSuper
	implements EscapyPhysics, Runnable{

	/** The physics on. */
	protected volatile boolean physicsOn = false;
	
	/**
	 * Instantiates a new escapy physics calculations thread.
	 */
	public EscapyPhysicsCalculationsThread() 
	{
		
	}
	
	/**
	 * Close.
	 */
	public void close()
	{
		physicsOn = false;
	}
	
	/**
	 * Start.
	 */
	public void start()
	{
		physicsOn = true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() 
	{
		for (int i = 0; i < eventObject.size(); i++)
			eventObject.get(i).definePhysicalSystem((EscapyPhysicsObjectSuper) physObject.get(i));
	
		while (physicsOn)
		{	
		
	/*	 
	//	 MIGHT BE REMOVED FROM POSTRUNNABLE FIXME
			
			Gdx.app.postRunnable(new Runnable() {
				
				@Override
				public void run() {
					for (int i = 0; i < eventObject.size(); i++)
					{
						eventObject.get(i).physicalCalculations((EscapyPhysicsObjectSuper) physObject.get(i));
						
						eventObject.get(i).physicalEvent(((EscapyPhysicsObjectSuper) physObject.get(i)).xpos,
									((EscapyPhysicsObjectSuper) physObject.get(i)).ypos, ((EscapyPhysicsObjectSuper) physObject.get(i)).mass,
										((EscapyPhysicsObjectSuper) physObject.get(i)).tetha,(EscapyPhysicsObjectSuper) physObject.get(i));
					}	
				}
			});
		*/	
		
			
			for (int i = 0; i < eventObject.size(); i++)
			{
				eventObject.get(i).physicalCalculations((EscapyPhysicsObjectSuper) physObject.get(i));
				
				eventObject.get(i).physicalEvent(((EscapyPhysicsObjectSuper) physObject.get(i)).xpos,
							((EscapyPhysicsObjectSuper) physObject.get(i)).ypos, ((EscapyPhysicsObjectSuper) physObject.get(i)).mass,
								((EscapyPhysicsObjectSuper) physObject.get(i)).tetha,(EscapyPhysicsObjectSuper) physObject.get(i));
			}	
		
			
			if(physicsOn)
			{
				try {
					Thread.sleep(0, 50000);	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}

		Thread.currentThread().interrupt();
	}
	
}
