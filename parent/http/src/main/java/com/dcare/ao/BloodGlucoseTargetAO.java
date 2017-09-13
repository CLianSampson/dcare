package com.dcare.ao;

public class BloodGlucoseTargetAO {

	private int limosisMin;  //空腹最低值
	
	private int limosisMax;  //空腹最高值
	
	private int beforeMealMin;  //饭前最低值
	
	private int beforeMealMax;	//饭前最高值
	
	private int afterMealMin;	//饭后最低值
	
	private int afterMealMax;	//饭后最高值
	
	private int beforeSleepMin;	//睡前最低值
	
	private int beforeSleepMax; //睡前最高值

	public int getLimosisMin() {
		return limosisMin;
	}

	public void setLimosisMin(int limosisMin) {
		this.limosisMin = limosisMin;
	}

	public int getLimosisMax() {
		return limosisMax;
	}

	public void setLimosisMax(int limosisMax) {
		this.limosisMax = limosisMax;
	}

	public int getBeforeMealMin() {
		return beforeMealMin;
	}

	public void setBeforeMealMin(int beforeMealMin) {
		this.beforeMealMin = beforeMealMin;
	}

	public int getBeforeMealMax() {
		return beforeMealMax;
	}

	public void setBeforeMealMax(int beforeMealMax) {
		this.beforeMealMax = beforeMealMax;
	}

	public int getAfterMealMin() {
		return afterMealMin;
	}

	public void setAfterMealMin(int afterMealMin) {
		this.afterMealMin = afterMealMin;
	}

	public int getAfterMealMax() {
		return afterMealMax;
	}

	public void setAfterMealMax(int afterMealMax) {
		this.afterMealMax = afterMealMax;
	}

	public int getBeforeSleepMin() {
		return beforeSleepMin;
	}

	public void setBeforeSleepMin(int beforeSleepMin) {
		this.beforeSleepMin = beforeSleepMin;
	}

	public int getBeforeSleepMax() {
		return beforeSleepMax;
	}

	public void setBeforeSleepMax(int beforeSleepMax) {
		this.beforeSleepMax = beforeSleepMax;
	}
	
}
