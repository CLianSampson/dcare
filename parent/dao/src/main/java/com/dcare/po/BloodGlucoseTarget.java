package com.dcare.po;

import java.util.Date;

public class BloodGlucoseTarget {
    private Integer id;

    private Integer userId;

    private Integer limosisMin;

    private Integer limosisMax;

    private Integer beforeMealMin;

    private Integer beforeMealMax;

    private Integer afterMealMin;

    private Integer afterMealMax;

    private Integer beforeSleepMin;

    private Integer beforeSleepMax;

    private Date createTime;

    private Date updateTime;

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

    public Integer getLimosisMin() {
        return limosisMin;
    }

    public void setLimosisMin(Integer limosisMin) {
        this.limosisMin = limosisMin;
    }

    public Integer getLimosisMax() {
        return limosisMax;
    }

    public void setLimosisMax(Integer limosisMax) {
        this.limosisMax = limosisMax;
    }

    public Integer getBeforeMealMin() {
        return beforeMealMin;
    }

    public void setBeforeMealMin(Integer beforeMealMin) {
        this.beforeMealMin = beforeMealMin;
    }

    public Integer getBeforeMealMax() {
        return beforeMealMax;
    }

    public void setBeforeMealMax(Integer beforeMealMax) {
        this.beforeMealMax = beforeMealMax;
    }

    public Integer getAfterMealMin() {
        return afterMealMin;
    }

    public void setAfterMealMin(Integer afterMealMin) {
        this.afterMealMin = afterMealMin;
    }

    public Integer getAfterMealMax() {
        return afterMealMax;
    }

    public void setAfterMealMax(Integer afterMealMax) {
        this.afterMealMax = afterMealMax;
    }

    public Integer getBeforeSleepMin() {
        return beforeSleepMin;
    }

    public void setBeforeSleepMin(Integer beforeSleepMin) {
        this.beforeSleepMin = beforeSleepMin;
    }

    public Integer getBeforeSleepMax() {
        return beforeSleepMax;
    }

    public void setBeforeSleepMax(Integer beforeSleepMax) {
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