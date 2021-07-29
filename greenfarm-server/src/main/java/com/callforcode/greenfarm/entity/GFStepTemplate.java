package com.callforcode.greenfarm.entity;

import java.util.Date;

public class GFStepTemplate {
    private Integer templateId;

    private Integer seedId;

    private Integer carbonCredit;

    private Integer plantNo;

    private String plantStep;

    private Date beginTime;

    private Date endTime;

    private String remark;

    private String vedioUrl;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getSeedId() {
        return seedId;
    }

    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }

    public Integer getCarbonCredit() {
        return carbonCredit;
    }

    public void setCarbonCredit(Integer carbonCredit) {
        this.carbonCredit = carbonCredit;
    }

    public Integer getPlantNo() {
        return plantNo;
    }

    public void setPlantNo(Integer plantNo) {
        this.plantNo = plantNo;
    }

    public String getPlantStep() {
        return plantStep;
    }

    public void setPlantStep(String plantStep) {
        this.plantStep = plantStep == null ? null : plantStep.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getVedioUrl() {
        return vedioUrl;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedioUrl = vedioUrl == null ? null : vedioUrl.trim();
    }
}
