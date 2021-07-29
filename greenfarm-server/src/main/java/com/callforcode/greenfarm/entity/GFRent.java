package com.callforcode.greenfarm.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GFRent {
    private Integer rentId;

    private Integer landId;

    private Integer farmId;

    private Integer seedId;

    private String username;

    private BigDecimal rentPrice;

    private Integer area;

    private Date rentStartTime;

    private Date rentEndTime;

    private String confirmCrops;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
    }

    public Integer getLandId() {
        return landId;
    }

    public void setLandId(Integer landId) {
        this.landId = landId;
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public Integer getSeedId() {
        return seedId;
    }

    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Date getRentStartTime() {
        return rentStartTime;
    }

    public void setRentStartTime(Date rentStartTime) {
        this.rentStartTime = rentStartTime;
    }

    public Date getRentEndTime() {
        return rentEndTime;
    }

    public void setRentEndTime(Date rentEndTime) {
        this.rentEndTime = rentEndTime;
    }

    public String getConfirmCrops() {
        return confirmCrops;
    }

    public void setConfirmCrops(String confirmCrops) {
        this.confirmCrops = confirmCrops == null ? null : confirmCrops.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
