package com.callforcode.greenfarm.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GFOrderDetailVo {

    private Integer orderId;

    private Integer productId;

    private String productName;

    private BigDecimal price;

    private Integer count;

    private String imageUrl;

    private String remark;

}
