package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GFSeedVo {

    private Integer seedId;

    private Integer productId;

    private String seedName;

    private String seedIntroduction;

    private String production;

    private String lowestPlanting;

    private BigDecimal seedPrice;

    private String fertilizerName;

    private BigDecimal fertilizerPrice;

    private String fertilizerAmount;

    private String wateringQuantity;

    private Integer maturePeriod;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
