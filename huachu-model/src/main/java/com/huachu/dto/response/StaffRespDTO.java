package com.huachu.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author zephyr
 * @date 2018/12/31
 */
@ApiModel("定损员信息")
public class StaffRespDTO {

    @ApiModelProperty(value = "定损员ID")
    private Integer id;
    @ApiModelProperty(value = "定损员手机号")
    private String staffPhone;
    @ApiModelProperty(value = "定损员姓名")
    private String staffName;
    @ApiModelProperty(value = "定损员描述")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
