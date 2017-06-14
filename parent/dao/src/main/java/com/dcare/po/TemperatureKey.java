package com.dcare.po;

import java.util.Date;

public class TemperatureKey {
    private Integer userId;

    private Date time;

    private Integer familyUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getFamilyUserId() {
        return familyUserId;
    }

    public void setFamilyUserId(Integer familyUserId) {
        this.familyUserId = familyUserId;
    }
}