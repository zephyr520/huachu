package com.huachu.dto.request.query;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.dto.BasePageDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询配件信息")
public class QueryRecyclingAccessoryReqDTO extends BasePageDto {

	@ApiModelProperty(value = "回收单号")
	private String recyclingNo;
	@ApiModelProperty(value = "配件名称")
	private String accessoryName;
	@ApiModelProperty(value = "配件回收状态", example = "-1-全部，0-未回收，1-已回收，3-已入库")
	private Short accessoryStatus;
	@ApiModelProperty(value = "配件回收开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startAccessoryRecyclingTime;
	@ApiModelProperty(value = "配件回收开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endAccessoryRecyclingTime;
	@ApiModelProperty(value = "配件回收员用户ID")
	private Integer accessoryRecyclingUserId;
	@ApiModelProperty(value = "配件回收员姓名")
	private String accessoryRecyclingUserName;
	@ApiModelProperty(value = "配件入库开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startAccessoryStorageTime;
	@ApiModelProperty(value = "配件入库结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endAccessoryStorageTime;
	@ApiModelProperty(value = "配件入库员姓名")
	private String accessoryStorageUserName;
	@ApiModelProperty(value = "配件是否是重要配件", example = "-1-全部，0-否，1-是")
    private Byte ifImportant;
	@ApiModelProperty(value = "配件是否回收失败", example = "-1-全部，0-否，1-是")
    private Byte ifRecyclingFailed;
	@ApiModelProperty(value = "配件是否需要拍照", example = "-1-全部，0-否，1-是")
    private Byte ifTakePhoto;

	public String getRecyclingNo() {
		return recyclingNo;
	}

	public void setRecyclingNo(String recyclingNo) {
		this.recyclingNo = recyclingNo;
	}

	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	public Short getAccessoryStatus() {
		return accessoryStatus;
	}

	public void setAccessoryStatus(Short accessoryStatus) {
		this.accessoryStatus = accessoryStatus;
	}

	public Date getStartAccessoryRecyclingTime() {
		return startAccessoryRecyclingTime;
	}

	public void setStartAccessoryRecyclingTime(Date startAccessoryRecyclingTime) {
		this.startAccessoryRecyclingTime = startAccessoryRecyclingTime;
	}

	public Date getEndAccessoryRecyclingTime() {
		return endAccessoryRecyclingTime;
	}

	public void setEndAccessoryRecyclingTime(Date endAccessoryRecyclingTime) {
		this.endAccessoryRecyclingTime = endAccessoryRecyclingTime;
	}

	public String getAccessoryRecyclingUserName() {
		return accessoryRecyclingUserName;
	}

	public void setAccessoryRecyclingUserName(String accessoryRecyclingUserName) {
		this.accessoryRecyclingUserName = accessoryRecyclingUserName;
	}

	public Date getStartAccessoryStorageTime() {
		return startAccessoryStorageTime;
	}

	public void setStartAccessoryStorageTime(Date startAccessoryStorageTime) {
		this.startAccessoryStorageTime = startAccessoryStorageTime;
	}

	public Date getEndAccessoryStorageTime() {
		return endAccessoryStorageTime;
	}

	public void setEndAccessoryStorageTime(Date endAccessoryStorageTime) {
		this.endAccessoryStorageTime = endAccessoryStorageTime;
	}

	public String getAccessoryStorageUserName() {
		return accessoryStorageUserName;
	}

	public void setAccessoryStorageUserName(String accessoryStorageUserName) {
		this.accessoryStorageUserName = accessoryStorageUserName;
	}

	public Byte getIfImportant() {
		return ifImportant;
	}

	public void setIfImportant(Byte ifImportant) {
		this.ifImportant = ifImportant;
	}

	public Byte getIfRecyclingFailed() {
		return ifRecyclingFailed;
	}

	public void setIfRecyclingFailed(Byte ifRecyclingFailed) {
		this.ifRecyclingFailed = ifRecyclingFailed;
	}

	public Byte getIfTakePhoto() {
		return ifTakePhoto;
	}

	public void setIfTakePhoto(Byte ifTakePhoto) {
		this.ifTakePhoto = ifTakePhoto;
	}

	public Integer getAccessoryRecyclingUserId() {
		return accessoryRecyclingUserId;
	}

	public void setAccessoryRecyclingUserId(Integer accessoryRecyclingUserId) {
		this.accessoryRecyclingUserId = accessoryRecyclingUserId;
	}
}
