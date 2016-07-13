package com.x.game.executable;

public class EscapyExecutableMenu {

	private static float RADIUS = 55.f; // def 40

	private static float[][] textXY;
	private static String[] options;
	protected static int selectedOption = Integer.MAX_VALUE;
	private static boolean option_active = false;
	protected static boolean overTheArea = false;

	private static float alphaN(short numbs, short n) {
		float alpha = ((180.f / (float) (numbs + 1)) * (float) (n));
		return (float) Math.toRadians(alpha);
	}

	private static float[] textPosition(short numbs, short n) {
		return new float[] { (float) ((Math.cos((double) alphaN(numbs, n))) * RADIUS),
				(float) ((Math.sin((double) alphaN(numbs, n))) * RADIUS) };
	}

	protected static void contextMenu(String[] Options, int[] startXY, int currY, int currX, int translationX,
			int translationY, int[] playerPos) {
		textXY = new float[Options.length][2];
		options = Options;
		for (int i = 0; i < Options.length; i++) {
			float[] textPos = textPosition((short) (options.length), (short) (i + 1));
			float xx = (startXY[0] + textPos[1]) - 20.f;
			float yy = (startXY[1] + textPos[0]) - 5.f;

			textXY[i][0] = xx - translationX;
			textXY[i][1] = yy - translationY;

			int dpX = Math.abs(playerPos[0] - startXY[0]);
			int dpY = Math.abs(playerPos[1] - startXY[1]);

			if (dpX <= 120 && dpY <= 150) {
				option_active = true;
				if (currX >= textXY[i][0]) {
					if (i > 0 && i < options.length - 1 && currX >= textXY[i][0]) {
						// if (currY < textXY[i][1] +
						// (font.getHeight(options[i]))
						// && currY >= textXY[i+1][1] +
						// (font.getHeight(options[i+1])))
						{
							selectedOption = i;
						}
					} else if (i == 0) {
						if (currY >= textXY[i][1])
							selectedOption = i;
					} else if (i == options.length - 1) {
						// if (currY <= textXY[i][1] +
						// (font.getHeight(options[i])))
						selectedOption = i;
					}
				} else
					selectedOption = Integer.MAX_VALUE;
			} else {
				selectedOption = Integer.MAX_VALUE;
				option_active = false;
			}

		}
	}

	public static void showContextMenu() {
		if (EscapyExecutableBase.uCanShow) {
			for (int i = 0; i < options.length; i++) {
				if (i != selectedOption && option_active)
					;
				// g.drawString(options[i], textXY[i][0], textXY[i][1]);
				else if (i == selectedOption && option_active)
					;
				// active.drawString(options[i], textXY[i][0], textXY[i][1]);
				else if (i != selectedOption && !option_active)
					;
				// inactive.drawString(options[i], textXY[i][0], textXY[i][1]);
			}
		}
	}

	public static void showPointerInfo() {
		if (overTheArea) {
			// g.drawString(EscapyExecutable.nameByType(EscapyExecutableBase.actualOptionType),
			// EscapyExecutableBase.frameWidth*(0.48f/EscapyExecutableBase.scale),
			// EscapyExecutableBase.frameHeight*(0.06f/EscapyExecutableBase.scale));
			// (EscapyExecutableBase.mouseInpt.getMouseX()+15)/GameConstants.scaleRatio(),
			// (EscapyExecutableBase.mouseInpt.getMouseY()+15)/GameConstants.scaleRatio());
		}
	}

}
