package com.callforcode.greenfarm.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GFSeed {
    private Integer seedId;

    private Integer productId;

    private String seedName;

    private String seedIntroduction;

    private String production;

    private String lowestPlanting;

    private BigDecimal seedPrice;

    private String fertilizerName;

    private BigDecimal fertilizerPrice;

    private String fertilizerAmount;

    private String wateringQuantity;

    private Integer maturePeriod;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Integer getSeedId() {
        return seedId;
    }

    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName == null ? null : seedName.trim();
    }

    public String getSeedIntroduction() {
        return seedIntroduction;
    }

    public void setSeedIntroduction(String seedIntroduction) {
        this.seedIntroduction = seedIntroduction == null ? null : seedIntroduction.trim();
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production == null ? null : production.trim();
    }

    public String getLowestPlanting() {
        return lowestPlanting;
    }

    public void setLowestPlanting(String lowestPlanting) {
        this.lowestPlanting = lowestPlanting == null ? null : lowestPlanting.trim();
    }

    public BigDecimal getSeedPrice() {
        return seedPrice;
    }

    public void setSeedPrice(BigDecimal seedPrice) {
        this.seedPrice = seedPrice;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName == null ? null : fertilizerName.trim();
    }

    public BigDecimal getFertilizerPrice() {
        return fertilizerPrice;
    }

    public void setFertilizerPrice(BigDecimal fertilizerPrice) {
        this.fertilizerPrice = fertilizerPrice;
    }

    public String getFertilizerAmount() {
        return fertilizerAmount;
    }

    public void setFertilizerAmount(String fertilizerAmount) {
        this.fertilizerAmount = fertilizerAmount == null ? null : fertilizerAmount.trim();
    }

    public String getWateringQuantity() {
        return wateringQuantity;
    }

    public void setWateringQuantity(String wateringQuantity) {
        this.wateringQuantity = wateringQuantity == null ? null : wateringQuantity.trim();
    }

    public Integer getMaturePeriod() {
        return maturePeriod;
    }

    public void setMaturePeriod(Integer maturePeriod) {
        this.maturePeriod = maturePeriod;
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
