package com.dcare.po;


public class TemperatureKey {
    private Integer userId;

    private String time;

    private Integer familyUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

  
    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getFamilyUserId() {
        return familyUserId;
    }

    public void setFamilyUserId(Integer familyUserId) {
        this.familyUserId = familyUserId;
    }
}