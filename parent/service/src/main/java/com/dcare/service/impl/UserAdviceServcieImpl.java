package com.dcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.dao.UserAdviceDO;
import com.dcare.po.UserAdvice;
import com.dcare.service.UserAdviceService;

@Service
@Transactional
public class UserAdviceServcieImpl implements UserAdviceService {
	
	@Autowired
	private UserAdviceDO userAdviceDO;
	
	
	public void insertUserAdvice(UserAdvice userAdvice) {
		
		userAdviceDO.insertSelective(userAdvice);
	}

}
