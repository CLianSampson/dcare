package com.dcare.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcare.po.BloodGlucose;

public interface BloodGlucoseDO {
    int deleteByPrimaryKey(Integer id);

    int insert(BloodGlucose record);

    int insertSelective(BloodGlucose record);

    BloodGlucose selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BloodGlucose record);

    int updateByPrimaryKey(BloodGlucose record);
    
    List<BloodGlucose> selectByTime(@Param("familyUserId") int familyUserId,@Param("startTime") Date startTime , @Param("endTime") Date endTime);
}