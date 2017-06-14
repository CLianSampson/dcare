package com.dcare.dao;


import java.util.List;

import com.dcare.po.Family;

public interface FamilyDO {
    int deleteByPrimaryKey(Integer id);

    int insert(Family record);

    int insertSelective(Family record);

    Family selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Family record);

    int updateByPrimaryKey(Family record);
    
    List<Family> selectByUserId(int userId);
}