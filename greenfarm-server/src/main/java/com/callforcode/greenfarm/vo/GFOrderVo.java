package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GFOrderVo {

    private Integer orderId;

    private String username;

    private String address;

    private BigDecimal money;

    private Integer carbonCredit;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private List<GFOrderDetailVo> detail;
    
    private String phone;
}
