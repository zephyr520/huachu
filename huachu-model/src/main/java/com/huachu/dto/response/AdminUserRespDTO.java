package com.huachu.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huachu.common.constants.Constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("系统用户展示信息")
public class AdminUserRespDTO {

	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "密码")
	private String passWord;
	@ApiModelProperty(value = "用户姓名")
	private String realName;
	@ApiModelProperty(value = "用户性别")
	private Byte gender;
	@ApiModelProperty(value = "用户手机号")
	private String phone;
	@ApiModelProperty(value = "用户状态")
	private String status;
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	@ApiModelProperty(value = "最后一次登录时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;
	@ApiModelProperty(value = "角色ID")
	private Integer roleId;
	@ApiModelProperty(value = "角色名称")
	private String roleName;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ApiModelProperty(value = "性别中文")
	public String getGenderStr() {
		String genderStr = "未知";
		if (Constant.MALE.equals(gender)) {
			genderStr = "男";
		} else if (Constant.FEMALE.equals(gender)) {
			genderStr = "女";
		}
		return genderStr;
	}
	
	@ApiModelProperty(value = "状态中文")
	public String getStatusStr() {
		String statusStr = "";
		if (Constant.ENABLE.equals(status)) {
			statusStr = "正常";
		} else if (Constant.DISABLE.equals(status)) {
			statusStr = "禁用";
		}
		return statusStr;
	}
}
