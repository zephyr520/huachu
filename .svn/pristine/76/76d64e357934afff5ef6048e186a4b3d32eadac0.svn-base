package com.huachu.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BasePageDto {
	
	@ApiModelProperty(value="页码")
	public Integer pageNo = 1;

	@ApiModelProperty(value="每页条目数")
	public Integer pageSize = 10;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		if(pageNo > 0) this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize > 0) this.pageSize = pageSize;
	}
	
}
