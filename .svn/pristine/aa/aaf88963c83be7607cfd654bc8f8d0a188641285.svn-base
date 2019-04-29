package com.huachu.core.dao;

import com.github.pagehelper.Page;
import com.huachu.domain.TRecyclingList;
import com.huachu.dto.request.query.QueryRecyclingListReqDTO;
import com.huachu.dto.response.RecyclingListRespDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRecyclingListMapper {
    int deleteByPrimaryKey(String recyclingNo);

    int insert(TRecyclingList record);

    int insertSelective(TRecyclingList record);

    TRecyclingList selectByPrimaryKey(String recyclingNo);

    int updateByPrimaryKeySelective(TRecyclingList record);

    int updateByPrimaryKey(TRecyclingList record);
    
    Page<RecyclingListRespDTO> queryList(@Param("condition") QueryRecyclingListReqDTO reqDto);
    
    /**
     * @description 批量查询可派单的回收单信息
     * @param recyclingNoList
     * @return
     */
    List<TRecyclingList> selectByPrimaryKeyList(List<String> recyclingNoList);
    
    /**
     * @description 更新回收单的派单记录
     * @param dispatchId 派单记录ID
     * @param recyclingNo 回收单号
     * @return
     */
    int updateDispatchRecyclingList(@Param("dispatchId") Long dispatchId, @Param("recyclingNo") String recyclingNo);
    
    /***
     * @description 更新回收单的回收状态
     * @param recyclingStatus 0-未回收，1-部分回收，2-已全部回收
     * @param recyclingNo 回收单号
     * @return
     */
    int updateRecyclingListStatus(@Param("recyclingStatus") Short recyclingStatus, @Param("recyclingNo") String recyclingNo);
    
    /***
     * @description 更新回收单的入库状态
     * @param storageStatus 0-未入库，1-部分入库，2-已全部入库
     * @param recyclingNo 回收单号
     * @return
     */
    int updateRecyclingListStorageStatus(@Param("storageStatus") Short storageStatus, @Param("recyclingNo") String recyclingNo);

    /**
     * @description 根据档案号和车牌号获取回收单信息
     * @param fileNo 档案号
     * @param plateNo 回收单号
     * @return
     */
    TRecyclingList selectByUniqueKey(@Param("fileNo") String fileNo, @Param("plateNo") String plateNo);
}