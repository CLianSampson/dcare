package com.dcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.code.AttributeConst;
import com.dcare.dao.BloodPressureDO;
import com.dcare.dao.FamilyDO;
import com.dcare.po.BloodPressure;
import com.dcare.po.Family;
import com.dcare.service.BloodPressureService;

@Service
@Transactional
public class BloodPressureServiceImpl implements BloodPressureService {
	
	@Autowired
	private BloodPressureDO bloodPressureDO;
	
	@Autowired
	private FamilyDO  familyDO;
	
	public AppErrorEnums addBloodPressure(BloodPressure record) {
		
		Family family = familyDO.selectByPrimaryKey(record.getFamilyUserId());
		if (null == family) {
			return AppErrorEnums.APP_ERROR_FAMILY_MEMBER_NOT_EXIST;
		}
		
		if (!family.getUserId().equals(record.getUserId())) {
			return AppErrorEnums.APP_ERROR_FAMILY_MEMBER_NOT_EXIST;
		}
		
		
		bloodPressureDO.insertSelective(record);
		
		return AppErrorEnums.APP_OK;
	}

	public List<BloodPressure> getBloodPresureByFamilyUserId(int familyUserId, int pageNo) {
		int currentPageNo = 0;
		if (pageNo - 1 >0) {
			currentPageNo = (pageNo - 1) * AttributeConst.DEFAULT_PAGE_SIZE;
		}
		
		int pageSize = currentPageNo + AttributeConst.DEFAULT_PAGE_SIZE;
		
		return bloodPressureDO.selectByFamilyUserId(familyUserId, currentPageNo, pageSize);
	}

	
	
}
