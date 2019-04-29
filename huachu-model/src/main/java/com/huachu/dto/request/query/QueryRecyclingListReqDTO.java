package com.huachu.dto.request.query;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.dto.BasePageDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询回收单信息")
public class QueryRecyclingListReqDTO extends BasePageDto {

	@ApiModelProperty(value = "回收单号")
	private String recyclingNo;
	@ApiModelProperty(value = "档案号")
	private String fileNo;
	@ApiModelProperty(value = "车牌号")
	private String plateNo;
	@ApiModelProperty(value = "修理厂名称")
	private String repairShopName;
	@ApiModelProperty(value = "定损员姓名")
	private String staffName;
	@ApiModelProperty(value = "三级机构")
	private String organization;
	@ApiModelProperty(value = "回收单回收员ID")
	private Integer recyclingUserId;
	@ApiModelProperty(value = "回收单回收员姓名")
	private String recyclingUserName;
	@ApiModelProperty(value = "回收单回收状态", example = "0-未回收，1-部分回收，2-已回收")
	private List<Short> recyclingStatusList;
	@ApiModelProperty(value = "回收单入库状态", example = "0-未入库，1-部分入库，2-已入库")
	private List<Short> storageStatusList;
	@ApiModelProperty(value = "回收单派单状态", example = "-1-全部，0-未派单，1-派单未过期，2-派单已过期")
	private Short dispatchStatus;
	@ApiModelProperty(value = "回收单创建开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date startCreateTime;
	@ApiModelProperty(value = "回收单创建结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date endCreateTime;
	@ApiModelProperty(value = "回收单中是否有配件", example = "-1-全部，0-无配件，1-有配件")
	private Byte hasAccessory;

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

	public String getRepairShopName() {
		return repairShopName;
	}

	public void setRepairShopName(String repairShopName) {
		this.repairShopName = repairShopName;
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

	public String getRecyclingUserName() {
		return recyclingUserName;
	}

	public void setRecyclingUserName(String recyclingUserName) {
		this.recyclingUserName = recyclingUserName;
	}

	public List<Short> getRecyclingStatusList() {
		return recyclingStatusList;
	}

	public void setRecyclingStatusList(List<Short> recyclingStatusList) {
		this.recyclingStatusList = recyclingStatusList;
	}

	public List<Short> getStorageStatusList() {
		return storageStatusList;
	}

	public void setStorageStatusList(List<Short> storageStatusList) {
		this.storageStatusList = storageStatusList;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Integer getRecyclingUserId() {
		return recyclingUserId;
	}

	public void setRecyclingUserId(Integer recyclingUserId) {
		this.recyclingUserId = recyclingUserId;
	}

	public Short getDispatchStatus() {
		return dispatchStatus;
	}

	public void setDispatchStatus(Short dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

	public Byte getHasAccessory() {
		return hasAccessory;
	}

	public void setHasAccessory(Byte hasAccessory) {
		this.hasAccessory = hasAccessory;
	}
}
