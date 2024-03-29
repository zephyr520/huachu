package com.huachu.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.constants.AccessoryStatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("回收单配件信息")
public class RecyclingAccessoryRespDTO {
	
	@ApiModelProperty(value = "配件ID")
	private Long id;
	@ApiModelProperty(value = "回收单号")
    private String recyclingNo;
	@ApiModelProperty(value = "配件名称")
    private String accessoryName;
	@ApiModelProperty(value = "配件价格")
    private BigDecimal accessoryPrice;
	@ApiModelProperty(value = "配件数量")
    private Integer accessoryNum;
	@ApiModelProperty(value = "配件回收状态", example = "0-未回收，1-已回收，3-已入库")
    private Short accessoryStatus;
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
	@ApiModelProperty(value = "配件回收时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date accessoryRecyclingTime;
	@ApiModelProperty(value = "配件回收员用户ID")
    private Integer accessoryRecyclingUserId;
	@ApiModelProperty(value = "配件回收员姓名")
	private String accessoryRecyclingUserName;
	@ApiModelProperty(value = "配件入库时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date accessoryStorageTime;
	@ApiModelProperty(value = "配件入库员用户ID")
    private Integer accessoryStorageUserId;
	@ApiModelProperty(value = "配件入库员姓名")
	private String accessoryStorageUserName;
	@ApiModelProperty(value = "配件是否是重要配件")
    private Boolean ifImportant;
	@ApiModelProperty(value = "配件是否回收失败")
    private Boolean ifRecyclingFailed;
	@ApiModelProperty(value = "配件是否需要拍照")
    private Boolean ifTakePhoto;
	@ApiModelProperty(value = "配件图片列表")
	private List<String> accessoryImageList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public BigDecimal getAccessoryPrice() {
		return accessoryPrice;
	}

	public void setAccessoryPrice(BigDecimal accessoryPrice) {
		this.accessoryPrice = accessoryPrice;
	}

	public Integer getAccessoryNum() {
		return accessoryNum;
	}

	public void setAccessoryNum(Integer accessoryNum) {
		this.accessoryNum = accessoryNum;
	}

	public Short getAccessoryStatus() {
		return accessoryStatus;
	}

	public void setAccessoryStatus(Short accessoryStatus) {
		this.accessoryStatus = accessoryStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAccessoryRecyclingTime() {
		return accessoryRecyclingTime;
	}

	public void setAccessoryRecyclingTime(Date accessoryRecyclingTime) {
		this.accessoryRecyclingTime = accessoryRecyclingTime;
	}

	public Integer getAccessoryRecyclingUserId() {
		return accessoryRecyclingUserId;
	}

	public void setAccessoryRecyclingUserId(Integer accessoryRecyclingUserId) {
		this.accessoryRecyclingUserId = accessoryRecyclingUserId;
	}

	public Date getAccessoryStorageTime() {
		return accessoryStorageTime;
	}

	public void setAccessoryStorageTime(Date accessoryStorageTime) {
		this.accessoryStorageTime = accessoryStorageTime;
	}

	public Integer getAccessoryStorageUserId() {
		return accessoryStorageUserId;
	}

	public void setAccessoryStorageUserId(Integer accessoryStorageUserId) {
		this.accessoryStorageUserId = accessoryStorageUserId;
	}

	public Boolean getIfImportant() {
		return ifImportant;
	}

	public void setIfImportant(Boolean ifImportant) {
		this.ifImportant = ifImportant;
	}

	public Boolean getIfRecyclingFailed() {
		return ifRecyclingFailed;
	}

	public void setIfRecyclingFailed(Boolean ifRecyclingFailed) {
		this.ifRecyclingFailed = ifRecyclingFailed;
	}

	public Boolean getIfTakePhoto() {
		return ifTakePhoto;
	}

	public void setIfTakePhoto(Boolean ifTakePhoto) {
		this.ifTakePhoto = ifTakePhoto;
	}
	
	public String getAccessoryRecyclingUserName() {
		return accessoryRecyclingUserName;
	}

	public void setAccessoryRecyclingUserName(String accessoryRecyclingUserName) {
		this.accessoryRecyclingUserName = accessoryRecyclingUserName;
	}

	public String getAccessoryStorageUserName() {
		return accessoryStorageUserName;
	}

	public void setAccessoryStorageUserName(String accessoryStorageUserName) {
		this.accessoryStorageUserName = accessoryStorageUserName;
	}

	public List<String> getAccessoryImageList() {
		return accessoryImageList;
	}

	public void setAccessoryImageList(List<String> accessoryImageList) {
		this.accessoryImageList = accessoryImageList;
	}

	@ApiModelProperty(value = "配件状态中文")
	public String getAccessoryStatusStr() {
		AccessoryStatusEnum status = AccessoryStatusEnum.getStatus(accessoryStatus.intValue());
		if (status == null) {
			return "";
		}
		return status.getMsgStr();
	}
}
