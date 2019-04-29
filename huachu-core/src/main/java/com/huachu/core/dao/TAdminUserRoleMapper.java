package com.huachu.core.dao;

import com.huachu.domain.TAdminUserRole;
import org.apache.ibatis.annotations.Param;

public interface TAdminUserRoleMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int insert(TAdminUserRole record);

    int insertSelective(TAdminUserRole record);
    
    int deleteByUserId(@Param("userId") Integer userId);
    
    int countUserByRoleId(@Param("roleId") Integer roleId);
}