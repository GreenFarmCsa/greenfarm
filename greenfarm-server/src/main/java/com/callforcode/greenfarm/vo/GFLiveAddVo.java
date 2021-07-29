package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFLiveAddVo {

    private Integer farmId;

    private Integer stepId;

    private String username;

    private String status;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
