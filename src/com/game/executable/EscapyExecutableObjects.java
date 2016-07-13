package com.game.executable;

import java.util.HashMap;

public interface EscapyExecutableObjects extends EscapyExecutable {

	public static final HashMap<Short, EscapyExecutableObjects> EXE_OBJECT_MAP = new HashMap<>();

	public void actionAnimation();

	public boolean actionAnimationFinish();

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

	public static void initExecutableObject(short id, EscapyExecutableObjects exeobject) {
		EXE_OBJECT_MAP.put(id, exeobject);
	}

}
