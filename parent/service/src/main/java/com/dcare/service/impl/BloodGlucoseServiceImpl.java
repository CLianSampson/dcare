/**
 * 
 */
package com.dcare.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.common.code.AppErrorEnums;
import com.dcare.dao.BloodGlucoseDO;
import com.dcare.dao.BloodGlucoseTargetDO;
import com.dcare.po.BloodGlucose;
import com.dcare.po.BloodGlucoseTarget;
import com.dcare.service.BloodGlucoseService;

/**
 * @author sampson
 *
 */
@Service
@Transactional
public class BloodGlucoseServiceImpl implements BloodGlucoseService{
	
	@Autowired
	private BloodGlucoseDO bloodGlucoseDO;
	
	@Autowired
	private BloodGlucoseTargetDO bloodGlucoseTargetDO;
	
	public void addBloodGlucose(BloodGlucose bloodGlucose) {
		//应检查familyUserId是否属于该用户
		
		bloodGlucose.setCreatTime(new Date());
		
		bloodGlucoseDO.insert(bloodGlucose);
	}

	public AppErrorEnums update(BloodGlucose bloodGlucose) {
		BloodGlucose recordInDB = bloodGlucoseDO.selectByPrimaryKey(bloodGlucose.getId());
		if (recordInDB.getFamilyUserId().equals(bloodGlucose.getFamilyUserId())) {
			return AppErrorEnums.APP_ERROR_BLOOD_GLUCOSE_FAMILY_USER_CAN_NOT_CHANGE;
		}
		
		
		bloodGlucoseDO.updateByPrimaryKeySelective(bloodGlucose);
		
		return AppErrorEnums.APP_OK;
	}

	
	public List<BloodGlucose> getBloodGlucose(int familyUserId, Date startTime, Date endTime) {
		List<BloodGlucose> list = bloodGlucoseDO.selectByTime(familyUserId, startTime, endTime);
		
		return list;
	}

	
	
	public void addaddBloodGlucoseTarget(BloodGlucoseTarget bloodGlucoseTarget) {
		BloodGlucoseTarget recordInDB = bloodGlucoseTargetDO.selectByUserId(bloodGlucoseTarget.getUserId());
		if (null == recordInDB) {
			//没有则添加
			bloodGlucoseTarget.setCreateTime(new Date());
			bloodGlucoseTargetDO.insert(bloodGlucoseTarget);
		}else {
			//有则更新
			bloodGlucoseTarget.setUpdateTime(new Date());
			
			bloodGlucoseTarget.setId(recordInDB.getId());
			
			bloodGlucoseTargetDO.updateByPrimaryKeySelective(bloodGlucoseTarget);
		}
		
		
	}

	public BloodGlucoseTarget getBloodGlucoseTarget(int userId) {
		
		return bloodGlucoseTargetDO.selectByPrimaryKey(userId);
	}

	

}
