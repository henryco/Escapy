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
	//private Vector2 translationVector;
	private int accuracy;
	private SimpleObserver<TransVec> observeObj;
	
	public final int ID = this.hashCode();
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
		this.setTransVec(vec2[0], vec2[1]);
	
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
		this.setTransVec(Vec.getVecArray()[0], Vec.getVecArray()[1]);
	
		return;
	}
	
	
	private void initVec() {
		this.translationVectorArray = new float[2];
		this.accuracy = (-1);
		
	}
	
	public TransVec setObservedObj(SimpleObserver<TransVec> observed) {
		this.observeObj = observed;
		return this;
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
	
	public TransVec funcf(Function<Float, Float> funct) {
		return this.setTransVec(funct.apply(this.translationVectorArray[0]).floatValue(),
				funct.apply(this.translationVectorArray[1]).floatValue());
	}
	public TransVec funcv(Function<TransVec, TransVec> funct) {
		return this.setTransVec(funct.apply(this));
	}
	public float[] arrfuncf(Function<Float, Float> funct) {
		return new float[]{
				funct.apply(this.translationVectorArray[0]).floatValue(),
				funct.apply(this.translationVectorArray[1]).floatValue()};
	}
	public TransVec vecfuncf(Function<Float, Float> funct) {
		return new TransVec(
				funct.apply(this.translationVectorArray[0]).floatValue(),
				funct.apply(this.translationVectorArray[1]).floatValue());
	}
	public float ffuncv(Function<TransVec, Float> funct) {
		return funct.apply(this).floatValue();
	}
	public float[] arrfuncv(Function<TransVec, float[]> funct) {
		float[] tmp = funct.apply(this);
		return new float[]{tmp[0], tmp[1]};
	}
	public TransVec vecfuncv(Function<TransVec, TransVec> funct) {
		TransVec tmp = new TransVec(this);
		return (funct.apply(tmp));
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
	public float[] getVecArray() {
		return translationVectorArray;
	}


	/**
	 * Sets the translation vector.
	 *
	 * @param translationMatrix
	 *            the new translation vector
	 */
	public TransVec setTransVec(float[] translationMatrix) {
		if (this.accuracy != (-1)) 	
			translationMatrix = roundVec(translationMatrix);
		this.translationVectorArray = translationMatrix.clone();
		this.x = translationMatrix[0];
		this.y = translationMatrix[1];
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
		return this;
	}
	
	/**
	 * Sets the translation vector.
	 * @deprecated
	 * @param translationVector
	 *            the new translation vector
	 */
	public void setTransVec(Vector2 translationVector) {
		if (this.accuracy != (-1)) 
			translationVector = roundVec(translationVector);
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
	 * @return 
	 */
	public TransVec setTransVec(float x, float y) {
		if (this.accuracy != (-1)) {
			x = roundVec(x);
			y = roundVec(y);
		}
		this.x = x;
		this.y = y;
		this.translationVectorArray[0] = this.x;
		this.translationVectorArray[1] = this.y;
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
		return this;
	}
	
	public TransVec setTransVec(TransVec vec) {
		return this.setTransVec(vec.x, vec.y);
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
		this.translationVectorArray[0] = this.x;
		this.translationVectorArray[1] = this.y;
		if (observeObj != null)
			this.observeObj.stateUpdated(this);
		return this;
	}
	
	@Override
	public String toString() {
		return this.ID+"| "+translationVectorArray[0]+
				" : "+translationVectorArray[1]+" ";
	}
}
