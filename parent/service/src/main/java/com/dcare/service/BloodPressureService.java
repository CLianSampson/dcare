package com.dcare.service;

import java.util.List;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.po.BloodPressure;

public interface BloodPressureService {
	AppErrorEnums addBloodPressure(BloodPressure record);
	
	List<BloodPressure> getBloodPresureByFamilyUserId(int familyUserId,int pageNo);
}
