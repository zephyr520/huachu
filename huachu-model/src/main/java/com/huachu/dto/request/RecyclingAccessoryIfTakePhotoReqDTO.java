package com.huachu.dto.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("配件回收是否需要拍照")
public class RecyclingAccessoryIfTakePhotoReqDTO {

	@ApiModelProperty(value = "配件ID")
	@NotNull(message = "请选择操作的配件")
	private Long id;
	@ApiModelProperty(value = "配件回收是否需要拍照")
	@NotNull(message = "参数不能为空")
	private Boolean ifTakePhoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIfTakePhoto() {
		return ifTakePhoto;
	}

	public void setIfTakePhoto(Boolean ifTakePhoto) {
		this.ifTakePhoto = ifTakePhoto;
	}
}
