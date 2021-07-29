package com.callforcode.greenfarm.vo;

import lombok.Data;

@Data
public class GFOrderDetailAddVo {

    private Integer productId;

    private String productName;

    private Integer count;

    private String imageUrl;
    
    private String remark;

}
