package com.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.game.render.camera.holder.CameraProgramHolder;
import com.game.utils.translationVec.TransVec;

// TODO: Auto-generated Javadoc
/**
 * The Class EscapyGdxCamera.
 */
public class EscapyGdxCamera {

	private OrthographicCamera camera;
	private float[] xInterval, yInterval;
	private int screenWidth, screenHeight;
	private float[] xIntervalLenght, yIntervalLenght;

	private CameraProgramHolder cameraProgramHolders;

	private TransVec translationVec;
	
	/**
	 * Instantiates a new escapy gdx camera.
	 */
	public EscapyGdxCamera() {
		return;
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
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.initGdxCamera(screenWidth, screenHeight);
		
		return;
	}

	private void initGdxCamera(int screenWidth, int screenHeight)
	{
		this.xInterval = new float[2];
		this.yInterval = new float[2];
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.cameraProgramHolders = new CameraProgramHolder();
		this.translationVec = new TransVec();
		this.updXYintervalLenghts();
		
	}
	
	
	
	/**
	 * Hold camera.
	 *
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera holdCamera() 
	{
		this.setTranslationVector(cameraProgramHolders.holdCamera(screenWidth, screenHeight, 1, this));
		this.camera.translate(this.getTranslationVectorArray()[0], this.getTranslationVectorArray()[1]);
		this.camera.update();
		return this;
	}
	
	

	private void updXYintervalLenghts() 
	{
		this.xIntervalLenght = new float[] { (xInterval[0] * screenWidth), (xInterval[1] * screenWidth) };
		this.yIntervalLenght = new float[] { (yInterval[0] * screenHeight), (yInterval[1] * screenHeight) };
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
	
	
	
	
	
	
	/**
	 * Sets the X interval.
	 *
	 * @param left_prc
	 *            the left prc
	 * @param right_prc
	 *            the right prc
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera setXInterval(float left_prc, float right_prc) {
		this.xInterval = fillTab(xInterval, left_prc, right_prc);
		return this;
	}

	/**
	 * Sets the Y interval.
	 *
	 * @param bot_prc
	 *            the bot prc
	 * @param top_prc
	 *            the top prc
	 * @return the escapy gdx camera
	 */
	public EscapyGdxCamera setYInterval(float bot_prc, float top_prc) {
		this.yInterval = fillTab(yInterval, bot_prc, top_prc);
		return this;
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
	public EscapyGdxCamera setCameraPosition(float x, float y)
	{
		this.camera.position.x = x;
		this.camera.position.y = y;
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
	 * Gets the horizontal borders.
	 *
	 * @return the horizontal borders
	 */
	public int[] getHorizontalBorders() {
		return new int[] { (int) (camera.position.x - xIntervalLenght[0]),
				(int) (camera.position.x + xIntervalLenght[1]) };
	}

	/**
	 * Gets the vertical borders.
	 *
	 * @return the vertical borders
	 */
	public int[] getVerticalBorders() {
		return new int[] { (int) (camera.position.y - yIntervalLenght[0]),
				(int) (camera.position.y + yIntervalLenght[1]) };
	}

	/**
	 * Gets the x interval lenght.
	 *
	 * @return the x interval lenght
	 */
	public float[] getxIntervalLenght() {
		return xIntervalLenght;
	}

	/**
	 * Gets the y interval lenght.
	 *
	 * @return the y interval lenght
	 */
	public float[] getyIntervalLenght() {
		return yIntervalLenght;
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

	private float[] fillTab(float[] tab, float a, float b) {
		tab[0] = a;
		tab[1] = b;
		this.updXYintervalLenghts();
		return tab;
	}

	/**
	 * Gets the translation vector array.
	 *
	 * @return the translation vector array
	 */
	public float[] getTranslationVectorArray() {
		return this.translationVec.getTranslationVectorArray();
	}
	
	/**
	 * Gets the translation vector.
	 *
	 * @return the translation vector
	 */
	public Vector2 getTranslationVector() {
		return this.translationVec.getTranslationVector();
	}

	/**
	 * Sets the translation vector.
	 *
	 * @param translationMatrix
	 *            the new translation vector
	 */
	public void setTranslationVector(float[] translationMatrix)
	{
		this.translationVec.setTranslationVector(translationMatrix);
	}
	
	/**
	 * Sets the translation vector.
	 *
	 * @param translationVector
	 *            the new translation vector
	 */
	public void setTranslationVector(Vector2 translationVector)
	{
		this.translationVec.setTranslationVector(translationVector);
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
