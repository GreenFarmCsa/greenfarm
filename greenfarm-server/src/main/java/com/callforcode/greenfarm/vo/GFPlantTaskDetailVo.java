package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GFPlantTaskDetailVo {

    private Integer detailId;

    private Integer stepId;

    private String username;

    private String location;

    private String status;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private String operRecord;

    private List<String> videoUrl;

}
