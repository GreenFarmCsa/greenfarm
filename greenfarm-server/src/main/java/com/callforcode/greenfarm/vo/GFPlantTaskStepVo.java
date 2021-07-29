package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFPlantTaskStepVo {

    private Integer stepId;

    private Integer taskId;
    
    private Integer carbonCredit;

    private Integer plantNo;

    private String plantStep;

    private String status;

    private Date beginTime;

    private Date endTime;

    private String remark;

}
