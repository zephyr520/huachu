package com.huachu.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("配件信息")
public class RecyclingAccessoryDTO {

	@ApiModelProperty(value = "配件名称")
	@NotEmpty(message = "配件名称不能为空")
	private String accessoryName;
	@ApiModelProperty(value = "配件价格")
	@NotNull(message = "配件价格不能为空")
	private BigDecimal accessoryPrice;
	@ApiModelProperty(value = "配件数量")
	@NotNull(message = "配件数量不能为空")
	private Integer accessoryNum;

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

	public Integer getAccessoryNum() {
		return accessoryNum;
	}

	public void setAccessoryNum(Integer accessoryNum) {
		this.accessoryNum = accessoryNum;
	}
}
