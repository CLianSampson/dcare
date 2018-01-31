package com.dcare.dao;

import com.dcare.po.User;

public interface UserDO {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User selectByPhone(String phone);
    
    User selectByMail(String mail);
    
}