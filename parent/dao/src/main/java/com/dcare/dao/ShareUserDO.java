package com.dcare.dao;

import java.util.List;

import com.dcare.po.ShareUser;

public interface ShareUserDO {
    int deleteByPrimaryKey(Integer id);

    int insert(ShareUser record);

    int insertSelective(ShareUser record);

    ShareUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareUser record);

    int updateByPrimaryKey(ShareUser record);
    
    List<ShareUser> selectAllShareUser(int userId);
}