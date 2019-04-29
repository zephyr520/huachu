package com.huachu.common.constants;

public enum StorageEnum {

	NOT_STORAGE(0, "未入库"), PART_STORAGE(1, "部分入库"), ALL_STORAGE(2, "已入库");

	private Integer code;
	private String msgStr;

	private StorageEnum(Integer code, String msgStr) {
		this.code = code;
		this.msgStr = msgStr;
	}
	
	public static StorageEnum getStorage(Integer code) {
		StorageEnum[] enums = StorageEnum.values();
		for (StorageEnum re : enums) {
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
