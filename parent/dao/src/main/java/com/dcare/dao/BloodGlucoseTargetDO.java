package com.dcare.dao;

import com.dcare.po.BloodGlucoseTarget;

public interface BloodGlucoseTargetDO {
    int deleteByPrimaryKey(Integer id);

    int insert(BloodGlucoseTarget record);

    int insertSelective(BloodGlucoseTarget record);

    BloodGlucoseTarget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BloodGlucoseTarget record);

    int updateByPrimaryKey(BloodGlucoseTarget record);
    
    BloodGlucoseTarget selectByUserId(Integer userId);
    
}