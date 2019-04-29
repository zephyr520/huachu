package com.huachu.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.constants.RecyclingEnum;
import com.huachu.common.constants.StorageEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("回收单信息")
public class RecyclingListRespDTO {
	@ApiModelProperty(value = "回收单号")
	private String recyclingNo;
	@ApiModelProperty(value = "档案号")
	private String fileNo;
	@ApiModelProperty(value = "车牌号")
	private String plateNo;
	@ApiModelProperty(value = "车款")
	private String carModel;
	@ApiModelProperty(value = "修理厂名称")
	private String repairShopName;
	@ApiModelProperty(value = "修理厂地址")
	private String repairShopAddr;
	@ApiModelProperty(value = "定损员手机号")
	private String staffPhone;
	@ApiModelProperty(value = "定损员姓名")
	private String staffName;
	@ApiModelProperty(value = "三级机构")
	private String organization;
	@ApiModelProperty(value = "回收单回收截止日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date expireDate;
	@ApiModelProperty(value = "回收单回收员姓名")
	private String recyclingUserName;
	@ApiModelProperty(value = "回收单派单时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dispatchTime;
	@ApiModelProperty(value = "回收单派单员姓名")
	private String dispatchUserName;
	@ApiModelProperty(value = "回收单回收状态", example = "0-未回收，1-部分回收，2-已回收")
	private Short recyclingStatus;
	@ApiModelProperty(value = "回收单标记已全部回收时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date recyclingTime;
	@ApiModelProperty(value = "回收单入库状态", example = "0-未入库，1-部分入库，2-已入库")
	private Short storageStatus;
	@ApiModelProperty(value = "回收单标记已全部入库时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date storageTime;
	@ApiModelProperty(value = "回收单创建人")
	private String creator;
	@ApiModelProperty(value = "回收单创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	public String getRecyclingNo() {
		return recyclingNo;
	}

	public void setRecyclingNo(String recyclingNo) {
		this.recyclingNo = recyclingNo;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getRepairShopName() {
		return repairShopName;
	}

	public void setRepairShopName(String repairShopName) {
		this.repairShopName = repairShopName;
	}

	public String getRepairShopAddr() {
		return repairShopAddr;
	}

	public void setRepairShopAddr(String repairShopAddr) {
		this.repairShopAddr = repairShopAddr;
	}

	public String getStaffPhone() {
		return staffPhone;
	}

	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Short getRecyclingStatus() {
		return recyclingStatus;
	}

	public void setRecyclingStatus(Short recyclingStatus) {
		this.recyclingStatus = recyclingStatus;
	}

	public Date getRecyclingTime() {
		return recyclingTime;
	}

	public void setRecyclingTime(Date recyclingTime) {
		this.recyclingTime = recyclingTime;
	}

	public Short getStorageStatus() {
		return storageStatus;
	}

	public void setStorageStatus(Short storageStatus) {
		this.storageStatus = storageStatus;
	}

	public Date getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getRecyclingUserName() {
		return recyclingUserName;
	}

	public void setRecyclingUserName(String recyclingUserName) {
		this.recyclingUserName = recyclingUserName;
	}

	public Date getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getDispatchUserName() {
		return dispatchUserName;
	}

	public void setDispatchUserName(String dispatchUserName) {
		this.dispatchUserName = dispatchUserName;
	}
	
	@ApiModelProperty(value = "回收单回收状态中文")
	public String getRecyclingStatusStr() {
		RecyclingEnum re = RecyclingEnum.getRecyling(recyclingStatus.intValue());
		if (re == null) {
			return "";
		}
		return re.getMsgStr();
	}
	
	@ApiModelProperty(value = "回收单入库状态中文")
	public String getStorageStatusStr() {
		StorageEnum se = StorageEnum.getStorage(storageStatus.intValue());
		if (se == null) {
			return "";
		}
		return se.getMsgStr();
	}
}
