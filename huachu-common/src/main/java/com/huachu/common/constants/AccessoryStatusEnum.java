package com.huachu.common.constants;

public enum AccessoryStatusEnum {

	NOT_RECYCLED(0, "未回收"), RECYCLED(1, "已回收未入库"), STORAGE(3, "已入库");

	private Integer code;
	private String msgStr;

	private AccessoryStatusEnum(Integer code, String msgStr) {
		this.code = code;
		this.msgStr = msgStr;
	}

	public static AccessoryStatusEnum getStatus(Integer code) {
		AccessoryStatusEnum[] enums = AccessoryStatusEnum.values();
		for (AccessoryStatusEnum re : enums) {
			if (code.equals(re.getCode())) {
				return re;
			}
		}
		return null;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsgStr() {
		return msgStr;
	}

	public void setMsgStr(String msgStr) {
		this.msgStr = msgStr;
	}
}
