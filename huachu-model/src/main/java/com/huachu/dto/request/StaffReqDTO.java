package com.huachu.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("定损员信息")
public class StaffReqDTO {

	@ApiModelProperty(value = "定损员ID")
	private Integer id;
	@ApiModelProperty(value = "定损员手机号")
	@NotEmpty(message = "定损员手机号不能为空")
	private String staffPhone;
	@ApiModelProperty(value = "定损员姓名")
	@NotEmpty(message = "定损员姓名不能为空")
	private String staffName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
