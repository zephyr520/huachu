package com.huachu.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author zephyr
 * @date 2019/1/1
 */
@ApiModel("修理厂信息操作")
public class RepairShopReqDTO {

    @ApiModelProperty(value = "修理厂ID")
    private Integer id;
    @ApiModelProperty(value = "修理厂名称")
    @NotEmpty(message = "修理厂名称不能为空")
    private String repairShopName;
    @ApiModelProperty(value = "修理厂地址")
    @NotEmpty(message = "修理厂地址不能为空")
    private String repairShopAddr;
    @ApiModelProperty(value = "修理厂线路")
    @NotEmpty(message = "修理厂线路不能为空")
    private String line;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepairShopName() {
        return repairShopName;
    }

    public void setRepairShopName(String repairShopName) {
        this.repairShopName = repairShopName;
    }

    public String getRepairShopAddr() {
        return repairShopAddr;
    }

    public void setRepairShopAddr(String repairShopAddr) {
        this.repairShopAddr = repairShopAddr;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
