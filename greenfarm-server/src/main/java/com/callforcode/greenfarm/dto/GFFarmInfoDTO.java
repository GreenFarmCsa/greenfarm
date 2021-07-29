package com.callforcode.greenfarm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GFFarmInfoDTO {
    @JsonProperty("farm_id")
    private String farmId;

    private String location;

    @JsonProperty("suited_crops")
    private String suitedCrops;

    @JsonProperty("rent_period")
    private String rentPeriod;

    @JsonProperty("total_area")
    private String totalArea;

    @JsonProperty("idle_area")
    private String idleArea;
}
