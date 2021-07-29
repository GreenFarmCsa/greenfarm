package com.callforcode.greenfarm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GFFinanceProdDTO {

    @JsonProperty("finance_product_id")
    private String financeProductId;

    @JsonProperty("org_name")
    private String orgName;

    @JsonProperty("finance_product_category")
    private String financeProductCategory;

    @JsonProperty("finance_limit")
    private String financeLimit;

    @JsonProperty("valid_period")
    private String validPeriod;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("modify_time")
    private String modifyTime;

    private String limit;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;



}
