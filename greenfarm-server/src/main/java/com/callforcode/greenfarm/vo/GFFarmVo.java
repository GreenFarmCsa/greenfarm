package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GFFarmVo {

    private Integer farmId;

    private String farmName;

    private String introduction;

    private String username;

    private Integer totalArea;

    private Integer idleArea;

    private String location;

    private String iconUrl;

    private String suitedCrops;

    private String rentPeriod;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private List<String> imageUrls;

    private String vrUrl;

    private String latitudeLongitude;

}
