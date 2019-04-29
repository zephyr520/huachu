package com.huachu.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("配件图片附件信息")
public class RecyclingAccessoryFileReqDTO {

	@ApiModelProperty(value = "配件ID")
	@NotNull(message = "请选择配件操作")
	private Long accessoryId;
	@ApiModelProperty(value = "配件图片url")
	@NotNull(message = "请上传配件图片")
	@Valid
	private List<String> fileUrlList;

	public Long getAccessoryId() {
		return accessoryId;
	}

	public void setAccessoryId(Long accessoryId) {
		this.accessoryId = accessoryId;
	}

	public List<String> getFileUrlList() {
		return fileUrlList;
	}

	public void setFileUrlList(List<String> fileUrlList) {
		this.fileUrlList = fileUrlList;
	}
}
