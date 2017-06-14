package com.dcare.dao;

import com.dcare.po.UserAdvice;

public interface UserAdviceDO {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAdvice record);

    int insertSelective(UserAdvice record);

    UserAdvice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAdvice record);

    int updateByPrimaryKeyWithBLOBs(UserAdvice record);

    int updateByPrimaryKey(UserAdvice record);
}