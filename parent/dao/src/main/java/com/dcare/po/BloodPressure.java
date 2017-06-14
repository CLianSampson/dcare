package com.dcare.po;

import java.util.Date;

public class BloodPressure {
    private Integer id;

    private Integer userId;

    private Integer familyUserId;

    private Float systolic;

    private Float diastolic;

    private Float heartRate;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFamilyUserId() {
        return familyUserId;
    }

    public void setFamilyUserId(Integer familyUserId) {
        this.familyUserId = familyUserId;
    }

    public Float getSystolic() {
        return systolic;
    }

    public void setSystolic(Float systolic) {
        this.systolic = systolic;
    }

    public Float getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Float diastolic) {
        this.diastolic = diastolic;
    }

    public Float getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Float heartRate) {
        this.heartRate = heartRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}