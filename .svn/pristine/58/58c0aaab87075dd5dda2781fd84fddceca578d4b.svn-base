package com.huachu.dto.request;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("新增回收单信息")
public class RecyclingListReqDTO {

	@ApiModelProperty(value = "档案号")
	@NotEmpty(message = "档案号不能为空")
	private String fileNo;
	@ApiModelProperty(value = "车牌号")
	@NotEmpty(message = "车牌号不能为空")
	private String plateNo;
	@ApiModelProperty(value = "车款")
	private String carModel;
	@ApiModelProperty(value = "修理厂ID")
	private Integer repairShopId;
	@ApiModelProperty(value = "三级机构")
	private String organization;
	@ApiModelProperty(value = "配件列表")
	@Valid
	private List<RecyclingAccessoryDTO> accessoryList;

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public Integer getRepairShopId() {
		return repairShopId;
	}

	public void setRepairShopId(Integer repairShopId) {
		this.repairShopId = repairShopId;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public List<RecyclingAccessoryDTO> getAccessoryList() {
		return accessoryList;
	}

	public void setAccessoryList(List<RecyclingAccessoryDTO> accessoryList) {
		this.accessoryList = accessoryList;
	}
}
