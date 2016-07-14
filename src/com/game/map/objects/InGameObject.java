package com.game.map.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.animator.EscapyAnimatorObject;
import com.game.animator.EscapyAnimatorSuperObject;
import com.game.render.EscapyRenderable;

// TODO: Auto-generated Javadoc
/**
 * The Class InGameObject.
 */
public abstract class InGameObject extends EscapyAnimatorSuperObject 
	implements EscapyRenderable{

	private float xPos, yPos;
	private String[] imgUrl;
	private int ID;
	private double defZoom;
	private objectType type;
	
	/** The sprite batcher. */
	protected SpriteBatch spriteBatcher;
	
	/**
	 * Instantiates a new in game object.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param iD
	 *            the i D
	 * @param imgurl
	 *            the imgurl
	 * @param defzoom
	 *            the defzoom
	 * @param typo
	 *            the typo
	 */
	public InGameObject(float x, float y, int iD, String imgurl, double defzoom, int typo) 
	{
		xPos = x;
		yPos = y;
		ID = iD;
		imgUrl = new String[]{imgurl};
		defZoom = defzoom;
		type = IntegerToObjectType(typo);
		initializeGraphic();
	}
	
	/**
	 * Initialize graphic.
	 */
	protected abstract void initializeGraphic();

	/* (non-Javadoc)
	 * @see com.game.animator.EscapyAnimatorSuperObject#initObjectAnimator(com.game.animator.EscapyAnimatorObject)
	 */
	@Override   
	public void initObjectAnimator(EscapyAnimatorObject object)
	{
		if (this.getType() != objectType.Interactive)
			launchAnimated(object);
	}

	/**
	 * The Enum objectType.
	 */
	public enum objectType
	{
		
		/** The Interactive. */
		Interactive(0), 
 /** The Passive animated. */
 PassiveAnimated(1), 
 /** The Passive static. */
 PassiveStatic(2), 
 /** The Front parallaxed. */
 FrontParallaxed(3),
		
		/** The Back parallaxed. */
		BackParallaxed(4), 
 /** The Background. */
 Background(5);
		
		private int type;
		
		private objectType(int typo)
		{
			type = typo;
		}
		
		/**
		 * Gets the object type.
		 *
		 * @return the object type
		 */
		public int getObjectType()
		{
			return type;
		}
		
		/**
		 * Object type name.
		 *
		 * @return the string
		 */
		public String ObjectTypeName()
		{
			return toString();
		}
		
		/**
		 * Sets the type.
		 *
		 * @param typo
		 *            the new type
		 */
		public void setType(int typo)
		{
			type = typo;
		}
		
		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public objectType getType()
		{
			return this;
		}
	}

	/**
	 * Integer to object type.
	 *
	 * @param typo
	 *            the typo
	 * @return the object type
	 */
	public static objectType IntegerToObjectType(int typo)
	{
		switch(typo)
		{
		case 0:
			return objectType.Interactive;
		case 1:
			return objectType.PassiveAnimated;
		case 2:
			return objectType.PassiveStatic;
		case 3:
			return objectType.FrontParallaxed;
		case 4:
			return objectType.BackParallaxed;
		case 5: 
			return objectType.Background;
		}return null;
	}
	
	/**
	 * Removes the PNG.
	 *
	 * @param url
	 *            the url
	 * @return the string
	 */
	public String removePNG(String url) {
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
	public float XPos()
	{
		return xPos;//*2;//*scaleRatio;
	}
	
	/**
	 * Y pos.
	 *
	 * @return the float
	 */
	public float YPos()
	{
		return yPos;//*2;//*scaleRatio;
	}
	
	/**
	 * XY pos.
	 *
	 * @return the float[]
	 */
	public float[] XYPos()
	{
		return new float[]{XPos(),YPos()};
	}
	
	/**
	 * Gets the xpos.
	 *
	 * @return the xpos
	 */
	public double getXpos() {
		return xPos;
	}

	/**
	 * Sets the def xpos.
	 *
	 * @param xPos
	 *            the new def xpos
	 */
	public void setDefXpos(float xPos) {
		this.xPos = xPos;
	}

	/**
	 * Gets the ypos.
	 *
	 * @return the ypos
	 */
	public double getYpos() {
		return yPos;
	}

	/**
	 * Sets the def ypos.
	 *
	 * @param yPos
	 *            the new def ypos
	 */
	public void setDefYpos(float yPos) {
		this.yPos = yPos;
	}

	/**
	 * Gets the img url.
	 *
	 * @return the img url
	 */
	public String[] getImgUrl() {
		return imgUrl;
	}

	/**
	 * Sets the img url.
	 *
	 * @param imgUrl
	 *            the new img url
	 */
	public void setImgUrl(String[] imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Sets the id.
	 *
	 * @param iD
	 *            the new id
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Gets the def zoom.
	 *
	 * @return the def zoom
	 */
	public double getDefZoom() {
		return defZoom;
	}
	
	/**
	 * Zoom.
	 *
	 * @return the double
	 */
	public double zoom()
	{
		return defZoom;//*scaleRatio;
	}

	/**
	 * Sets the def zoom.
	 *
	 * @param defZoom
	 *            the new def zoom
	 */
	public void setDefZoom(double defZoom) {
		this.defZoom = defZoom;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public objectType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(objectType type) {
		this.type = type;
	}

}
