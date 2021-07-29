package com.callforcode.greenfarm.dto;

import lombok.Data;

@Data
public class AIRecommProductModel<T> {

    //// CHECKSTYLE:OFF
    private String rescode;

    private T recommend_product_list;
    // CHECKSTYLE:ON
}
