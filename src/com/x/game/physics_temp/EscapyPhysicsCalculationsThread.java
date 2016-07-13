package com.x.game.physics_temp;

public class EscapyPhysicsCalculationsThread extends EscapyPhysicsSuper
	implements EscapyPhysics, Runnable{

	protected volatile boolean physicsOn = false;
	
	public EscapyPhysicsCalculationsThread() 
	{
		
	}
	
	public void close()
	{
		physicsOn = false;
	}
	public void start()
	{
		physicsOn = true;
	}

	@Override
	public void run() 
	{
		for (int i = 0; i < eventObject.size(); i++)
			eventObject.get(i).definePhysicalSystem((EscapyPhysicsObjectSuper) physObject.get(i));
	
		while (physicsOn)
		{	
		/**
		 
		 MIGHT BE REMOVED FROM POSTRUNNABLE FIXME
			
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
			
		**/
			
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
