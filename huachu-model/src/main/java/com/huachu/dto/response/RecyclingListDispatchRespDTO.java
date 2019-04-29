package com.huachu.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.util.DateUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("派单记录信息")
public class RecyclingListDispatchRespDTO {

	@ApiModelProperty(value = "派单记录ID")
	private Long id;
	@ApiModelProperty(value = "回收单号")
	private String recyclingNo;
	@ApiModelProperty(value = "回收截止日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date expireDate;
	@ApiModelProperty(value = "回收员用户ID")
	private Integer recyclingUserId;
	@ApiModelProperty(value = "回收员姓名")
	private String recyclingUserName;
	@ApiModelProperty(value = "派单时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dispatchTime;
	@ApiModelProperty(value = "派单员用户ID")
	private Integer dispatchUserId;
	@ApiModelProperty(value = "派单员员姓名")
	private String dispatchUserName;

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

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getRecyclingUserId() {
		return recyclingUserId;
	}

	public void setRecyclingUserId(Integer recyclingUserId) {
		this.recyclingUserId = recyclingUserId;
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

	public Integer getDispatchUserId() {
		return dispatchUserId;
	}

	public void setDispatchUserId(Integer dispatchUserId) {
		this.dispatchUserId = dispatchUserId;
	}

	public String getDispatchUserName() {
		return dispatchUserName;
	}

	public void setDispatchUserName(String dispatchUserName) {
		this.dispatchUserName = dispatchUserName;
	}
	
	public String getRecyclingUserNameStr() {
		return "回收员：" + this.getDispatchUserName();
	}
	
	public String getExpireDateStr() {
		return "截止日期：" + DateUtils.YYYY年MM月DD日.get().format(this.getExpireDate());
	}
}
