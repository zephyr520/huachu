package com.huachu.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @DATE 2018/8/21
 */
@ApiModel("用户信息")
public class AuthUserDTO implements Serializable{

	private static final long serialVersionUID = -2201554581540048190L;
	@ApiModelProperty("用户ID")
    private Integer userId;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("姓名")
    private String realName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("角色")
    private List<String> roleNos;

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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<String> getRoleNos() {
		return roleNos;
	}

	public void setRoleNos(List<String> roleNos) {
		this.roleNos = roleNos;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

	@Override
	public String toString() {
		return "AuthUserDTO [userId=" + userId + ", userName=" + userName + ", realName=" + realName + ", phone="
				+ phone + ", roleNos=" + roleNos + "]";
	}
}
