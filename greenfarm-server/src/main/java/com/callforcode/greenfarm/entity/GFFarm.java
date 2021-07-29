package com.callforcode.greenfarm.entity;

import java.util.Date;

public class GFFarm {
    private Integer farmId;

    private String farmName;

    private String introduction;

    private String username;

    private Integer totalArea;

    private Integer idleArea;

    private String location;

    private String iconUrl;

    private String rentPeriod;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private String suitedCrops;

    private String latitudeLongitude;

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName == null ? null : farmName.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Integer totalArea) {
        this.totalArea = totalArea;
    }

    public Integer getIdleArea() {
        return idleArea;
    }

    public void setIdleArea(Integer idleArea) {
        this.idleArea = idleArea;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    public String getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(String rentPeriod) {
        this.rentPeriod = rentPeriod == null ? null : rentPeriod.trim();
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

    public String getSuitedCrops() {
        return suitedCrops;
    }

    public void setSuitedCrops(String suitedCrops) {
        this.suitedCrops = suitedCrops == null ? null : suitedCrops.trim();
    }

    public String getLatitudeLongitude() {
        return latitudeLongitude;
    }

    public void setLatitudeLongitude(String latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude == null ? null : latitudeLongitude.trim();
    }
}
