package com.callforcode.greenfarm.entity;

import java.util.Date;

public class GFUserFinance {
    private Integer userFinanceId;

    private Integer financeProductId;

    private String username;

    private String financeLimit;

    private Date startDate;

    private Date endDate;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    public Integer getUserFinanceId() {
        return userFinanceId;
    }

    public void setUserFinanceId(Integer userFinanceId) {
        this.userFinanceId = userFinanceId;
    }

    public Integer getFinanceProductId() {
        return financeProductId;
    }

    public void setFinanceProductId(Integer financeProductId) {
        this.financeProductId = financeProductId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getFinanceLimit() {
        return financeLimit;
    }

    public void setFinanceLimit(String financeLimit) {
        this.financeLimit = financeLimit == null ? null : financeLimit.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
