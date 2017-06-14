package com.dcare.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcare.po.BloodPressure;

public interface BloodPressureDO {
    int deleteByPrimaryKey(Integer id);

    int insert(BloodPressure record);

    int insertSelective(BloodPressure record);

    BloodPressure selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BloodPressure record);

    int updateByPrimaryKey(BloodPressure record);
    
    List<BloodPressure> selectByFamilyUserId(@Param("familyUserId") int familyUserId, @Param("pageNo") int pageNo,@Param("pageSize") int pageSize);
}