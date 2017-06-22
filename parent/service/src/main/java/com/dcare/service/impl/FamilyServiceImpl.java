package com.dcare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.dcare.common.code.AppErrorEnums;
import com.dcare.dao.FamilyDO;
import com.dcare.po.Family;
import com.dcare.service.FamilyService;

@Service
@Transactional
public class FamilyServiceImpl implements FamilyService{

	
	@Autowired
	private FamilyDO  familyDO;
	
	public AppErrorEnums addFamilyMember(Family family) {
		
		List<Family> families = familyDO.selectByUserId(family.getUserId());
		
		//去掉一个关系只能添加一个人的限制
//		if (null != families && families.size()!=0) {
//			for (Family temp : families) {
//				if (temp.getRelation().equals(family.getRelation())) {
//					return AppErrorEnums.APP_ERROR_FAMILY_RELATION_EXIST;
//				}
//			}
//		}
		
		
		familyDO.insertSelective(family);
		
		return AppErrorEnums.APP_OK;
	}

	public AppErrorEnums deleteFamilyMember(Family record) {
		
		Family family = familyDO.selectByPrimaryKey(record.getId());
		if (null == family) {
			return AppErrorEnums.APP_ERROR_FAMILY_MEMBER_NOT_EXIST;
		}
		
		if (!family.getUserId().equals(record.getUserId())) {
			return AppErrorEnums.APP_ERROR_FAMILY_MEMBER_NOT_EXIST;
		}
		
		familyDO.deleteByPrimaryKey(record.getId());
		
		return AppErrorEnums.APP_OK;
	}

	public AppErrorEnums updateFamilyMerber(Family record) {
		
		Family family = familyDO.selectByPrimaryKey(record.getId());
		if (null == family) {
			return AppErrorEnums.APP_ERROR_FAMILY_MEMBER_NOT_EXIST;
		}
		
		if (!family.getUserId().equals(record.getUserId())) {
			return AppErrorEnums.APP_ERROR_FAMILY_MEMBER_NOT_EXIST;
		}
		
		//本人不能编辑关系
		if (family.getRelation().equals("本人") && !record.getRelation().equals("本人")) {
			return AppErrorEnums.APP_ERROR_FAMILY_CANNOT_EDIT;
		}
		
		familyDO.updateByPrimaryKeySelective(record);
		
		return AppErrorEnums.APP_OK;
		
	}

	public List<Family> getAllFamilyMember(int userId) {
		
		return  familyDO.selectByUserId(userId);
	}
	
	
	
	
	
}
