package com.huachu.dto.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登录成功的信息")
public class LoginRespDTO {

	@ApiModelProperty("姓名")
	private String nickname;
	@ApiModelProperty("token")
	private String accessToken;
	@ApiModelProperty("操作授权码")
	private List<String> permitIdentifiers;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public List<String> getPermitIdentifiers() {
		return permitIdentifiers;
	}

	public void setPermitIdentifiers(List<String> permitIdentifiers) {
		this.permitIdentifiers = permitIdentifiers;
	}
}
