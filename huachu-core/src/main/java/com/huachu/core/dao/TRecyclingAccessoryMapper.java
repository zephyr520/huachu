package com.huachu.core.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.huachu.domain.TRecyclingAccessory;
import com.huachu.dto.request.query.QueryRecyclingAccessoryReqDTO;
import com.huachu.dto.response.RecyclingAccessoryRespDTO;

public interface TRecyclingAccessoryMapper {
	int deleteByPrimaryKey(Long id);

	int insert(TRecyclingAccessory record);

	int insertSelective(TRecyclingAccessory record);

	TRecyclingAccessory selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TRecyclingAccessory record);

	int updateByPrimaryKey(TRecyclingAccessory record);
	
	int insertBatch(List<TRecyclingAccessory> recordList);

	/***
	 * @description 更新配件的派单信息
	 * @param accessoryRecyclingUserId
	 * @param recyclingNo
	 * @return
	 */
	int updateDispatchRecyclingAccessory(@Param("accessoryRecyclingUserId") Integer accessoryRecyclingUserId,
			@Param("recyclingNo") String recyclingNo);

	Page<RecyclingAccessoryRespDTO> queryList(QueryRecyclingAccessoryReqDTO reqDto);

	/*****
	 * @description 标记配件回收失败或撤销配件回收失败
	 * @param id
	 * @param oldIfRecyclingFailed
	 * @param ifRecyclingFailed
	 * @return
	 */
	int updateRecyclingAccessoryFailure(@Param("id") Long id,
			@Param("oldIfRecyclingFailed") Boolean oldIfRecyclingFailed,
			@Param("ifRecyclingFailed") Boolean ifRecyclingFailed);

	/***
	 * @description 标记配件回收时需要拍照或撤销配件回收时需要拍照操作
	 * @param id
	 * @param oldIfTakePhoto
	 * @param ifTakePhoto
	 * @return
	 */
	int updateRecyclingAccessoryTakePhoto(@Param("id") Long id, @Param("oldIfTakePhoto") Boolean oldIfTakePhoto,
			@Param("ifTakePhoto") Boolean ifTakePhoto);

	/**
	 * @description 更新配件的回收状态
	 * @param id
	 * @param accessoryStatus 更新后的状态
	 * @param oldAccessoryStatus 更新前的状态
	 * @param accessoryRecyclingTime 回收时间
	 * @return
	 */
	int updateAccessoryRecyclingStatus(@Param("id") Long id, @Param("accessoryStatus") Short accessoryStatus,
			@Param("oldAccessoryStatus") Short oldAccessoryStatus, @Param("accessoryRecyclingTime") Date accessoryRecyclingTime);
	
	/**
	 * @description 根据回收单获取配件信息
	 * @param recyclingNo
	 * @return
	 */
	List<TRecyclingAccessory> queryAccessoryListByRecyclingNo(@Param("recyclingNo") String recyclingNo);
	
	/**
	 * @description 查询未回收的配件列表
	 * @param recyclingNo
	 * @return
	 */
	List<TRecyclingAccessory> queryNotRecycledAccessoryList(@Param("recyclingNo") String recyclingNo);
	
	/**
	 * @description 查询已回收的配件列表（包含已回收未入库和已入库的配件）
	 * @param recyclingNo
	 * @return
	 */
	List<TRecyclingAccessory> queryRecycledAccessoryList(@Param("recyclingNo") String recyclingNo);
	
	/***
	 * @description 更新配件的入库状态
	 * @param id  配件ID
	 * @param accessoryStatus 更新后的配件状态(已入库)
	 * @param accessoryStorageUserId 入库员用户ID
	 * @param oldAccessoryStatus 配件入库之前的状态(已回收未入库)
	 * @param accessoryStorageTime 配件入库时间
	 * @return
	 */
	int updateAccessoryStorageStatus(@Param("id") Long id, @Param("accessoryStatus") Short accessoryStatus, @Param("accessoryStorageUserId") Integer accessoryStorageUserId,
			@Param("oldAccessoryStatus") Short oldAccessoryStatus, @Param("accessoryStorageTime") Date accessoryStorageTime);
	
	/***
	 * @description 查询未入库的配件列表
	 * @param recyclingNo 回收单号
	 * @return
	 */
	List<TRecyclingAccessory> queryNotStorageAccessoryList(@Param("recyclingNo") String recyclingNo);
	
	/**
	 * @description 查询已入库的配件列表
	 * @param recyclingNo 回收单号
	 * @return
	 */
	List<TRecyclingAccessory> queryStorageAccessoryList(@Param("recyclingNo") String recyclingNo);
	
	/**
	 * @description 回收派单过期的配件
	 * @param recyclingNo
	 * @return
	 */
	int accessoryDispatchRecycling(@Param("recyclingNo") String recyclingNo);
}