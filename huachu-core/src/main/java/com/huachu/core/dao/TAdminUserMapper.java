package com.huachu.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.huachu.domain.TAdminUser;
import com.huachu.dto.request.query.QueryAdminUserReqDTO;
import com.huachu.dto.response.AdminUserRespDTO;
import com.huachu.dto.response.RecyclingUserRespDTO;

public interface TAdminUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(TAdminUser record);

    int insertSelective(TAdminUser record);

    TAdminUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(TAdminUser record);

    int updateByPrimaryKey(TAdminUser record);
    
    TAdminUser queryByUsername(@Param("userName") String userName);
    
    TAdminUser queryByPhone(@Param("phone") String phone);
    
    Page<AdminUserRespDTO> queryListByPage(QueryAdminUserReqDTO reqDto);
    
    List<RecyclingUserRespDTO> queryRecyclingUserList(@Param("roleNo") String roleNo);
}