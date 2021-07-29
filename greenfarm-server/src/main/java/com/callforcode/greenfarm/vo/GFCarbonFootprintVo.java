package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GFCarbonFootprintVo {

    private Integer footprintId;

    private String footprintName;

    private String footprintCategory;

    private Integer farmId;

    private String username;

    private BigDecimal carbonReduction;

    private Integer carbonCredit;

    private String location;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
