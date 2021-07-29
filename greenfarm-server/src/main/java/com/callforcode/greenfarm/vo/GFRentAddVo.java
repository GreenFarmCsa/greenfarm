package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GFRentAddVo {

    private Integer landId;

    private Integer farmId;

    private Integer seedId;

    private String username;

    private BigDecimal rentPrice;

    private Integer area;

    private Date rentStartTime;

    private Date rentEndTime;

    private String confirmCrops;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
