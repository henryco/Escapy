package com.game.utils.translationVec;

import com.badlogic.gdx.math.Vector2;

// TODO: Auto-generated Javadoc
/**
 * The Class TransVec.
 */
public class TransVec {

	private float[] translationVectorArray;
	private Vector2 translationVector;
	
	/**
	 * Instantiates a new trans vec.
	 */
	public TransVec() {
		this.initVec();
		return;
	}
	
	/**
	 * Instantiates a new trans vec.
	 *
	 * @param vec2
	 *            the vec 2
	 */
	public TransVec(float[] vec2) {
		this.initVec();
		this.setTranslationVector(vec2);
		return;
	}
	
	/**
	 * Instantiates a new trans vec.
	 *
	 * @param vec2
	 *            the vec 2
	 */
	public TransVec(Vector2 vec2) {
		this.initVec();
		this.setTranslationVector(vec2);
		return;
	}
	
	private void initVec() {
		this.translationVectorArray = new float[2];
		this.translationVector = new Vector2(0, 0);
	}
	
	/**
	 * Gets the translation vector array.
	 *
	 * @return the translation vector array
	 */
	public float[] getTranslationVectorArray() {
		return translationVectorArray;
	}


	/**
	 * Gets the translation vector.
	 *
	 * @return the translation vector
	 */
	public Vector2 getTranslationVector() {
		return translationVector;
	}


	/**
	 * Sets the translation vector.
	 *
	 * @param translationMatrix
	 *            the new translation vector
	 */
	public void setTranslationVector(float[] translationMatrix)
	{
		this.translationVectorArray = translationMatrix;
		this.translationVector.x = translationMatrix[0];
		this.translationVector.y = translationMatrix[1];
	}
	
	/**
	 * Sets the translation vector.
	 *
	 * @param translationVector
	 *            the new translation vector
	 */
	public void setTranslationVector(Vector2 translationVector)
	{
		this.translationVector = translationVector;
		this.translationVectorArray[0] = translationVector.x;
		this.translationVectorArray[1] = translationVector.y;
	}
	
	


}
