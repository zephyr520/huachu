package com.huachu.dto.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("标记或撤销配件回收失败")
public class AccessoryIfRecyclingFailedReqDTO {

	@ApiModelProperty(value = "配件ID")
	@NotNull(message = "请选择操作的配件")
	private Long id;
	@ApiModelProperty(value = "配件是否回收失败新值")
	@NotNull(message = "参数不能为空")
	private Boolean ifRecyclingFailed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIfRecyclingFailed() {
		return ifRecyclingFailed;
	}

	public void setIfRecyclingFailed(Boolean ifRecyclingFailed) {
		this.ifRecyclingFailed = ifRecyclingFailed;
	}
}
