package com.huachu.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("配件信息")
public class RecyclingAccessoryReqDTO {

	@ApiModelProperty(value = "配件ID")
	private Long id;
	@ApiModelProperty(value = "配件名称")
	@NotEmpty(message = "配件名称不能为空")
	private String accessoryName;
	@ApiModelProperty(value = "配件价格")
	@NotNull(message = "配件价格不能为空")
	private BigDecimal accessoryPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	public BigDecimal getAccessoryPrice() {
		return accessoryPrice;
	}

	public void setAccessoryPrice(BigDecimal accessoryPrice) {
		this.accessoryPrice = accessoryPrice;
	}

}
