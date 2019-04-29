package com.huachu.common.constants;

public enum ClientType {
	// 移动端
	ANDROID(1), IOS(2),
	// 后台系统
	ADMIN(3)
	;
	
	private Integer intVal;
	
	private ClientType(Integer intVal) {
		this.intVal = intVal;
	}
	
	public Integer getValue() {
		return this.intVal;
	}
	
	public static ClientType getClientType(String type) {
		for(ClientType ct : values()) {
			if(ct.name().equalsIgnoreCase(type)) {
				return ct;
			}
		}
		return null;
	}
}
