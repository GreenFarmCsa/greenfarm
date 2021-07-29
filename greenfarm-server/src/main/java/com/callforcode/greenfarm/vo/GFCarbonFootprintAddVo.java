package com.callforcode.greenfarm.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GFCarbonFootprintAddVo {

    private String footprintName;

    private String footprintCategory;

    private Integer farmId;

    private String username;

    private BigDecimal carbonReduction;

    private Integer carbonCredit;

    private String location;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date modifyTime;

    private String remark;

}
