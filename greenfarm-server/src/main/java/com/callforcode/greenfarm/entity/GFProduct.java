package com.callforcode.greenfarm.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GFProduct {
    private Integer productId;

    private Integer farmId;

    private Integer landId;

    private String username;

    private String productName;

    private String category;

    private String introduction;

    private String weight;

    private BigDecimal price;

    private Integer carbonCredit;

    private Integer number;

    private Integer saleNumber;

    private Integer likeNumber;

    private String identifications;

    private BigDecimal carbonEmission;

    private BigDecimal donateAmount;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public Integer getLandId() {
        return landId;
    }

    public void setLandId(Integer landId) {
        this.landId = landId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCarbonCredit() {
        return carbonCredit;
    }

    public void setCarbonCredit(Integer carbonCredit) {
        this.carbonCredit = carbonCredit;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getIdentifications() {
        return identifications;
    }

    public void setIdentifications(String identifications) {
        this.identifications = identifications == null ? null : identifications.trim();
    }

    public BigDecimal getCarbonEmission() {
        return carbonEmission;
    }

    public void setCarbonEmission(BigDecimal carbonEmission) {
        this.carbonEmission = carbonEmission;
    }

    public BigDecimal getDonateAmount() {
        return donateAmount;
    }

    public void setDonateAmount(BigDecimal donateAmount) {
        this.donateAmount = donateAmount;
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
