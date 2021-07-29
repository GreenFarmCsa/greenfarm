package com.callforcode.greenfarm.dto;

import lombok.Data;

@Data
public class AIRecommFarmModel<T> {

    // CHECKSTYLE:OFF
    private String rescode;

    private T recommend_farm_list;
    // CHECKSTYLE:ON

}
