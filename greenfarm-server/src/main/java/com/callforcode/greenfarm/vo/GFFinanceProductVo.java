package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFFinanceProductVo {

    private Integer financeProductId;

    private String financeProductName;

    private String financeProductNo;

    private String financeProductDesc;

    private String orgName;

    private String financeProductCategory;

    private String financeLimit;

    private String validPeriod;

    private Date createTime;

    private Date modifyTime;

    private String remark;
    
    private boolean isSigned;
}
