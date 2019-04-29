package com.huachu.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huachu.domain.TRecyclingDispatch;
import com.huachu.dto.response.RecyclingListDispatchRespDTO;

public interface TRecyclingDispatchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TRecyclingDispatch record);

    int insertSelective(TRecyclingDispatch record);

    TRecyclingDispatch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TRecyclingDispatch record);

    int updateByPrimaryKey(TRecyclingDispatch record);
    
    /**
     * @description 查询最新一条的派单记录
     * @param recyclingNo 回收单号
     * @return
     */
    TRecyclingDispatch selectByRecyclingNo(@Param("recyclingNo") String recyclingNo);
    
    /***
     * @description 查询回收单的所有派单记录
     * @param recyclingNo 回收单号
     * @return
     */
    List<RecyclingListDispatchRespDTO> queryListByRecyclingNo(@Param("recyclingNo") String recyclingNo);
    
    /***
     * @description 查询已过期的回收单号列表
     * @return
     */
    List<String> queryExpiredRecyclingNos();
}