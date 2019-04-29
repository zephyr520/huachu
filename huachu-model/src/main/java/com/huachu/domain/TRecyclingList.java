package com.huachu.domain;

import java.io.Serializable;
import java.util.Date;

public class TRecyclingList implements Serializable {
    private String recyclingNo;

    private String fileNo;

    private String plateNo;

    private String carModel;

    private Integer repairShopId;

    private Integer staffId;

    private String organization;

    private Short recyclingStatus;

    private Date recyclingTime;

    private Short storageStatus;

    private Date storageTime;

    private Integer createId;

    private Date createTime;

    private Integer dispatchId;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public String getRecyclingNo() {
        return recyclingNo;
    }

    public void setRecyclingNo(String recyclingNo) {
        this.recyclingNo = recyclingNo;
    }

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

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Short getRecyclingStatus() {
        return recyclingStatus;
    }

    public void setRecyclingStatus(Short recyclingStatus) {
        this.recyclingStatus = recyclingStatus;
    }

    public Date getRecyclingTime() {
        return recyclingTime;
    }

    public void setRecyclingTime(Date recyclingTime) {
        this.recyclingTime = recyclingTime;
    }

    public Short getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(Short storageStatus) {
        this.storageStatus = storageStatus;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Integer dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}