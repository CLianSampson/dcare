package com.dcare.ao;

public class BloodGlucoseTargetAO {

	private float limosisMin;  //空腹最低值
	
	private float limosisMax;  //空腹最高值
	
	private float beforeMealMin;  //饭前最低值
	
	private float beforeMealMax;	//饭前最高值
	
	private float afterMealMin;	//饭后最低值
	
	private float afterMealMax;	//饭后最高值
	
	private float beforeSleepMin;	//睡前最低值
	
	private float beforeSleepMax; //睡前最高值

	public float getLimosisMin() {
		return limosisMin;
	}

	public void setLimosisMin(float limosisMin) {
		this.limosisMin = limosisMin;
	}

	public float getLimosisMax() {
		return limosisMax;
	}

	public void setLimosisMax(float limosisMax) {
		this.limosisMax = limosisMax;
	}

	public float getBeforeMealMin() {
		return beforeMealMin;
	}

	public void setBeforeMealMin(float beforeMealMin) {
		this.beforeMealMin = beforeMealMin;
	}

	public float getBeforeMealMax() {
		return beforeMealMax;
	}

	public void setBeforeMealMax(float beforeMealMax) {
		this.beforeMealMax = beforeMealMax;
	}

	public float getAfterMealMin() {
		return afterMealMin;
	}

	public void setAfterMealMin(float afterMealMin) {
		this.afterMealMin = afterMealMin;
	}

	public float getAfterMealMax() {
		return afterMealMax;
	}

	public void setAfterMealMax(float afterMealMax) {
		this.afterMealMax = afterMealMax;
	}

	public float getBeforeSleepMin() {
		return beforeSleepMin;
	}

	public void setBeforeSleepMin(float beforeSleepMin) {
		this.beforeSleepMin = beforeSleepMin;
	}

	public float getBeforeSleepMax() {
		return beforeSleepMax;
	}

	public void setBeforeSleepMax(float beforeSleepMax) {
		this.beforeSleepMax = beforeSleepMax;
	}
	
}
