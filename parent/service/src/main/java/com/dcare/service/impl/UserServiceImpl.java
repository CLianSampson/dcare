package com.dcare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcare.dao.UserDO;
import com.dcare.po.User;
import com.dcare.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDO userDO;
	
	public User getuserById(int id) {
		
		
		return userDO.selectByPrimaryKey(id);
	}
	
}
