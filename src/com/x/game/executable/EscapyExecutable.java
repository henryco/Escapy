package com.x.game.executable;

public interface EscapyExecutable {

	public static final int JUST_OBJECT = 0;
	public static final int DOOR = 1;
	public static final int ELEVEATOR = 2;
	public static final int STATIC_NPC = 3;
	
	
	public static final String ENTER = "enter";
	public static final String INSPECT = "inspect";
	public static final String KNOCK = "knock";
	
	
	
	public static String nameByType(int type)
	{
		switch (type) 
		{
		case JUST_OBJECT:
			return "Just object";
			
		case DOOR:
			return "Door";
			
		case ELEVEATOR:
			return "Elevator";
			
		case  STATIC_NPC:
			return "Living being";
		}
		return " ";
	}
	
	
	public static void typeAction(int type, EscapyExecutableObjects object)
	{
		Thread typeActionThread = new Thread(new Runnable() 
		{
			int typo = type;
			boolean threadFinished = false;
			EscapyExecutableObjects obj = object;
			
			@Override
			public void run() 
			{
				while (!threadFinished)
				{
					if (obj.actionAnimationFinish())
					{	
						Thread.currentThread().interrupt();
						switch (typo) 
						{
						case JUST_OBJECT:
						
							break;
							
						case DOOR:
							doorAction(obj);
							break;
							
						case ELEVEATOR:
							
							break;
							
						case  STATIC_NPC:
							
							break;
						}
						
						threadFinished = true;
						//Thread.currentThread().interrupt();
					}
					if (!threadFinished)
					{
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		typeActionThread.start();
	}
	
	
	public static void doorAction(EscapyExecutableObjects object)
	{
		EscapyExecutableBase.actualLocation = 2;
		EscapyExecutableBase.actionIsPossible = true;
	}
	
}
