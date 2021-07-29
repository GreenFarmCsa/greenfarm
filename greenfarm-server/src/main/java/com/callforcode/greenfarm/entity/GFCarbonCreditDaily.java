package com.callforcode.greenfarm.entity;

import java.util.Date;

public class GFCarbonCreditDaily {
    private Integer creditDailyId;

    private String username;

    private Integer carbonCredit;

    private Date carbonDate;

    private String type;

    public Integer getCreditDailyId() {
        return creditDailyId;
    }

    public void setCreditDailyId(Integer creditDailyId) {
        this.creditDailyId = creditDailyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getCarbonCredit() {
        return carbonCredit;
    }

    public void setCarbonCredit(Integer carbonCredit) {
        this.carbonCredit = carbonCredit;
    }

    public Date getCarbonDate() {
        return carbonDate;
    }

    public void setCarbonDate(Date carbonDate) {
        this.carbonDate = carbonDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}
