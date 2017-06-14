package com.dcare.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcare.po.Temperature;
import com.dcare.po.TemperatureKey;

public interface TemperatureDO {
    int deleteByPrimaryKey(TemperatureKey key);

    int insert(Temperature record);

    int insertSelective(Temperature record);

    Temperature selectByPrimaryKey(TemperatureKey key);

    int updateByPrimaryKeySelective(Temperature record);

    int updateByPrimaryKeyWithBLOBs(Temperature record);

    int updateByPrimaryKey(Temperature record);
    
    
    List<Temperature> selectByFamilyUerIdAndDate(@Param("familyUserId") int familyUserId, @Param("time") Date time);
}