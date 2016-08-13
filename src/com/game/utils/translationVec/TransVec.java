package com.game.utils.translationVec;

import java.util.function.Function;

import com.badlogic.gdx.math.Vector2;
import com.game.utils.observ.SimpleObserver;

// TODO: Auto-generated Javadoc
/**
 * The Class TransVec.
 */
public class TransVec {

	private float[] translationVectorArray;
	private Vector2 translationVector;
	
	private int accuracy;
	
	private SimpleObserver<TransVec> observeObj;
	
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
		this.setTransVec(vec2);
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
		this.setTransVec(vec2);
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
		this.setTransVec(x, y);
		return;
	}
	
	public TransVec(TransVec Vec) {
		this.initVec();
		this.setTransVec(Vec.getTransVec());
		return;
	}
	
	
	private void initVec() {
		this.translationVectorArray = new float[2];
		this.translationVector = new Vector2(0, 0);
		this.accuracy = (-1);
	}
	
	public void setObservedObj(SimpleObserver<TransVec> observed) {
		this.observeObj = observed;
	}
	
	/**
	 * Sets the accuracy.
	 *
	 * @param acc
	 *            - dot accuracy, full lenght if (-1)
	 * @return the trans vec
	 */
	public TransVec setAccuracy(int acc) {
		if (acc == (-1)) {
			this.accuracy = acc;
			return this;
		}
		int tempAcc = 1;
		for (int i = 0; i < acc; i++){
			tempAcc *= 10;
		}
		this.accuracy = tempAcc;
		return this;
	}
	
	public float func(Function<float[], Float> funct) {
		return funct.apply(this.translationVectorArray).floatValue();
	}
	
	public float[] arrfunc(Function<Float, Float> funct) {
		return new float[]{
				funct.apply(this.translationVectorArray[0]).floatValue(),
				funct.apply(this.translationVectorArray[1]).floatValue()
		};
	}
	
	public TransVec vecfunc(Function<Float, Float> funct) {
		return new TransVec(funct.apply(this.translationVectorArray[0]).floatValue(),
				funct.apply(this.translationVectorArray[1]).floatValue());
	}
	
	/**
	 * Round vec.
	 *
	 * @param dta
	 *            - value for round
	 * @return rounded float
	 */
	protected float roundVec(float dta) {
		
		return (Math.round(dta*((float)this.accuracy))
				/((float)this.accuracy));
	}
	
	/**
	 * Round vec.
	 *
	 * @param dta
	 *             - array for round
	 * @return the float[]
	 */
	protected float[] roundVec(float[] dta) {
		for (int i = 0; i < dta.length; i++)
			dta[i] = roundVec(dta[i]);
		return dta;
	}
	
	/**
	 * Round vec.
	 *
	 * @param dta
	 *            the dta
	 * @return the vector 2
	 */
	protected Vector2 roundVec(Vector2 dta) {
		float x = dta.x;
		float y = dta.y;
		dta.set(roundVec(x), roundVec(y));
		return dta;
	}
	
	/**
	 * Gets the translation vector array.
	 *
	 * @return the translation vector array
	 */
	public float[] getTransVecArray() {
		return translationVectorArray;
	}


	/**
	 * Gets the translation vector.
	 *
	 * @return the translation vector
	 */
	public Vector2 getTransVec() {
		return translationVector;
	}


	/**
	 * Sets the translation vector.
	 *
	 * @param translationMatrix
	 *            the new translation vector
	 */
	public void setTransVec(float[] translationMatrix) {
		if (this.accuracy != (-1)) 	
			translationMatrix = roundVec(translationMatrix);
		this.translationVectorArray = translationMatrix.clone();
		this.translationVector.set(translationMatrix[0], translationMatrix[1]);
		this.x = translationMatrix[0];
		this.y = translationMatrix[1];
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
	}
	
	/**
	 * Sets the translation vector.
	 *
	 * @param translationVector
	 *            the new translation vector
	 */
	public void setTransVec(Vector2 translationVector) {
		if (this.accuracy != (-1)) 
			translationVector = roundVec(translationVector);
		this.translationVector = translationVector.cpy();
		this.translationVectorArray[0] = translationVector.x;
		this.translationVectorArray[1] = translationVector.y;
		this.x = translationVector.x;
		this.y = translationVector.y;
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
	}
	
	/**
	 * Sets the translation vector.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setTransVec(float x, float y) {
		if (this.accuracy != (-1)) {
			x = roundVec(x);
			y = roundVec(y);
		}
		this.x = x;
		this.y = y;
		this.translationVectorArray[0] = this.x;
		this.translationVectorArray[1] = this.y;
		this.translationVector.set(this.x, this.y);
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
	}

	public TransVec sub(TransVec vec) {
		this.sub(vec.x, vec.y);
		return this;
	}
	public TransVec sub(Vector2 v) {
		this.sub(v.x, v.y);
		return this;
	}
	public TransVec sub(float[] v) {
		this.sub(v[0], v[1]);
		return this;
	}
	public TransVec sub(float x, float y) {
		if (this.accuracy != (-1)) {
			x = roundVec(x);
			y = roundVec(y);
		}
		this.x -= x;
		this.y -= y;
		this.translationVector.sub(x, y);
		this.translationVectorArray[0] = this.x;
		this.translationVectorArray[1] = this.y;
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
		return this;
	}
	
	public TransVec add(TransVec vec) {
		this.add(vec.x, vec.y);
		return this;
	}
	public TransVec add(Vector2 v) {
		this.add(v.x, v.y);
		return this;
	}
	public TransVec add(float[] v) {
		this.add(v[0], v[1]);
		return this;
	}
	
	public TransVec add(float x, float y) {
		if (this.accuracy != (-1)) {
			x = roundVec(x);
			y = roundVec(y);
		}
		this.x += x;
		this.y += y;
		this.translationVector.add(x, y);
		this.translationVectorArray[0] = this.x;
		this.translationVectorArray[1] = this.y;
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
		return this;
	}
}
