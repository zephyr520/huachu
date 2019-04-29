package com.huachu.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huachu.domain.TAdminMenu;

public interface TAdminMenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(TAdminMenu record);

    int insertSelective(TAdminMenu record);

    TAdminMenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(TAdminMenu record);

    int updateByPrimaryKey(TAdminMenu record);
    
    List<TAdminMenu> queryAllMenus();
    
    List<TAdminMenu> listAuthMenu(@Param("userId") Integer userId);
}