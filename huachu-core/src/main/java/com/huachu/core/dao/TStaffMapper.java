package com.huachu.core.dao;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.huachu.domain.TStaff;
import com.huachu.dto.request.query.QueryStaffReqDTO;
import com.huachu.dto.response.StaffRespDTO;

public interface TStaffMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TStaff record);

    int insertSelective(TStaff record);

    TStaff selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TStaff record);

    int updateByPrimaryKey(TStaff record);

    Page<StaffRespDTO> queryList(QueryStaffReqDTO reqDto);
    
    TStaff selectByUniqueKey(@Param("staffPhone") String staffPhone, @Param("id") Integer id);
}