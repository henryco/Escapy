package com.game.map.weather;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.render.camera.EscapyGdxCamera;
import com.game.utils.arrContainer.EscapyNamedArray;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author Henry on 19/10/16.
 */
public class EscapyWeather {

	private EscapyNamedArray<ParticleEffectPool> weatherArray;
	private ParticleEffectPool actualWeather;
	private ParticleEffectPool.PooledEffect[] actual;

	private Batch batch;
	private final float SCR_W, SCR_H;
	private float[] startPoint = new float[2];
	private String name = "";

	private boolean isActive = false;

	public EscapyWeather(float scrW, float scrH){
		weatherArray = new EscapyNamedArray<>(ParticleEffectPool.class);
		batch = new SpriteBatch();
		SCR_W = scrW;
		SCR_H = scrH;
	}
	public EscapyWeather(float scrW, float scrH, String[] pFile, String[] pImg, String[] names){
		weatherArray = new EscapyNamedArray<>(ParticleEffectPool.class);
		batch = new SpriteBatch();
		SCR_W = scrW;
		SCR_H = scrH;
		load(pFile, pImg, names);
	}
	public EscapyWeather(float scrW, float scrH, String pFile, String pImg, String name){
		weatherArray = new EscapyNamedArray<>(ParticleEffectPool.class);
		batch = new SpriteBatch();
		SCR_W = scrW;
		SCR_H = scrH;
		load(pFile, pImg, name);
	}

	public EscapyWeather setWeather(String name) {
		if (name != null) {
			if (actual != null) {
				Arrays.stream(actual).forEach(ParticleEffectPool.PooledEffect::free);
			}
			actual = new ParticleEffectPool.PooledEffect[3];
			float[] ps = new float[]{ -SCR_W, 0, SCR_W };
			for (int i = 0; i < actual.length; i++) {
				actual[i] = weatherArray.get(name).obtain();
				actual[i].flipY();
				actual[i].setPosition(ps[i] + startPoint[0], startPoint[1] - 50);
			}
		}
		return this;
	}

	public EscapyWeather start(){
		Arrays.stream(actual).forEach(ParticleEffect::start);
		isActive = true;
		return this;
	}

	public EscapyWeather end(int endDur){
		Arrays.stream(actual).forEach(a -> a.setDuration(endDur));
		isActive = false;
		return this;
	}

	public EscapyWeather reset() {
		if (actual != null) Arrays.stream(actual).forEach(ParticleEffect::reset);
		return this;
	}

	public EscapyWeather updateWeather(float delta){
		if (isActive) for (int i = 0; i < 3; i++) actual[i].update(delta);
		return this;
	}

	public EscapyWeather setFunc(Consumer<ParticleEffectPool.PooledEffect> cons) {
		if (actual != null) Arrays.stream(actual).forEach(cons);
		return this;
	}

	public void renderWeather(EscapyGdxCamera camera) {

		if (isActive) {
			float cm_pos_x = camera.getCamera().position.x;
			float cm_pos_y = camera.getCamera().position.y;
			int n = (int) Math.floor(cm_pos_x / SCR_W);

			batch.begin();
			if ((n+3) %3 == 0) draw1(n, cm_pos_x, cm_pos_y, camera);
			else if ((n+2) %3 == 0) draw2(n, cm_pos_x, cm_pos_y, camera);
			else if ((n+1) %3 == 0) draw3(n, cm_pos_x, cm_pos_y, camera);
			batch.end();

			camera.setCameraPosition(cm_pos_x, cm_pos_y);
		}
	}

	private void draw1(int n, float px, float py, EscapyGdxCamera camera){

		camera.setCameraPosition(px - (SCR_W * (n - 0.5f)), py);
		batch.setProjectionMatrix(camera.combined());
		actual[1].draw(batch);
		actual[2].draw(batch);
	}
	private void draw2(int n, float px, float py, EscapyGdxCamera camera){

		camera.setCameraPosition(px - (SCR_W * (n - 0.5f)) - SCR_W, py);
		batch.setProjectionMatrix(camera.combined());
		actual[0].draw(batch);
		actual[1].draw(batch);
	}
	private void draw3(int n, float px, float py, EscapyGdxCamera camera) {

		camera.setCameraPosition(px - (SCR_W * (n - 0.5f)) + SCR_W, py);
		batch.setProjectionMatrix(camera.combined());
		actual[2].draw(batch);
		camera.setCameraPosition(px - (SCR_W * (n - 0.5f)) - (3 * SCR_W), py);
		batch.setProjectionMatrix(camera.combined());
		actual[0].draw(batch);
	}

	public EscapyWeather load(String[] pFile, String[] pImg, String[] names) {

		for (int i = 0; i < pFile.length; i++) {

			ParticleEffect tmp = new ParticleEffect();
			tmp.load(new FileHandle(pFile[i]), new FileHandle(pImg[i]));
			tmp.setPosition(0, -50);
			weatherArray.addSource(new ParticleEffectPool(tmp, 0, 10), names[i]);
		}
		return this;
	}
	public EscapyWeather load(String pf, String pi, String nm) {
		return load(new String[]{pf}, new String[]{pi}, new String[]{nm});
	}

	public EscapyWeather setStartPoint(float ... xy) {
		this.startPoint = new float[]{xy[0], xy[1]};
		return this;
	}

	public boolean isActive() {
		return isActive;
	}
	public String getName(){
		return name;
	}
	public EscapyWeather setName(String name) {
		this.name = name;
		return this;
	}


}
