package com.callforcode.greenfarm.entity;

import java.util.Date;

public class GFTaskStep {
    private Integer stepId;

    private Integer taskId;
    
    private Integer carbonCredit;

    private Integer plantNo;

    private String plantStep;

    private String status;

    private Date beginTime;

    private Date endTime;

    private String remark;

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public Integer getCarbonCredit() {
        return carbonCredit;
    }

    public void setCarbonCredit(Integer carbonCredit) {
        this.carbonCredit = carbonCredit;
    }
    
}
