package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFPlantTaskDetailAddVo {

    private Integer stepId;

    private String username;

    private String location;

    private String status;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private String operRecord;

}
