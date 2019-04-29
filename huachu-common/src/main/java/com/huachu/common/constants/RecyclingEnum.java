package com.huachu.common.constants;

public enum RecyclingEnum {

	NOT_RECYCLED(0, "未回收"), PART_RECYCLED(1, "部分回收"), ALL_RECYCLED(2, "已回收");

	private Integer code;
	private String msgStr;

	private RecyclingEnum(Integer code, String msgStr) {
		this.code = code;
		this.msgStr = msgStr;
	}

	public static RecyclingEnum getRecyling(Integer code) {
		RecyclingEnum[] enums = RecyclingEnum.values();
		for (RecyclingEnum re : enums) {
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
