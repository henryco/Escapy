package com.game.executable;

public class EscapyExecutableDialogs implements EscapyExecutable {

	private static StringBuffer dialogToPrint = new StringBuffer(" ");
	private static float posInFrameX = EscapyExecutableBase.frameWidth * 0.25f / EscapyExecutableBase.scale;
	private static float posInFrameY = EscapyExecutableBase.frameHeight * 0.86f / EscapyExecutableBase.scale;
	private static float showTime = 0;
	private static long time0 = 0;
	private static boolean show_it = false;

	public static void drawInLineMonolog() {
		if (!show_it)
			time0 = System.nanoTime();
		else {
			// g.drawString(dialogToPrint.toString(), posInFrameX, posInFrameY);
			show_it = staticTimerStatus(showTime);
		}
	}

	public static void setDialogToPrint(String dialogToPrint, float timeToShow) {
		EscapyExecutableDialogs.dialogToPrint = new StringBuffer(dialogToPrint);
		EscapyExecutableDialogs.showTime = timeToShow;
		EscapyExecutableDialogs.show_it = true;
		time0 = System.nanoTime();
	}

	private static boolean staticTimerStatus(float time) {
		long time1 = System.nanoTime();

		if (((time1 - time0) / 1000000000) < time)
			return true;

		return false;
	}
}
