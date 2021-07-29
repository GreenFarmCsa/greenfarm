package com.callforcode.greenfarm.entity;

import java.util.Date;

public class GFFinanceProduct {
    private Integer financeProductId;

    private String financeProductName;

    private String financeProductNo;

    private String financeProductDesc;

    private String orgName;

    private String financeProductCategory;

    private String financeLimit;

    private String validPeriod;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Integer getFinanceProductId() {
        return financeProductId;
    }

    public void setFinanceProductId(Integer financeProductId) {
        this.financeProductId = financeProductId;
    }

    public String getFinanceProductName() {
        return financeProductName;
    }

    public void setFinanceProductName(String financeProductName) {
        this.financeProductName = financeProductName == null ? null : financeProductName.trim();
    }

    public String getFinanceProductDesc() {
        return financeProductDesc;
    }

    public void setFinanceProductDesc(String financeProductDesc) {
        this.financeProductDesc = financeProductDesc == null ? null : financeProductDesc.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getFinanceProductCategory() {
        return financeProductCategory;
    }

    public void setFinanceProductCategory(String financeProductCategory) {
        this.financeProductCategory = financeProductCategory == null ? null : financeProductCategory.trim();
    }

    public String getFinanceLimit() {
        return financeLimit;
    }

    public void setFinanceLimit(String financeLimit) {
        this.financeLimit = financeLimit == null ? null : financeLimit.trim();
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod == null ? null : validPeriod.trim();
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

    public String getFinanceProductNo() {
        return financeProductNo;
    }

    public void setFinanceProductNo(String financeProductNo) {
        this.financeProductNo = financeProductNo;
    }
}
