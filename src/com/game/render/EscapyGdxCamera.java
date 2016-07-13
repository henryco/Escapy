package com.game.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.game.render.camera.holder.CameraProgramHolder;
import com.game.utils.translationVec.TransVec;

public class EscapyGdxCamera {

	private OrthographicCamera camera;
	private float[] xInterval, yInterval;
	private int screenWidth, screenHeight;
	private float[] xIntervalLenght, yIntervalLenght;

	private CameraProgramHolder cameraProgramHolders;

	private TransVec translationVec;
	
	public EscapyGdxCamera() {
		return;
	}
	public EscapyGdxCamera(OrthographicCamera camera, int screenWidth, int screenHeight) 
	{
		this.camera = camera;
		this.initGdxCamera(screenWidth, screenHeight);
		return;
	}
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

	public EscapyGdxCamera translateCamera(float[] translation) {
		this.translateCamera(translation[0], translation[1]);
		return this;
	}

	public EscapyGdxCamera translateCamera(float x, float y) {
		this.camera.translate(x, y);
		this.camera.update();
		return this;
	}

	public void clear()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	public void wipe()
	{
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	
	
	
	
	
	public EscapyGdxCamera setXInterval(float left_prc, float right_prc) {
		this.xInterval = fillTab(xInterval, left_prc, right_prc);
		return this;
	}

	public EscapyGdxCamera setYInterval(float bot_prc, float top_prc) {
		this.yInterval = fillTab(yInterval, bot_prc, top_prc);
		return this;
	}

	public EscapyGdxCamera setCameraPosition(float[] position) {
		return this.setCameraPosition(position[0], position[1]);
	}

	public EscapyGdxCamera setCameraPosition(float x, float y)
	{
		this.camera.position.x = x;
		this.camera.position.y = y;
		return this;
	}

	public int[] getHorizontalBorders() {
		return new int[] { (int) (camera.position.x - xIntervalLenght[0]),
				(int) (camera.position.x + xIntervalLenght[1]) };
	}

	public int[] getVerticalBorders() {
		return new int[] { (int) (camera.position.y - yIntervalLenght[0]),
				(int) (camera.position.y + yIntervalLenght[1]) };
	}

	public float[] getxIntervalLenght() {
		return xIntervalLenght;
	}

	public float[] getyIntervalLenght() {
		return yIntervalLenght;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public CameraProgramHolder getCameraProgramHolder() {
		return cameraProgramHolders;
	}

	public void setCameraProgramHolder(CameraProgramHolder cameraHolders) {
		this.cameraProgramHolders = cameraHolders;
	}

	private float[] fillTab(float[] tab, float a, float b) {
		tab[0] = a;
		tab[1] = b;
		this.updXYintervalLenghts();
		return tab;
	}

	public float[] getTranslationVectorArray() {
		return this.translationVec.getTranslationVectorArray();
	}
	
	public Vector2 getTranslationVector() {
		return this.translationVec.getTranslationVector();
	}

	public void setTranslationVector(float[] translationMatrix)
	{
		this.translationVec.setTranslationVector(translationMatrix);
	}
	
	public void setTranslationVector(Vector2 translationVector)
	{
		this.translationVec.setTranslationVector(translationVector);
	}
	public TransVec getTranslationVec() {
		return translationVec;
	}
	
	public void setTranslationVec(TransVec translationVec) {
		this.translationVec = translationVec;
	}




	

}
