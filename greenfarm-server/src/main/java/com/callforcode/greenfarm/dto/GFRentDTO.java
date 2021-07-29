package com.callforcode.greenfarm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GFRentDTO {
    @JsonProperty("rent_id")
    private String rentId;

    @JsonProperty("land_id")
    private String landId;

    @JsonProperty("farm_id")
    private String farmId;
    
    private String username;

    @JsonProperty("rent_price")
    private String rentPrice;

    private String area;

    @JsonProperty("rent_start_time")
    private String rentStartTime;

    @JsonProperty("rent_end_time")
    private String rentEndTime;

    @JsonProperty("confirm_crops")
    private String confirmCrops;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("modify_time")
    private String modifyTime;

    @JsonProperty("farm_location")
    private String farmLocation;

}
