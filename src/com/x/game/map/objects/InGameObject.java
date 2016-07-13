package com.x.game.map.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x.game.animator.EscapyAnimatorObject;
import com.x.game.animator.EscapyAnimatorSuperObject;
import com.x.game.render.EscapyRenderable;

public abstract class InGameObject extends EscapyAnimatorSuperObject 
	implements EscapyRenderable{

	private float xPos, yPos;
	private String[] imgUrl;
	private int ID;
	private double defZoom;
	private objectType type;
	protected SpriteBatch spriteBatcher;
	
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
	
	protected abstract void initializeGraphic();

	@Override   
	public void initObjectAnimator(EscapyAnimatorObject object)
	{
		if (this.getType() != objectType.Interactive)
			launchAnimated(object);
	}

	public enum objectType
	{
		Interactive(0), PassiveAnimated(1), PassiveStatic(2), FrontParallaxed(3),
		BackParallaxed(4), Background(5);
		
		private int type;
		
		private objectType(int typo)
		{
			type = typo;
		}
		public int getObjectType()
		{
			return type;
		}
		public String ObjectTypeName()
		{
			return toString();
		}
		public void setType(int typo)
		{
			type = typo;
		}
		public objectType getType()
		{
			return this;
		}
	}

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
	
	public String removePNG(String url) {
		//.png
		StringBuffer strb = new StringBuffer(url);
		if (strb.charAt(strb.length()-4) == '.')
			strb.delete(strb.length()-4, strb.length());
		return strb.toString();
	}
	
	
	public float XPos()
	{
		return xPos;//*2;//*scaleRatio;
	}
	
	public float YPos()
	{
		return yPos;//*2;//*scaleRatio;
	}
	
	public float[] XYPos()
	{
		return new float[]{XPos(),YPos()};
	}
	
	public double getXpos() {
		return xPos;
	}

	public void setDefXpos(float xPos) {
		this.xPos = xPos;
	}

	public double getYpos() {
		return yPos;
	}

	public void setDefYpos(float yPos) {
		this.yPos = yPos;
	}

	public String[] getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String[] imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getDefZoom() {
		return defZoom;
	}
	
	public double zoom()
	{
		return defZoom;//*scaleRatio;
	}

	public void setDefZoom(double defZoom) {
		this.defZoom = defZoom;
	}

	public objectType getType() {
		return type;
	}

	public void setType(objectType type) {
		this.type = type;
	}

}
