package com.dcare.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dcare.po.Goods;

public interface GoodsDO {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    
    List<Goods> selectLimitPageNo(@Param("currentPage") int currentPage, @Param("pageNo") int pageNo);
}