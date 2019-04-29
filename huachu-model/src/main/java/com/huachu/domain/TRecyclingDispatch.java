package com.huachu.domain;

import java.io.Serializable;
import java.util.Date;

public class TRecyclingDispatch implements Serializable {
    private Long id;

    private String recyclingNo;

    private Date expireDate;

    private Integer recyclingUserId;

    private Date dispatchTime;

    private Integer dispatchUserId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecyclingNo() {
        return recyclingNo;
    }

    public void setRecyclingNo(String recyclingNo) {
        this.recyclingNo = recyclingNo;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getRecyclingUserId() {
        return recyclingUserId;
    }

    public void setRecyclingUserId(Integer recyclingUserId) {
        this.recyclingUserId = recyclingUserId;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Integer getDispatchUserId() {
        return dispatchUserId;
    }

    public void setDispatchUserId(Integer dispatchUserId) {
        this.dispatchUserId = dispatchUserId;
    }
}