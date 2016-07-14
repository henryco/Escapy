package com.game.executable;

import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Interface EscapyExecutableObjects.
 */
public interface EscapyExecutableObjects extends EscapyExecutable {

	/** The Constant EXE_OBJECT_MAP. */
	public static final HashMap<Short, EscapyExecutableObjects> EXE_OBJECT_MAP = new HashMap<>();

	/**
	 * Action animation.
	 */
	public void actionAnimation();

	/**
	 * Action animation finish.
	 *
	 * @return true, if successful
	 */
	public boolean actionAnimationFinish();

	/**
	 * Action.
	 *
	 * @param type
	 *            the type
	 * @param option
	 *            the option
	 * @param ID
	 *            the id
	 */
	public static void action(int type, String option, short ID) {
		switch (type) {
		case JUST_OBJECT:
			// TODO
			break;

		case DOOR:
			if (option.equalsIgnoreCase(ENTER)) {
				EXE_OBJECT_MAP.get(ID).actionAnimation();
				EscapyExecutable.typeAction(DOOR, EXE_OBJECT_MAP.get(ID));
			}

			if (option.equalsIgnoreCase(INSPECT))
				EscapyExecutableDialogs.setDialogToPrint("Hero: \"Old fullmetal door\"", 3.f);
			if (option.equalsIgnoreCase(KNOCK))
				EscapyExecutableDialogs.setDialogToPrint("Hero: *knocks on the door*", 3.f);
			break;

		case ELEVEATOR:
			// TODO
			break;

		case STATIC_NPC:
			// TODO
			break;
		}
	}

	/**
	 * Inits the executable object.
	 *
	 * @param id
	 *            the id
	 * @param exeobject
	 *            the exeobject
	 */
	public static void initExecutableObject(short id, EscapyExecutableObjects exeobject) {
		EXE_OBJECT_MAP.put(id, exeobject);
	}

}
