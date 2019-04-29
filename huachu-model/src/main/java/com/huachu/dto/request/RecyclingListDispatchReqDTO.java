package com.huachu.dto.request;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("回收单派单操作")
public class RecyclingListDispatchReqDTO {

	@ApiModelProperty(value = "回收单号集合")
	@NotNull(message = "请选择回收单")
	@Valid
	private List<String> recyclingNoList;
	@ApiModelProperty(value = "回收员用户ID")
	@NotNull(message = "请选择回收员")
	private Integer recyclingUserId;
	@ApiModelProperty(value = "回收单回收截止日期")
	@NotNull(message = "请选择回收单回收截止日期")
	private Date expireDate;

	public List<String> getRecyclingNoList() {
		return recyclingNoList;
	}

	public void setRecyclingNoList(List<String> recyclingNoList) {
		this.recyclingNoList = recyclingNoList;
	}

	public Integer getRecyclingUserId() {
		return recyclingUserId;
	}

	public void setRecyclingUserId(Integer recyclingUserId) {
		this.recyclingUserId = recyclingUserId;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
}
