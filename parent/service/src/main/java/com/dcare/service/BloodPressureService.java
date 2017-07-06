package com.dcare.service;

import java.util.Date;
import java.util.List;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.po.BloodPressure;

public interface BloodPressureService {
	AppErrorEnums addBloodPressure(BloodPressure record);
	
	List<BloodPressure> getBloodPresureByFamilyUserId(int familyUserId,int pageNo);
	
	List<BloodPressure> getBloodPressuresBetweenTime(int familyUserId,Date startTime,Date endTime);
}
