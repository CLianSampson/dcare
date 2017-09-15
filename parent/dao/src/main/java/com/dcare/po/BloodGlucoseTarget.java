package com.dcare.po;

import java.util.Date;

public class BloodGlucoseTarget {
    private Float id;

    private int userId;

    private Float limosisMin;

    private Float limosisMax;

    private Float beforeMealMin;

    private Float beforeMealMax;

    private Float afterMealMin;

    private Float afterMealMax;

    private Float beforeSleepMin;

    private Float beforeSleepMax;

    private Date createTime;

    private Date updateTime;

    public Float getId() {
        return id;
    }

    public void setId(Float id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Float getLimosisMin() {
        return limosisMin;
    }

    public void setLimosisMin(Float limosisMin) {
        this.limosisMin = limosisMin;
    }

    public Float getLimosisMax() {
        return limosisMax;
    }

    public void setLimosisMax(Float limosisMax) {
        this.limosisMax = limosisMax;
    }

    public Float getBeforeMealMin() {
        return beforeMealMin;
    }

    public void setBeforeMealMin(Float beforeMealMin) {
        this.beforeMealMin = beforeMealMin;
    }

    public Float getBeforeMealMax() {
        return beforeMealMax;
    }

    public void setBeforeMealMax(Float beforeMealMax) {
        this.beforeMealMax = beforeMealMax;
    }

    public Float getAfterMealMin() {
        return afterMealMin;
    }

    public void setAfterMealMin(Float afterMealMin) {
        this.afterMealMin = afterMealMin;
    }

    public Float getAfterMealMax() {
        return afterMealMax;
    }

    public void setAfterMealMax(Float afterMealMax) {
        this.afterMealMax = afterMealMax;
    }

    public Float getBeforeSleepMin() {
        return beforeSleepMin;
    }

    public void setBeforeSleepMin(Float beforeSleepMin) {
        this.beforeSleepMin = beforeSleepMin;
    }

    public Float getBeforeSleepMax() {
        return beforeSleepMax;
    }

    public void setBeforeSleepMax(Float beforeSleepMax) {
        this.beforeSleepMax = beforeSleepMax;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}