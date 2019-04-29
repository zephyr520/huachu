package com.huachu.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.huachu.domain.TAdminRole;
import com.huachu.dto.request.query.QueryAdminRoleReqDTO;
import com.huachu.dto.response.AdminRoleRespDTO;

public interface TAdminRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(TAdminRole record);

    int insertSelective(TAdminRole record);

    TAdminRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(TAdminRole record);

    int updateByPrimaryKey(TAdminRole record);
    
    List<TAdminRole> selectByUserId(@Param("userId") Integer userId);
    
    List<TAdminRole> queryAllRoles();
    
    Page<AdminRoleRespDTO> queryListByPage(QueryAdminRoleReqDTO reqDto);
}