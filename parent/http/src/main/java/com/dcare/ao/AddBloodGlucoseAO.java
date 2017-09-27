/**
 * 
 */
package com.dcare.ao;

/**
 * @author sampson
 *
 */
public class AddBloodGlucoseAO {
	private int familyUserId;  //家庭用户id
	
	private float glucose; //血糖值
	
	private int meal; //哪一餐
	
 	private int medicine;  //是否已服药
	
	private String backup; //备注

	
	public int getFamilyUserId() {
		return familyUserId;
	}

	public void setFamilyUserId(int familyUserId) {
		this.familyUserId = familyUserId;
	}

	public float getGlucose() {
		return glucose;
	}

	public void setGlucose(float glucose) {
		this.glucose = glucose;
	}

	public int getMeal() {
		return meal;
	}

	public void setMeal(int meal) {
		this.meal = meal;
	}

	public int getMedicine() {
		return medicine;
	}

	public void setMedicine(int medicine) {
		this.medicine = medicine;
	}

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}
	
}


