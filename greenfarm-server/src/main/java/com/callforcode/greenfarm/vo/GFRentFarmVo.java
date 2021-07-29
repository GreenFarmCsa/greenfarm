package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.List;

@Data
public class GFRentFarmVo {

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

    private String remark;

    private List<String> imageUrls;

    private List<GFRentVo> lands;

    private String phone;

    private String email;
    
    private String vrUrl;

}
