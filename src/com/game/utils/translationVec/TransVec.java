package com.game.utils.translationVec;

import com.badlogic.gdx.math.Vector2;

// TODO: Auto-generated Javadoc
/**
 * The Class TransVec.
 */
public class TransVec {

	private float[] translationVectorArray;
	private Vector2 translationVector;
	
	/** The y. */
	public float x, y;
	
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
	
	/**
	 * Instantiates a new trans vec.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public TransVec(float x, float y) {
		this.initVec();
		this.setTranslationVector(x, y);
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
	public void setTranslationVector(float[] translationMatrix) {
		this.translationVectorArray = translationMatrix;
		this.translationVector.set(translationMatrix[0], translationMatrix[1]);
		this.x = translationMatrix[0];
		this.y = translationMatrix[1];
	}
	
	/**
	 * Sets the translation vector.
	 *
	 * @param translationVector
	 *            the new translation vector
	 */
	public void setTranslationVector(Vector2 translationVector) {
		this.translationVector = translationVector;
		this.translationVectorArray[0] = translationVector.x;
		this.translationVectorArray[1] = translationVector.y;
		this.x = translationVector.x;
		this.y = translationVector.y;
	}
	
	/**
	 * Sets the translation vector.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setTranslationVector(float x, float y) {
		this.x = x;
		this.y = y;
		this.translationVectorArray[0] = this.x;
		this.translationVectorArray[1] = this.y;
		this.translationVector.set(this.x, this.y);
	}


}
