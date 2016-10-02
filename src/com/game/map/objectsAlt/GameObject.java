package com.game.map.objectsAlt;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	public static String removePNG(String url) {
		//.png
		StringBuffer strb = new StringBuffer(url);
		if (strb.charAt(strb.length() - 4) == '.')
			strb.delete(strb.length() - 4, strb.length());
		return strb.toString();
	}
}

