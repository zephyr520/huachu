package com.huachu.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("系统用户操作")
public class AdminUserReqDTO {

	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	@ApiModelProperty(value = "用户名")
	@NotEmpty(message = "用户名不能为空")
	private String userName;
	@ApiModelProperty(value = "用户密码")
	@NotEmpty(message = "用户密码不能为空")
	private String passWord;
	@ApiModelProperty(value = "姓名")
	@NotEmpty(message = "姓名不能为空")
	private String realName;
	@ApiModelProperty(value = "性别", example = "1-男，2-女")
	@NotNull(message = "性别不能为空")
	private Byte gender;
	@ApiModelProperty(value = "手机号")
	@NotEmpty(message = "手机号不能为空")
	@Pattern(regexp = "^1\\d{10}$", message = "手机号格式不正确")
	private String phone;
	@ApiModelProperty(value = "用户状态", example = "enable-正常，disable-禁用")
	@NotEmpty(message = "用户状态不能为空")
	private String status;
	@ApiModelProperty(value = "角色ID")
	@NotNull(message = "角色ID不能为空")
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
