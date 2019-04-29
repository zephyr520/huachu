package com.huachu.dto.request.query;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.dto.BasePageDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("系统用户查询")
public class QueryAdminUserReqDTO extends BasePageDto {

	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "姓名")
	private String realName;
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "用户状态", example = "all:全部，enable:正常，disable:禁用")
	private String status;
	@ApiModelProperty(value = "创建开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startCreateTime;
	@ApiModelProperty(value = "创建结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endCreateTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
