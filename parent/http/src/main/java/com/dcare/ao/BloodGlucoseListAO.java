package com.dcare.ao;

import java.util.Date;

public class BloodGlucoseListAO {
	
	private int familyUserId; //家庭用户id
	
	private Date startTime;
	
	private Date endTime;
	
	

	public int getFamilyUserId() {
		return familyUserId;
	}

	public void setFamilyUserId(int familyUserId) {
		this.familyUserId = familyUserId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

}



