package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GFGFShoppingCartVo {

    private Integer cartId;

    private String username;

    private Integer productId;

    private Integer number;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private BigDecimal price;

    private String imageUrl;

    private String productName;

    private Integer carbonCredit;
    
    private BigDecimal donateAmount;

}
