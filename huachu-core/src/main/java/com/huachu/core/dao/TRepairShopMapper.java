package com.huachu.core.dao;

import com.github.pagehelper.Page;
import com.huachu.domain.TRepairShop;
import com.huachu.dto.request.query.QueryRepairShopReqDTO;
import com.huachu.dto.response.RepairShopRespDTO;
import org.apache.ibatis.annotations.Param;

public interface TRepairShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TRepairShop record);

    int insertSelective(TRepairShop record);

    TRepairShop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRepairShop record);

    int updateByPrimaryKey(TRepairShop record);

    Page<RepairShopRespDTO> queryList(QueryRepairShopReqDTO reqDto);

    TRepairShop selectByRepairShopName(@Param("repairShopName") String repairShopName, @Param("id") Integer id);

    TRepairShop queryByUniqueKey(@Param("repairShopName") String repairShopName);
}