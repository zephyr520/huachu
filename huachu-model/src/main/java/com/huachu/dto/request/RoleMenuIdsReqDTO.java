package com.huachu.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("角色授权菜单")
public class RoleMenuIdsReqDTO {

	@ApiModelProperty(value = "角色ID")
	@NotNull(message = "角色ID不能为空")
	private Integer roleId;
	@ApiModelProperty(value = "菜单ID集合")
	@NotNull(message = "菜单ID集合不能为空")
	@Valid
	private List<Integer> menuIds;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
}
