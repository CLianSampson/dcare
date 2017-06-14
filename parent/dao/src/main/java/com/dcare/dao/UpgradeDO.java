package com.dcare.dao;

import com.dcare.po.Upgrade;

public interface UpgradeDO {
    int deleteByPrimaryKey(Integer id);

    int insert(Upgrade record);

    int insertSelective(Upgrade record);

    Upgrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Upgrade record);

    int updateByPrimaryKeyWithBLOBs(Upgrade record);

    int updateByPrimaryKey(Upgrade record);
    
    //获取最大版本
    Upgrade selectMaxVersion(Upgrade search);
}