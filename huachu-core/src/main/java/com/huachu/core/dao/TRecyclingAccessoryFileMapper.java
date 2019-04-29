package com.huachu.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huachu.domain.TRecyclingAccessoryFile;

public interface TRecyclingAccessoryFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TRecyclingAccessoryFile record);

    int insertSelective(TRecyclingAccessoryFile record);

    TRecyclingAccessoryFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRecyclingAccessoryFile record);

    int updateByPrimaryKey(TRecyclingAccessoryFile record);
    
    List<TRecyclingAccessoryFile> selectByAccessoryId(@Param("accessoryId") Long accessoryId);
}