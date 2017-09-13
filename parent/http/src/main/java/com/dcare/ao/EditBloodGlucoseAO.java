/**
 * 
 */
package com.dcare.ao;

/**
 * @author sampson
 *
 */
public class EditBloodGlucoseAO {
	
	private int id;  //家庭用户id
	
	private int glucose; //血糖值
	
	private int meal; //哪一餐
	
 	private int medicine;  //是否已服药
	
	private String backup; //备注
	
	private int recordid; //记录id，对应数据库主键id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGlucose() {
		return glucose;
	}

	public void setGlucose(int glucose) {
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

	public int getRecordid() {
		return recordid;
	}

	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}
	
}
