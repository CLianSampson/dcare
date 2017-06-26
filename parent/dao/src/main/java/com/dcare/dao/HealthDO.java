package com.dcare.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcare.po.Health;

public interface HealthDO {
	int deleteByPrimaryKey();
	
	void insertSelective(Health record);
	
	int updateByPrimaryKeySelective(Health record);
	
	List<Health> selectById(@Param("loreclass") Integer loreclass, @Param("pageNo") Integer pageNo ,@Param("pageSize") Integer pageSize);
}
