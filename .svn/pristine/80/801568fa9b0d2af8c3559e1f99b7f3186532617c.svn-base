package com.huachu.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author zephyr
 * @date 2018/12/31
 */
@ApiModel("修理厂信息展示")
public class RepairShopRespDTO {

    @ApiModelProperty(value = "修理厂ID")
    private Integer id;
    @ApiModelProperty(value = "修理厂名称")
    private String repairShopName;
    @ApiModelProperty(value = "修理厂地址")
    private String repairShopAddr;
    @ApiModelProperty(value = "修理厂线路")
    private String line;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
