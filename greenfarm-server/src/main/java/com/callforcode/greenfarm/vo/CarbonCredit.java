package com.callforcode.greenfarm.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "Carbon footprint chart VO")
@Data
public class CarbonCredit {

    private String date;

    private Integer value;

}
