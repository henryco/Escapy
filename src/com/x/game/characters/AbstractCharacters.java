package com.x.game.characters;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x.game.animator.EscapyAnimatorSuperCharacter;
import com.x.game.render.EscapyRenderable;
import com.x.game.render.extra.normals.EscapyNormalRender;

public abstract class AbstractCharacters extends EscapyAnimatorSuperCharacter 
	implements EscapyRenderable, EscapyNormalRender {

	private float xPos, yPos;
	private float height, width;
	private float mass;
	private float Vx, Vy;
	private float zoom;

	private int HP = 100;
	private int MP = 100;
	private int SP = 100;

	protected Texture[] standImg, landImg, walkImg,
		runImg, jumpImg, fallImg;

	protected Texture[] standImgNRML, landImgNRML, walkImgNRML,
		runImgNRML, jumpImgNRML, fallImgNRML;

	protected SpriteBatch spriteBatcher;

	protected float movSpeed;
	protected float runSpeed;

	protected int[] stand, walk, run, jump, fall, land;

	protected boolean lastWasLeft = false, lastWasRight = true;
	private boolean lastMov = false;
	private boolean lastRun = false;
	private boolean lastStand = false;
	protected boolean lastFall = false;
	protected boolean lastLand = false;

	protected int actualFrame;
	protected long time0 = 0;

	protected int[] bodyPosition = new int[] { 0, 0 };

	public AbstractCharacters(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		super();
		fillDataTabs(urls, times, zoom);
		initializeGraphic();
		setDefaultSpeed();
	}

	public void setDefaultSpeed() {
		this.movSpeed = 1.f;
		this.runSpeed = 11.f;
	}

	protected abstract void initializeGraphic();

	protected abstract Sprite setFrame0(Texture texture);

	protected abstract Sprite setFrame180(Texture texture);

	private void fillDataTabs(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		
		this.standImg = listToImg(urls[0]);
		this.walkImg = listToImg(urls[1]);
		this.runImg = listToImg(urls[2]);
		this.jumpImg = listToImg(urls[3]);
		this.fallImg = listToImg(urls[4]);
		this.landImg = listToImg(urls[5]);
		this.stand = listToTime(times[0]);
		this.walk = listToTime(times[1]);
		this.run = listToTime(times[2]);
		this.jump = listToTime(times[3]);
		this.fall = listToTime(times[4]);
		this.land = listToTime(times[5]);
		
		this.standImgNRML = listToNRML(urls[0]);
		
		this.zoom = zoom;
		this.actualFrame = 0;
	}

	private Texture[] listToImg(ArrayList<String> urls) {
		Texture[] img = new Texture[urls.size()];
		Iterator<String> iterator = urls.iterator();
		for (int i = 0; i < img.length; i++) {
			img[i] = new Texture(new FileHandle(iterator.next()));
			img[i].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		}
		return img;
	}
	
	private Texture[] listToNRML(ArrayList<String> urls) {
		Texture[] img = new Texture[urls.size()];
		Iterator<String> iterator = urls.iterator();
		for (int i = 0; i < img.length; i++) {
			img[i] = new Texture(new FileHandle(this.removePNG(iterator.next()))+"NRML.png");
			img[i].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		}
		return img;
	}
	
	private int[] listToTime(ArrayList<Integer> times) {
		int[] timeTab = new int[times.size()];
		Iterator<Integer> iterator = times.iterator();
		for (int i = 0; i < timeTab.length; i++) {
			timeTab[i] = iterator.next().intValue();
		}	return timeTab;
	}

	protected Texture[] animation(Texture[] imgg, int[] times) {
		long time1 = System.nanoTime() - time0;
		if ((float) (time1 / 1000000.f) >= times[actualFrame]) {
			time0 = System.nanoTime();
			actualFrame++;
			if (actualFrame > imgg.length - 1)
				actualFrame = 0;
		}	return imgg;
	}

	protected Texture[] flyAnimation(Texture[] imgg, int[] times) {
		long time1 = System.nanoTime() - time0;
		if ((float) (time1 / 1000000.f) >= times[actualFrame]) {
			time0 = System.nanoTime();
			actualFrame++;
			if (actualFrame > imgg.length - 1)
				actualFrame -= 1;
		}	return imgg;
	}

	private String removePNG(String url) {
		//.png
		StringBuffer strb = new StringBuffer(url);
		if (strb.charAt(strb.length()-4) == '.')
			strb.delete(strb.length()-4, strb.length());
		return strb.toString();
	}
	
	
	public float xPos() {
		return xPos;
	}

	public void setXPos(float xPos) {
		this.xPos = xPos;
	}

	public float yPos() {
		return yPos;
	}

	public void setYPos(float yPos) {
		this.yPos = yPos;
	}

	public float height() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float widht() {
		return width;
	}

	public void setWidht(float widht) {
		this.width = widht;
	}

	public float mass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float Vx() {
		return Vx;
	}

	public void setVx(float vx) {
		Vx = vx;
	}

	public float Vy() {
		return Vy;
	}

	public void setVy(float vy) {
		Vy = vy;
	}

	public float zoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public int HP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int MP() {
		return MP;
	}

	public void setMP(int mP) {
		MP = mP;
	}

	public int SP() {
		return SP;
	}

	public void setSP(int sP) {
		SP = sP;
	}

	public boolean lastWasLeft() {
		return lastWasLeft;
	}

	public boolean lastWasRight() {
		return lastWasRight;
	}

	protected void setLeftlast() {
		lastWasLeft = true;
		lastWasRight = false;
	}

	protected void setRightlast() {
		lastWasRight = true;
		lastWasLeft = false;
	}

	public int[] getBodyPosition() {
		bodyPosition[0] = (int) (xPos + (width * 0.52f));
		bodyPosition[1] = (int) (yPos + (height * 0.48f));
		return bodyPosition;
	}

	public void setPosition(int[] position) {
		this.xPos = position[0];
		this.yPos = position[1];
	}

	public float getMovSpeed() {
		return movSpeed;
	}

	public void setMovSpeed(float movSpeed) {
		this.movSpeed = movSpeed;
	}

	public float getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(float runSpeed) {
		this.runSpeed = runSpeed;
	}

	public boolean isLastStand() {
		return lastStand;
	}

	public void setLastStand(boolean lastStand) {
		this.lastStand = lastStand;
	}

	public boolean isLastMov() {
		return lastMov;
	}

	public void setLastMov(boolean lastMov) {
		this.lastMov = lastMov;
	}

	public boolean isLastRun() {
		return lastRun;
	}

	public void setLastRun(boolean lastRun) {
		this.lastRun = lastRun;
	}

	public boolean isLastFall() {
		return lastFall;
	}

	public boolean isLastLand() {
		return lastLand;
	}

}
