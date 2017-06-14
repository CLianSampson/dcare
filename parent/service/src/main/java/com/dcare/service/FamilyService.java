package com.dcare.service;

import java.util.List;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.po.Family;

public interface FamilyService {
	AppErrorEnums addFamilyMember(Family family);
	
	AppErrorEnums deleteFamilyMember(Family record);
	
	AppErrorEnums updateFamilyMerber(Family record);
	
	List<Family> getAllFamilyMember(int userId);
}
