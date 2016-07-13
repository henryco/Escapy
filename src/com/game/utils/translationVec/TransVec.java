package com.game.utils.translationVec;

import com.badlogic.gdx.math.Vector2;

public class TransVec {

	private float[] translationVectorArray;
	private Vector2 translationVector;
	
	public TransVec() {
		this.initVec();
		return;
	}
	
	public TransVec(float[] vec2) {
		this.initVec();
		this.setTranslationVector(vec2);
		return;
	}
	
	public TransVec(Vector2 vec2) {
		this.initVec();
		this.setTranslationVector(vec2);
		return;
	}
	
	private void initVec() {
		this.translationVectorArray = new float[2];
		this.translationVector = new Vector2(0, 0);
	}
	
	public float[] getTranslationVectorArray() {
		return translationVectorArray;
	}


	public Vector2 getTranslationVector() {
		return translationVector;
	}


	public void setTranslationVector(float[] translationMatrix)
	{
		this.translationVectorArray = translationMatrix;
		this.translationVector.x = translationMatrix[0];
		this.translationVector.y = translationMatrix[1];
	}
	
	public void setTranslationVector(Vector2 translationVector)
	{
		this.translationVector = translationVector;
		this.translationVectorArray[0] = translationVector.x;
		this.translationVectorArray[1] = translationVector.y;
	}
	
	


}
