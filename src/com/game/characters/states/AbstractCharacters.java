package com.game.characters.states;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.animator.EscapyAnimatorSuperCharacter;
import com.game.render.EscapyUniRender;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCharacters.
 */
public abstract class AbstractCharacters extends EscapyAnimatorSuperCharacter
	implements EscapyUniRender {

	private float xPos, yPos;
	private float zoom;

	/** The fall img. */
	protected Texture[] standImg, landImg, walkImg,
		runImg, jumpImg, fallImg;

	/** The fall img NRML. */
	protected Texture[] standImgNRML, landImgNRML, walkImgNRML,
		runImgNRML, jumpImgNRML, fallImgNRML;

	protected Texture[] standImgLTMP, landImgLTMP, walkImgLTMP,
		runImgLTMP, jumpImgLTMP, fallImgLTMP;
	
	
	/** The sprite batcher. */
	protected SpriteBatch spriteBatcher;


	/** The land. */
	protected int[] stand, walk, run, jump, fall, land;



	protected int actualFrame;



	public AbstractCharacters(ArrayList<String>[] urls, ArrayList<Integer>[] times, float zoom) {
		super();
		fillDataTabs(urls, times, zoom);
		initializeGraphic();
	}


	protected abstract void initializeGraphic();
	protected abstract Sprite setFrame0(Texture texture);
	protected abstract Sprite setFrame180(Texture texture);

	@Override
	public AbstractCharacters shift() {
		return this;
	}

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
		
		this.standImgNRML = listToPREF(urls[0], "NRML.png");
		this.standImgLTMP = listToPREF(urls[0], "LTMP.png");
		
		this.walkImgNRML = listToPREF(urls[1], "NRML.png");
		this.walkImgLTMP = listToPREF(urls[1], "LTMP.png");
		
		this.runImgNRML = listToPREF(urls[2], "NRML.png");
		this.runImgLTMP = listToPREF(urls[2], "LTMP.png");
		
		this.jumpImgNRML = listToPREF(urls[3], "NRML.png");
		this.jumpImgLTMP = listToPREF(urls[3], "LTMP.png");
		
		this.fallImgNRML = listToPREF(urls[4], "NRML.png");
		this.fallImgLTMP = listToPREF(urls[4], "LTMP.png");
		
		this.landImgNRML = listToPREF(urls[5], "NRML.png");
		this.landImgLTMP = listToPREF(urls[5], "LTMP.png");
		
		this.zoom = zoom;
		this.actualFrame = 0;
	}

	private Texture[] listToImg(ArrayList<String> urls) {
		
		Texture[] img = new Texture[urls.size()];
		Iterator<String> iterator = urls.iterator();
		for (int i = 0; i < img.length; i++) {
			img[i] = new Texture(new FileHandle(iterator.next()));
			img[i].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}	return img;
	}

	private Texture[] listToPREF(ArrayList<String> urls, String postFix) {
		
		Texture[] img = null;
		try {
			img = new Texture[urls.size()];
			Iterator<String> iterator = urls.iterator();
			for (int i = 0; i < img.length; i++) {
				img[i] = new Texture(new FileHandle(this.removePNG(iterator.next()))+postFix);
				img[i].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
			}
		} catch (com.badlogic.gdx.utils.GdxRuntimeException ignored) {
		} return img;
	}
	
	private int[] listToTime(ArrayList<Integer> times) {
		int[] timeTab = new int[times.size()];
		Iterator<Integer> iterator = times.iterator();
		for (int i = 0; i < timeTab.length; i++) {
			timeTab[i] = iterator.next();
		}	return timeTab;
	}


	private long time0 = 0;
	protected Texture[] animation(Texture[] imgg, int[] times) {
		long time1 = System.nanoTime() - time0;
		if (time1 / 1000000.f >= times[actualFrame]) {
			time0 = System.nanoTime();
			actualFrame++;
			if (actualFrame > imgg.length - 1)
				actualFrame = 0;
		}	return imgg;
	}
	protected Texture[] flyAnimation(Texture[] imgg, int[] times) {
		long time1 = System.nanoTime() - time0;
		if (time1 / 1000000.f >= times[actualFrame]) {
			time0 = System.nanoTime();
			actualFrame++;
			if (actualFrame > imgg.length - 1)
				actualFrame -= 1;
		}	return imgg;
	}

	private String removePNG(String url) {
		StringBuilder strb = new StringBuilder(url);
		if (strb.charAt(strb.length()-4) == '.')
			strb.delete(strb.length()-4, strb.length());
		return strb.toString();
	}
	

	public void setXPos(float xPos) {
		this.xPos = xPos;
	}
	public void setYPos(float yPos) {
		this.yPos = yPos;
	}
	public float zoom() {
		return zoom;
	}
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	public float getxPos() {
		return xPos;
	}
	public float getyPos() {
		return yPos;
	}

	public void setPosition(int[] position) {
		this.xPos = position[0];
		this.yPos = position[1];
	}
	public void setPosition(float[] position) {
		this.xPos = position[0];
		this.yPos = position[1];
	}
	public void setPosition(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}


}
