package com.huachu.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zephyr on 2019/1/12.
 */
@ApiModel("回收单和配件信息")
public class RecyclingListDTO {

    @ApiModelProperty(value = "档案号")
    private String fileNo;
    @ApiModelProperty(value = "车牌号")
    private String plateNo;
    @ApiModelProperty(value = "车款")
    private String carModel;
    @ApiModelProperty(value = "三级机构")
    private String organization;
    @ApiModelProperty(value = "修理厂名称")
    private String repairShopName;
    @ApiModelProperty(value = "配件名称")
    private String accessoryName;
    @ApiModelProperty(value = "配件价格")
    private BigDecimal accessoryPrice;
    @ApiModelProperty(value = "配件数量")
    private Integer accessoryNum;
    @ApiModelProperty(value = "是否标记为重要配件", example = "true:是，false：否")
    private Boolean ifImportant;

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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRepairShopName() {
        return repairShopName;
    }

    public void setRepairShopName(String repairShopName) {
        this.repairShopName = repairShopName;
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

    public Integer getAccessoryNum() {
        return accessoryNum;
    }

    public void setAccessoryNum(Integer accessoryNum) {
        this.accessoryNum = accessoryNum;
    }

    public Boolean getIfImportant() {
        return ifImportant;
    }

    public void setIfImportant(Boolean ifImportant) {
        this.ifImportant = ifImportant;
    }
}
