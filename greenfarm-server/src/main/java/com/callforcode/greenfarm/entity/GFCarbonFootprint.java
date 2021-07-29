package com.callforcode.greenfarm.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GFCarbonFootprint {
    private Integer footprintId;

    private String footprintName;

    private String footprintCategory;

    private Integer farmId;

    private String username;

    private BigDecimal carbonReduction;

    private Integer carbonCredit;

    private String location;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Integer getFootprintId() {
        return footprintId;
    }

    public void setFootprintId(Integer footprintId) {
        this.footprintId = footprintId;
    }

    public String getFootprintName() {
        return footprintName;
    }

    public void setFootprintName(String footprintName) {
        this.footprintName = footprintName == null ? null : footprintName.trim();
    }

    public String getFootprintCategory() {
        return footprintCategory;
    }

    public void setFootprintCategory(String footprintCategory) {
        this.footprintCategory = footprintCategory == null ? null : footprintCategory.trim();
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public BigDecimal getCarbonReduction() {
        return carbonReduction;
    }

    public void setCarbonReduction(BigDecimal carbonReduction) {
        this.carbonReduction = carbonReduction;
    }

    public Integer getCarbonCredit() {
        return carbonCredit;
    }

    public void setCarbonCredit(Integer carbonCredit) {
        this.carbonCredit = carbonCredit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
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
