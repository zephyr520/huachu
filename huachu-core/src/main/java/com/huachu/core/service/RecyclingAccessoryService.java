package com.huachu.core.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.huachu.common.constants.AccessoryStatusEnum;
import com.huachu.common.constants.Constant;
import com.huachu.common.constants.RecyclingEnum;
import com.huachu.common.constants.StorageEnum;
import com.huachu.common.dto.AuthUserDTO;
import com.huachu.common.exception.BOException;
import com.huachu.common.util.BeanUtil;
import com.huachu.common.util.LDTUtil;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.dao.TRecyclingAccessoryFileMapper;
import com.huachu.core.dao.TRecyclingAccessoryMapper;
import com.huachu.core.dao.TRecyclingDispatchMapper;
import com.huachu.core.dao.TRecyclingListMapper;
import com.huachu.domain.TRecyclingAccessory;
import com.huachu.domain.TRecyclingAccessoryFile;
import com.huachu.domain.TRecyclingDispatch;
import com.huachu.dto.request.AccessoryIfRecyclingFailedReqDTO;
import com.huachu.dto.request.RecyclingAccessoryIfTakePhotoReqDTO;
import com.huachu.dto.request.RecyclingAccessoryReqDTO;
import com.huachu.dto.request.query.QueryRecyclingAccessoryReqDTO;
import com.huachu.dto.response.RecyclingAccessoryRespDTO;

@Service
public class RecyclingAccessoryService {

	@Autowired
	TRecyclingAccessoryMapper recyclingAccessoryMapper;
	@Autowired
	TRecyclingAccessoryFileMapper recyclingAccessoryFileMapper;
	@Autowired
	TRecyclingListMapper recyclingListMapper;
	@Autowired
	TRecyclingDispatchMapper recyclingDispatchMapper;

	public Page<RecyclingAccessoryRespDTO> queryList(QueryRecyclingAccessoryReqDTO reqDto, AuthUserDTO authUser) {
		if (!authUser.getRoleNos().contains(Constant.ROLE_ADMIN)) {
			reqDto.setAccessoryRecyclingUserId(authUser.getUserId());
		}
		Page<RecyclingAccessoryRespDTO> page =  recyclingAccessoryMapper.queryList(reqDto);
		PageInfo<RecyclingAccessoryRespDTO> resultPage = page.toPageInfo();
		if (resultPage.getList() != null && !resultPage.getList().isEmpty()) {
			for (RecyclingAccessoryRespDTO dto : resultPage.getList()) {
				List<TRecyclingAccessoryFile> fileList = recyclingAccessoryFileMapper.selectByAccessoryId(dto.getId());
				if (fileList != null && !fileList.isEmpty()) {
					dto.setAccessoryImageList(fileList.stream().map(TRecyclingAccessoryFile::getFileUrl).collect(Collectors.toList()));
				}
			}
		}
		return page;
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryRecyclingFailure(AccessoryIfRecyclingFailedReqDTO reqDto) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(reqDto.getId());
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		Boolean isRecycled = AccessoryStatusEnum.NOT_RECYCLED.getCode().intValue() == oldRecord.getAccessoryStatus().intValue();
		if (!isRecycled) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "只能对未回收的配件标记为回收失败");
		}
		int updateRow = recyclingAccessoryMapper.updateRecyclingAccessoryFailure(reqDto.getId(),
				oldRecord.getIfRecyclingFailed(), reqDto.getIfRecyclingFailed());
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "配件回收失败操作错误");
		}
		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryTakePhoto(RecyclingAccessoryIfTakePhotoReqDTO reqDto) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(reqDto.getId());
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		Boolean isRecycled = AccessoryStatusEnum.NOT_RECYCLED.getCode().intValue() == oldRecord.getAccessoryStatus().intValue();
		if (!isRecycled) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "只能对未回收的配件标记为需要拍照");
		}
		int updateRow = recyclingAccessoryMapper.updateRecyclingAccessoryTakePhoto(reqDto.getId(),
				oldRecord.getIfTakePhoto(), reqDto.getIfTakePhoto());
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "配件回收时是否需要拍照处理操作错误");
		}
		return Boolean.TRUE;
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryHasRecycled(Long id, AuthUserDTO authUser) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(id);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		TRecyclingDispatch dispatchRecord = recyclingDispatchMapper.selectByRecyclingNo(oldRecord.getRecyclingNo());
		if (dispatchRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "此配件未派单，不能标记为已回收");
		}
		if (!LDTUtil.compareDate(dispatchRecord.getExpireDate(), new Date())) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "派单过期，请派单员重新派单");
		}
		Boolean isRecycled = AccessoryStatusEnum.NOT_RECYCLED.getCode().intValue() == oldRecord.getAccessoryStatus().intValue();
		if (!isRecycled) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "只能对未回收的配件进行回收");
		}
		if (!authUser.getRoleNos().contains(Constant.ROLE_ADMIN)) {
			if (!authUser.getUserId().equals(oldRecord.getAccessoryRecyclingUserId())) {
				throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "您无权回收他人的配件");
			}
		}
		if (oldRecord.getIfRecyclingFailed()) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "标记为回收失败的配件，需要先关闭标记");
		}
		if (oldRecord.getIfTakePhoto()) {
			List<TRecyclingAccessoryFile> accessoryFileList = recyclingAccessoryFileMapper.selectByAccessoryId(oldRecord.getId());
			if (accessoryFileList == null || accessoryFileList.isEmpty()) {
				throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "标记为需要拍照的配件，需要上传配件照片");
			}
		}
		int updateRow = recyclingAccessoryMapper.updateAccessoryRecyclingStatus(id,
				AccessoryStatusEnum.RECYCLED.getCode().shortValue(),
				AccessoryStatusEnum.NOT_RECYCLED.getCode().shortValue(), new Date());
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "配件已回收，切勿重复操作");
		}
		// TODO 配件标记为已回收后，检测是否还存在未回收的配件，有，则标记回收单为部分回收，否则，标记回收单已全部回收
		List<TRecyclingAccessory> notRecycledList = recyclingAccessoryMapper.queryNotRecycledAccessoryList(oldRecord.getRecyclingNo());
		if (notRecycledList == null || notRecycledList.isEmpty()) {
			updateRow = recyclingListMapper.updateRecyclingListStatus(RecyclingEnum.ALL_RECYCLED.getCode().shortValue(), oldRecord.getRecyclingNo());
		} else {
			updateRow = recyclingListMapper.updateRecyclingListStatus(RecyclingEnum.PART_RECYCLED.getCode().shortValue(), oldRecord.getRecyclingNo());
		}
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "更新回收单的回收状态失败");
		}
		return Boolean.TRUE;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryCancelRecycled(Long id, AuthUserDTO authUser) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(id);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		if (!authUser.getRoleNos().contains(Constant.ROLE_ADMIN)) {
			if (!authUser.getUserId().equals(oldRecord.getAccessoryRecyclingUserId())) {
				throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "您无权撤销他人已回收的配件");
			}
		}
		Boolean isRecycled = AccessoryStatusEnum.RECYCLED.getCode().intValue() == oldRecord.getAccessoryStatus().intValue();
		if (!isRecycled) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "只能对已回收的配件进行撤销操作");
		}
		int updateRow = recyclingAccessoryMapper.updateAccessoryRecyclingStatus(id,
				AccessoryStatusEnum.NOT_RECYCLED.getCode().shortValue(),
				AccessoryStatusEnum.RECYCLED.getCode().shortValue(), null);
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "您只能对已回收的配件进行撤销操作");
		}
		// TODO 撤销配件回收后，需要检测配件是否已全部回收完成，没有，则标记回收单为部分回收，否则，标记回收单已全部回收
		List<TRecyclingAccessory> recycledList = recyclingAccessoryMapper.queryRecycledAccessoryList(oldRecord.getRecyclingNo());
		if (recycledList == null || recycledList.isEmpty()) {
			updateRow = recyclingListMapper.updateRecyclingListStatus(RecyclingEnum.NOT_RECYCLED.getCode().shortValue(), oldRecord.getRecyclingNo());
		} else {
			updateRow = recyclingListMapper.updateRecyclingListStatus(RecyclingEnum.PART_RECYCLED.getCode().shortValue(), oldRecord.getRecyclingNo());
		}
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "更新回收单的回收状态失败");
		}
		return Boolean.TRUE;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryHasStorage(Long id, AuthUserDTO authUser) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(id);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		Boolean isRecycled = AccessoryStatusEnum.RECYCLED.getCode().intValue() == oldRecord.getAccessoryStatus().intValue();
		if (!isRecycled) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "只能对已回收的配件进行入库");
		}
		int updateRow = recyclingAccessoryMapper.updateAccessoryStorageStatus(id,
				AccessoryStatusEnum.STORAGE.getCode().shortValue(), authUser.getUserId(),
				AccessoryStatusEnum.RECYCLED.getCode().shortValue(), new Date());
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "配件已入库，切勿重复操作");
		}
		// TODO 配件标记为已入库后，检测是否还存在未入库的配件，有，则标记回收单为部分入库，否则，标记回收单已全部入库
		List<TRecyclingAccessory> notStorageList = recyclingAccessoryMapper.queryNotStorageAccessoryList(oldRecord.getRecyclingNo());
		if (notStorageList == null || notStorageList.isEmpty()) {
			updateRow = recyclingListMapper.updateRecyclingListStorageStatus(StorageEnum.ALL_STORAGE.getCode().shortValue(), oldRecord.getRecyclingNo());
		} else {
			updateRow = recyclingListMapper.updateRecyclingListStorageStatus(StorageEnum.PART_STORAGE.getCode().shortValue(), oldRecord.getRecyclingNo());
		}
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "更新回收单的入库状态失败");
		}
		return Boolean.TRUE;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryCancelStorage(Long id, AuthUserDTO authUser) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(id);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		Boolean isStorage = AccessoryStatusEnum.STORAGE.getCode().intValue() == oldRecord.getAccessoryStatus().intValue();
		if (!isStorage) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "只能对已入库的配件进行撤销操作");
		}
		int updateRow = recyclingAccessoryMapper.updateAccessoryStorageStatus(id,
				AccessoryStatusEnum.RECYCLED.getCode().shortValue(), 0,
				AccessoryStatusEnum.STORAGE.getCode().shortValue(), null);
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "配件已入库，切勿重复操作");
		}
		// TODO 撤销配件已入库后，需要检测配件是否已全部入库，没有，则标记回收单为部分入库，否则，标记回收单已全部入库
		List<TRecyclingAccessory> storageList = recyclingAccessoryMapper.queryStorageAccessoryList(oldRecord.getRecyclingNo());
		if (storageList == null || storageList.isEmpty()) {
			updateRow = recyclingListMapper.updateRecyclingListStorageStatus(StorageEnum.NOT_STORAGE.getCode().shortValue(), oldRecord.getRecyclingNo());
		} else {
			updateRow = recyclingListMapper.updateRecyclingListStorageStatus(StorageEnum.PART_STORAGE.getCode().shortValue(), oldRecord.getRecyclingNo());
		}
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "更新回收单的入库状态失败");
		}
		return Boolean.TRUE;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryDelete(Long id, AuthUserDTO authUser) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(id);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		Boolean isRecycled = AccessoryStatusEnum.NOT_RECYCLED.getCode().intValue() == oldRecord.getAccessoryStatus().intValue();
		if (!isRecycled) {
			throw new BOException(ApiResultCode.CUSTOMIZE_ERROR, "只能删除未回收的配件");
		}
		int deleteRow = recyclingAccessoryMapper.deleteByPrimaryKey(id);
		if (deleteRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "删除配件操作异常");
		}
		return Boolean.TRUE;
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean accessoryModify(RecyclingAccessoryReqDTO reqDto, AuthUserDTO authUser) {
		TRecyclingAccessory oldRecord = recyclingAccessoryMapper.selectByPrimaryKey(reqDto.getId());
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "未查找到对应的配件信息");
		}
		TRecyclingAccessory record = BeanUtil.copy(reqDto, TRecyclingAccessory.class);
		int updateRow = recyclingAccessoryMapper.updateByPrimaryKeySelective(record);
		if (updateRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR, "配件信息修改操作异常");
		}
		return Boolean.TRUE;
	}
}
