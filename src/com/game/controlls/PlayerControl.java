package com.game.controlls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerControl.
 */
public class PlayerControl implements InputProcessor {

	private boolean KEY_D_Down = false;
	private boolean KEY_A_Down = false;
	private boolean KEY_SPACE_Pressed = false;
	private boolean IS_MOVING = false;
	private boolean KEY_SHIFT_Down = false;
	private boolean KEY_F_Pressed = false;
	private boolean KEY_ESC_Pressed = false;
	private boolean MLB_PRESSED = false, MLP_RELEASED = false;

	/**
	 * Instantiates a new player control.
	 */
	protected PlayerControl() {
		Gdx.input.setInputProcessor(this);
	}

	/**
	 * Player controller.
	 *
	 * @return the player control
	 */
	public static PlayerControl playerController() {
		return new PlayerControl();
	}

	/**
	 * Base keyboard upd.
	 */
	public void baseKeyboard_upd() {
		if (KEY_D_Down == true || KEY_A_Down == true)
			IS_MOVING = true;
		else
			IS_MOVING = false;

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			KEY_ESC_Pressed = true;
		else
			KEY_ESC_Pressed = false;

		if (Gdx.input.isKeyPressed(Input.Keys.F))
			KEY_F_Pressed = true;
		else
			KEY_F_Pressed = false;
	}

	/**
	 * Down A.
	 *
	 * @return true, if successful
	 */
	public boolean down_A() {
		return KEY_A_Down;
	}

	/**
	 * Down D.
	 *
	 * @return true, if successful
	 */
	public boolean down_D() {
		return KEY_D_Down;
	}

	/**
	 * Down SPACE.
	 *
	 * @return true, if successful
	 */
	public boolean down_SPACE() {
		return KEY_SPACE_Pressed;
	}

	/**
	 * Down KE Y LSHIFT.
	 *
	 * @return true, if successful
	 */
	public boolean down_KEY_LSHIFT() {
		return KEY_SHIFT_Down;
	}

	/**
	 * Down KE Y F.
	 *
	 * @return true, if successful
	 */
	public boolean down_KEY_F() {
		return KEY_F_Pressed;
	}

	/**
	 * Checks if is moving.
	 *
	 * @return true, if successful
	 */
	public boolean IS_MOVING() {
		return IS_MOVING;
	}

	/**
	 * Pressed ESC.
	 *
	 * @return true, if successful
	 */
	public boolean pressed_ESC() {
		return KEY_ESC_Pressed;
	}

	/**
	 * Sets the MLB pressed.
	 */
	public void setMLBPressed() {
		MLB_PRESSED = true;
		MLP_RELEASED = false;
	}

	/**
	 * Sets the MLB released.
	 */
	public void setMLBReleased() {
		MLP_RELEASED = true;
		MLB_PRESSED = false;
	}

	/**
	 * Checks if is MLB released.
	 *
	 * @return true, if is MLB released
	 */
	public boolean isMLBReleased() {
		return MLP_RELEASED;
	}

	/**
	 * Checks if is MLB down.
	 *
	 * @return true, if is MLB down
	 */
	public boolean isMLBDown() {
		return MLB_PRESSED;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.D)
			KEY_D_Down = true;

		if (keycode == Input.Keys.A)
			KEY_A_Down = true;

		if (keycode == Input.Keys.SPACE)
			KEY_SPACE_Pressed = true;

		if (keycode == Input.Keys.SHIFT_LEFT)
			KEY_SHIFT_Down = true;

		if (KEY_D_Down == true || KEY_A_Down == true)
			IS_MOVING = true;

		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.D)
			KEY_D_Down = false;

		if (keycode == Input.Keys.A)
			KEY_A_Down = false;

		if (keycode == Input.Keys.SPACE)
			KEY_SPACE_Pressed = false;

		if (keycode == Input.Keys.SHIFT_LEFT)
			KEY_SHIFT_Down = false;

		if (KEY_D_Down == true || KEY_A_Down == true)
			IS_MOVING = false;

		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
