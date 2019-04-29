package com.huachu.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("回收员用户信息")
public class RecyclingUserRespDTO {

	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	@ApiModelProperty(value = "回收员姓名")
	private String realName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
