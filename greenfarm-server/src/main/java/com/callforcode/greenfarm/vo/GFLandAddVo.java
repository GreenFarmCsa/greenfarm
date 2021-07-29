
package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GFLandAddVo {

    private Integer farmId;

    private BigDecimal price;

    private String suitedCrops;

    private Integer area;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}


