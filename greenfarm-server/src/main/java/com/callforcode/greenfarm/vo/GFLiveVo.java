package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFLiveVo {
    private Integer liveId;

    private Integer farmId;

    private Integer stepId;
    
    private String username;

    private String status;

    private Date createTime;

    private Date modifyTime;
    
    private Date tickTime;

    private String remark;

    private String iconUrl;

}
