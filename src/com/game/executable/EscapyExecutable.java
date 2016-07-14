package com.game.executable;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyExecutable.
 */
public interface EscapyExecutable {

	/** The Constant JUST_OBJECT. */
	public static final int JUST_OBJECT = 0;
	
	/** The Constant DOOR. */
	public static final int DOOR = 1;
	
	/** The Constant ELEVEATOR. */
	public static final int ELEVEATOR = 2;
	
	/** The Constant STATIC_NPC. */
	public static final int STATIC_NPC = 3;
	
	
	/** The Constant ENTER. */
	public static final String ENTER = "enter";
	
	/** The Constant INSPECT. */
	public static final String INSPECT = "inspect";
	
	/** The Constant KNOCK. */
	public static final String KNOCK = "knock";
	
	
	
	/**
	 * Name by type.
	 *
	 * @param type
	 *            the type
	 * @return the string
	 */
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
	
	
	/**
	 * Type action.
	 *
	 * @param type
	 *            the type
	 * @param object
	 *            the object
	 */
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
	
	
	/**
	 * Door action.
	 *
	 * @param object
	 *            the object
	 */
	public static void doorAction(EscapyExecutableObjects object)
	{
		EscapyExecutableBase.actualLocation = 2;
		EscapyExecutableBase.actionIsPossible = true;
	}
	
}
