package com.dcare.ao;

import java.util.Date;

public class BloodGlucoseListAO {
	
	private int id; //家庭用户id
	
	private Date startTime;
	
	private Date endTime;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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



