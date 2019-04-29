package com.huachu.common.dto;

import com.huachu.common.constants.ClientType;

public class UserClient {

	private Integer userId;
	private ClientType clientType;
	/***
	 * token生成时间
	 */
	private Long genTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public Long getGenTime() {
		return genTime;
	}

	public void setGenTime(Long genTime) {
		this.genTime = genTime;
	}
}
