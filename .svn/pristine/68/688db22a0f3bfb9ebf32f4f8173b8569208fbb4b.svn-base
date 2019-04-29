package com.huachu.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("角色信息展示")
public class AdminRoleRespDTO {
	
	@ApiModelProperty(value = "角色ID")
	private Integer roleId;
	@ApiModelProperty(value = "角色代码")
	private String roleNo;
	@ApiModelProperty(value = "角色名称")
	private String roleName;
	@ApiModelProperty(value = "角色描述")
	private String roleDesc;
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	@ApiModelProperty(value = "是否允许删除")
	private Boolean ifOperate;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getIfOperate() {
		return ifOperate;
	}

	public void setIfOperate(Boolean ifOperate) {
		this.ifOperate = ifOperate;
	}
	
	@ApiModelProperty(value = "是否允许删除中文")
	public String getIfOperateStr() {
		String ifOperateStr = "";
		if (ifOperate) {
			ifOperateStr = "是";
		} else {
			ifOperateStr = "否";
		}
		return ifOperateStr;
	}
}
