package com.game.map.objectsAlt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.animator.EscapyAnimatorObject;
import com.game.animator.EscapyAnimatorSuperObject;
import com.game.render.EscapyRenderable;

/**
 * @author Henry on 02/10/16.
 */
public abstract class GameObject extends EscapyAnimatorSuperObject
		implements EscapyRenderable {

	public static final class type {

		public static final int STATIC = 0;
		public static final int ANIMATED = 1;
		public static final int INTERACTIVE = 2;
	}

	public static boolean errPrint = false;

	protected Batch spriteBatch = new SpriteBatch();

	public final int ID, objectType;
	protected float[] position;
	protected float defZoom;
	protected String[] textureUrl;


	public GameObject(float x, float y, int iD, String texUrl, float zoom, int type) {

		this.position = new float[]{x, y};
		this.ID = iD;
		this.objectType = type;
		this.textureUrl = new String[]{texUrl};
		this.defZoom = zoom;
		initializeGraphic();
	}

	public float[] getPosition() {return position;}

	protected abstract void initializeGraphic();

	@Override
	public void initObjectAnimator(EscapyAnimatorObject object) {
		if (objectType != type.INTERACTIVE) launchAnimated(object);
	}

	protected static String removePNG(String url) {

		StringBuffer strb = new StringBuffer(url); //.png
		if (strb.charAt(strb.length() - 4) == '.')
			strb.delete(strb.length() - 4, strb.length());
		return strb.toString();
	}

	protected static Sprite makeSpriteFromTexture(TextureRegion texture, float xpos, float ypos, float zoom) {

		Sprite sprite = new Sprite(texture);
		sprite.flip(false, true);
		sprite.setPosition(xpos, ypos);
		sprite.setSize(sprite.getWidth() * zoom, sprite.getHeight() * zoom);

		return sprite;
	}

	protected static Sprite[] makeSpriteArray(Texture texture, float xpos, float ypos, float zoom) {

		Sprite[] arr = new Sprite[10];
		TextureRegion texReg = new TextureRegion(texture, 0, 0,
				(int) ((float) texture.getWidth() / 10.), texture.getHeight());

		for (int i = 0; i < 10; i++) {
			texReg.setRegion((int) ((texture.getWidth() / 10.) * i), 0,
					(int) ((float) texture.getWidth() / 10.), texture.getHeight());
			arr[i] = makeSpriteFromTexture(texReg, xpos, ypos, zoom);
		}	return arr;
	}


}

