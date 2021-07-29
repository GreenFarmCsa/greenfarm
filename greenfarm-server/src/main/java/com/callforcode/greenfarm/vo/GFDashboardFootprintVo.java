package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.List;

@Data
public class GFDashboardFootprintVo {

    private List<CarbonCredit> targetFootprint;

    private List<CarbonCredit> actualFootprint;

}
