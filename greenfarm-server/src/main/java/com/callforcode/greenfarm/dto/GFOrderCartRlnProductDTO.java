package com.callforcode.greenfarm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GFOrderCartRlnProductDTO {
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("farm_id")
    private String farmId;

    private String username;

    private String plantername;

    @JsonProperty("product_name")
    private String productName;

    private String category;

    private String price;

    @JsonProperty("carbon_credit")
    private String carbonCredit;

    private String number;

    @JsonProperty("sale_number")
    private String saleNumber;

    private String identifications;

    @JsonProperty("carbon_emission")
    private String carbonEmission;

    @JsonProperty("donate_amount")
    private String donateAmount;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("modify_time")
    private String modifyTime;

    @JsonProperty("order_id")
    private String orderId;

}
