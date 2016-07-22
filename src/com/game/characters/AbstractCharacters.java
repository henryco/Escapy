package com.game.characters;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.animator.EscapyAnimatorSuperCharacter;
import com.game.render.EscapyRenderable;
import com.game.render.extra.normalMap.EscapyNormalMapRender;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCharacters.
 */
public abstract class AbstractCharacters extends EscapyAnimatorSuperCharacter 
	implements EscapyRenderable, EscapyNormalMapRender {

	private float xPos, yPos;
	private float height, width;
	private float mass;
	private float Vx, Vy;
	private float zoom;

	private int HP = 100;
	private int MP = 100;
	private int SP = 100;

	/** The fall img. */
	protected Texture[] standImg, landImg, walkImg,
		runImg, jumpImg, fallImg;

	/** The fall img NRML. */
	protected Texture[] standImgNRML, landImgNRML, walkImgNRML,
		runImgNRML, jumpImgNRML, fallImgNRML;

	/** The sprite batcher. */
	protected SpriteBatch spriteBatcher;

	/** The mov speed. */
	protected float movSpeed;
	
	/** The run speed. */
	protected float runSpeed;

	/** The land. */
	protected int[] stand, walk, run, jump, fall, land;

	/** The last was right. */
	protected boolean lastWasLeft = false, lastWasRight = true;
	private boolean lastMov = false;
	private boolean lastRun = false;
	private boolean lastStand = false;
	
	/** The last fall. */
	protected boolean lastFall = false;
	
	/** The last land. */
	protected boolean lastLand = false;

	/** The actual frame. */
	protected int actualFrame;
	
	/** The time 0. */
	protected long time0 = 0;

	/** The body position. */
	protected int[] bodyPosition = new int[] { 0, 0 };
	protected float[] bodyFloatPosition = new float[]{0,0};

	/**
	 * Instantiates a new abstract characters.
	 *
	 * @param urls
	 *            the urls
	 * @param times
	 *            the times
	 * @param zoom
	 *            the zoom
	 */
	public AbstractCharacters(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		super();
		fillDataTabs(urls, times, zoom);
		initializeGraphic();
		setDefaultSpeed();
	}

	/**
	 * Sets the default speed.
	 */
	public void setDefaultSpeed() {
		this.movSpeed = 1.f;
		this.runSpeed = 11.f;
	}

	/**
	 * Initialize graphic.
	 */
	protected abstract void initializeGraphic();

	/**
	 * Sets the frame 0.
	 *
	 * @param texture
	 *            the texture
	 * @return the sprite
	 */
	protected abstract Sprite setFrame0(Texture texture);

	/**
	 * Sets the frame 180.
	 *
	 * @param texture
	 *            the texture
	 * @return the sprite
	 */
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

	/**
	 * Animation.
	 *
	 * @param imgg
	 *            the imgg
	 * @param times
	 *            the times
	 * @return the texture[]
	 */
	protected Texture[] animation(Texture[] imgg, int[] times) {
		long time1 = System.nanoTime() - time0;
		if ((float) (time1 / 1000000.f) >= times[actualFrame]) {
			time0 = System.nanoTime();
			actualFrame++;
			if (actualFrame > imgg.length - 1)
				actualFrame = 0;
		}	return imgg;
	}

	/**
	 * Fly animation.
	 *
	 * @param imgg
	 *            the imgg
	 * @param times
	 *            the times
	 * @return the texture[]
	 */
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
	
	
	/**
	 * X pos.
	 *
	 * @return the float
	 */
	public float xPos() {
		return xPos;
	}

	/**
	 * Sets the x pos.
	 *
	 * @param xPos
	 *            the new x pos
	 */
	public void setXPos(float xPos) {
		this.xPos = xPos;
	}

	/**
	 * Y pos.
	 *
	 * @return the float
	 */
	public float yPos() {
		return yPos;
	}

	/**
	 * Sets the y pos.
	 *
	 * @param yPos
	 *            the new y pos
	 */
	public void setYPos(float yPos) {
		this.yPos = yPos;
	}

	/**
	 * Height.
	 *
	 * @return the float
	 */
	public float height() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Widht.
	 *
	 * @return the float
	 */
	public float widht() {
		return width;
	}

	/**
	 * Sets the widht.
	 *
	 * @param widht
	 *            the new widht
	 */
	public void setWidht(float widht) {
		this.width = widht;
	}

	/**
	 * Mass.
	 *
	 * @return the float
	 */
	public float mass() {
		return mass;
	}

	/**
	 * Sets the mass.
	 *
	 * @param mass
	 *            the new mass
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	/**
	 * Vx.
	 *
	 * @return the float
	 */
	public float Vx() {
		return Vx;
	}

	/**
	 * Sets the vx.
	 *
	 * @param vx
	 *            the new vx
	 */
	public void setVx(float vx) {
		Vx = vx;
	}

	/**
	 * Vy.
	 *
	 * @return the float
	 */
	public float Vy() {
		return Vy;
	}

	/**
	 * Sets the vy.
	 *
	 * @param vy
	 *            the new vy
	 */
	public void setVy(float vy) {
		Vy = vy;
	}

	/**
	 * Zoom.
	 *
	 * @return the float
	 */
	public float zoom() {
		return zoom;
	}

	/**
	 * Sets the zoom.
	 *
	 * @param zoom
	 *            the new zoom
	 */
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	/**
	 * Hp.
	 *
	 * @return the int
	 */
	public int HP() {
		return HP;
	}

	/**
	 * Sets the hp.
	 *
	 * @param hP
	 *            the new hp
	 */
	public void setHP(int hP) {
		HP = hP;
	}

	/**
	 * Mp.
	 *
	 * @return the int
	 */
	public int MP() {
		return MP;
	}

	/**
	 * Sets the mp.
	 *
	 * @param mP
	 *            the new mp
	 */
	public void setMP(int mP) {
		MP = mP;
	}

	/**
	 * Sp.
	 *
	 * @return the int
	 */
	public int SP() {
		return SP;
	}

	/**
	 * Sets the sp.
	 *
	 * @param sP
	 *            the new sp
	 */
	public void setSP(int sP) {
		SP = sP;
	}

	/**
	 * Last was left.
	 *
	 * @return true, if successful
	 */
	public boolean lastWasLeft() {
		return lastWasLeft;
	}

	/**
	 * Last was right.
	 *
	 * @return true, if successful
	 */
	public boolean lastWasRight() {
		return lastWasRight;
	}

	/**
	 * Sets the leftlast.
	 */
	protected void setLeftlast() {
		lastWasLeft = true;
		lastWasRight = false;
	}

	/**
	 * Sets the rightlast.
	 */
	protected void setRightlast() {
		lastWasRight = true;
		lastWasLeft = false;
	}

	/**
	 * Gets the body position.
	 *
	 * @return the body position
	 */
	public int[] getBodyPosition() {
		bodyPosition[0] = (int) (xPos + (width * 0.52f));
		bodyPosition[1] = (int) (yPos + (height * 0.48f));
		return bodyPosition;
	}
	public float[] getBodyFloatPosition() {
		bodyFloatPosition[0] = (xPos + (width * 0.52f));
		bodyFloatPosition[1] = (yPos + (height * 0.48f));
		return bodyFloatPosition;
	}

	/**
	 * Sets the position.
	 *
	 * @param position
	 *            the new position
	 */
	public void setPosition(int[] position) {
		this.xPos = position[0];
		this.yPos = position[1];
	}

	/**
	 * Gets the mov speed.
	 *
	 * @return the mov speed
	 */
	public float getMovSpeed() {
		return movSpeed;
	}

	/**
	 * Sets the mov speed.
	 *
	 * @param movSpeed
	 *            the new mov speed
	 */
	public void setMovSpeed(float movSpeed) {
		this.movSpeed = movSpeed;
	}

	/**
	 * Gets the run speed.
	 *
	 * @return the run speed
	 */
	public float getRunSpeed() {
		return runSpeed;
	}

	/**
	 * Sets the run speed.
	 *
	 * @param runSpeed
	 *            the new run speed
	 */
	public void setRunSpeed(float runSpeed) {
		this.runSpeed = runSpeed;
	}

	/**
	 * Checks if is last stand.
	 *
	 * @return true, if is last stand
	 */
	public boolean isLastStand() {
		return lastStand;
	}

	/**
	 * Sets the last stand.
	 *
	 * @param lastStand
	 *            the new last stand
	 */
	public void setLastStand(boolean lastStand) {
		this.lastStand = lastStand;
	}

	/**
	 * Checks if is last mov.
	 *
	 * @return true, if is last mov
	 */
	public boolean isLastMov() {
		return lastMov;
	}

	/**
	 * Sets the last mov.
	 *
	 * @param lastMov
	 *            the new last mov
	 */
	public void setLastMov(boolean lastMov) {
		this.lastMov = lastMov;
	}

	/**
	 * Checks if is last run.
	 *
	 * @return true, if is last run
	 */
	public boolean isLastRun() {
		return lastRun;
	}

	/**
	 * Sets the last run.
	 *
	 * @param lastRun
	 *            the new last run
	 */
	public void setLastRun(boolean lastRun) {
		this.lastRun = lastRun;
	}

	/**
	 * Checks if is last fall.
	 *
	 * @return true, if is last fall
	 */
	public boolean isLastFall() {
		return lastFall;
	}

	/**
	 * Checks if is last land.
	 *
	 * @return true, if is last land
	 */
	public boolean isLastLand() {
		return lastLand;
	}

}
