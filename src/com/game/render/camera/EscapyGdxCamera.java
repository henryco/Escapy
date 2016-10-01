package com.game.render.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.game.render.camera.program.holder.CameraProgramHolder;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyGdxCamera.
 */
public class EscapyGdxCamera {


	private float cam_dt, cam_t;
	private int screenWidth, screenHeight;
	private CameraProgramHolder cameraProgramHolders;
	private OrthographicCamera camera;
	private TransVec translationVec, shiftVec;

	
	{
		this.shiftVec = new TransVec(0f, 0f);
		this.cam_dt = 0;
		this.cam_t = 0.025f;
	}

	/**
	 * Instantiates a new escapy gdx camera.
	 *
	 * @param camera
	 *            the camera
	 * @param screenWidth
	 *            the screen width
	 * @param screenHeight
	 *            the screen height
	 */
	public EscapyGdxCamera(OrthographicCamera camera, int screenWidth, int screenHeight) 
	{
		this.camera = camera;
		this.initGdxCamera(screenWidth, screenHeight);
		return;
	}
	
	/**
	 * Instantiates a new escapy gdx camera.
	 *
	 * @param screenWidth
	 *            the screen width
	 * @param screenHeight
	 *            the screen height
	 */
	public EscapyGdxCamera(int screenWidth, int screenHeight) 
	{
		this.camera = new OrthographicCamera(screenWidth, screenHeight);
		this.camera.setToOrtho(true, screenWidth, screenHeight);
		this.initGdxCamera(screenWidth, screenHeight);
		
		return;
	}

	public EscapyGdxCamera(boolean yDown, int screenWidth, int screenHeight) 
	{
		this.camera = new OrthographicCamera(screenWidth, screenHeight);
		this.camera.setToOrtho(yDown, screenWidth, screenHeight);
		this.initGdxCamera(screenWidth, screenHeight);
		
		return;
	}
	
	private void initGdxCamera(int screenWidth, int screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.cameraProgramHolders = new CameraProgramHolder();
		this.translationVec = new TransVec().setAccuracy(-1);
	}
	
	
	
	/**
	 * Hold camera.
	 *
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera holdCamera() {
		this.setTranslationVector(cameraProgramHolders.holdCamera(camera.position.x, camera.position.y));
		this.translateCamera(this.translationVec.getVecArray());
		this.camera.update();
		this.cam_dt = 0;
		return this;
	}
	
	public EscapyGdxCamera holdCamera(float delta) {
		return ((cam_dt += delta) >= cam_t) ? holdCamera() : this;
	}


	/**
	 * Translate camera.
	 *
	 * @param translation
	 *            the translation
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera translateCamera(float[] translation) {
		this.translateCamera(translation[0], translation[1]);
		return this;
	}

	/**
	 * Translate camera.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera translateCamera(float x, float y) {
		this.shiftVec.add(x, y);
		this.camera.translate(x, y);
		this.camera.update();
		return this;
	}
	
	/**
	 * Translate camera.
	 *
	 * @param translationVec
	 *            the translation vec
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera translateCamera(TransVec translationVec) {
		this.camera.translate(translationVec.x, translationVec.y);
		this.camera.update();
		return this;
	}

	/**
	 * Clear current GL buffer. <br><br>
	 * <b>Gdx.gl.glClearColor(0f, 0f, 0f, 1f); <br>
	 * Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);</b>
	 */
	public void clear()
	{
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * Wipe current GL buffer. <br><br>
	 * <b>Gdx.gl.glClearColor(0f, 0f, 0f, 0f); <br>
	 * Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);</b>
	 */
	public void wipe()
	{
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}


	public void setCam_t(float cam_t) {
		this.cam_t = cam_t;
	}


	/**
	 * Sets the camera position.
	 *
	 * @param position
	 *            the position
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera setCameraPosition(float[] position) {
		return this.setCameraPosition(position[0], position[1]);
	}

	/**
	 * Sets the camera position.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera setCameraPosition(float x, float y) {
		this.translateCamera(x - camera.position.x, y - camera.position.y);
		return this;
	}
	
	/**
	 * Sets the camera position.
	 *
	 * @param position
	 *            the position
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera setCameraPosition(TransVec position) {
		return this.setCameraPosition(position.x, position.y);
	}




	/**
	 * Gets the camera.
	 *
	 * @return the camera
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * Sets the camera.
	 *
	 * @param camera
	 *            the new camera
	 */
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	/**
	 * Gets the camera program holder.
	 *
	 * @return the camera program holder
	 */
	public CameraProgramHolder getCameraProgramHolder() {
		return cameraProgramHolders;
	}

	/**
	 * Sets the camera program holder.
	 *
	 * @param cameraHolders
	 *            the new camera program holder
	 */
	public void setCameraProgramHolder(CameraProgramHolder cameraHolders) {
		this.cameraProgramHolders = cameraHolders;
	}


	/**
	 * Gets the translation vector array.
	 *
	 * @return the translation vector array
	 */
	public float[] getTranslationVectorArray() {
		return this.translationVec.getVecArray();
	}
	
	public void setTranslationVector(float[] translationMatrix)
	{
		this.translationVec.setTransVec(translationMatrix);
	}
	
	/**
	 * Sets the translation vector.
	 *
	 * @param translationVector
	 *            the new translation vector
	 */
	@SuppressWarnings("deprecation")
	public void setTranslationVector(Vector2 translationVector) {
		this.translationVec.setTransVec(translationVector);
	}
	
	/**
	 * Gets the translation vec.
	 *
	 * @return the translation vec
	 */
	public TransVec getTranslationVec() {
		return translationVec;
	}
	
	/**
	 * Sets the translation vec.
	 *
	 * @param translationVec
	 *            the new translation vec
	 */
	public void setTranslationVector(TransVec translationVec) {
		this.translationVec = translationVec;
	}

	public TransVec getShiftVec() {
		return this.shiftVec;
	}
	
	public float[] getShiftVecArr() {
		return this.shiftVec.getVecArray();
	}
	
	/**
	 * Update.
	 */
	public void update() {
		this.camera.update();
	}

	/**
	 * Update.
	 *
	 * @param updateFrustum
	 *            the update frustum
	 */
	public void update(boolean updateFrustum) {
		this.camera.update(updateFrustum);
	}

	/**
	 * Combined.
	 *
	 * @return the combined projection and view matrix 
	 */
	public Matrix4 combined() {
		return this.camera.combined;
	}


	

}
