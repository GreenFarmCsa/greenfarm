package com.callforcode.greenfarm.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GFLand {
    private Integer landId;

    private Integer farmId;

    private BigDecimal price;

    private Boolean isRent;

    private String suitedCrops;

    private Integer area;

    private Date createTime;

    private Date modifyTime;

    private String remark;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsRent() {
        return isRent;
    }

    public void setIsRent(Boolean isRent) {
        this.isRent = isRent;
    }

    public String getSuitedCrops() {
        return suitedCrops;
    }

    public void setSuitedCrops(String suitedCrops) {
        this.suitedCrops = suitedCrops == null ? null : suitedCrops.trim();
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
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
