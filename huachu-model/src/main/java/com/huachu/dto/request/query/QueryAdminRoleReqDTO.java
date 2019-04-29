package com.huachu.dto.request.query;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.dto.BasePageDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("系统角色信息查询")
public class QueryAdminRoleReqDTO extends BasePageDto {

	@ApiModelProperty(value = "角色名称")
	private String roleName;
	@ApiModelProperty(value = "开始创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startCreateTime;
	@ApiModelProperty(value = "结束创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endCreateTime;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
