package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFPlantTaskVo {

    private Integer taskId;

    private String username;

    private Integer landId;

    private Integer seedId;

    private Integer productId;

    private String seedName;

    private String status;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    /*add extra field from farm info*/
    private String farmName;

    private String farmId;

    private String percentage;

    private String imageUrl;

    private String iconUrl;

}
