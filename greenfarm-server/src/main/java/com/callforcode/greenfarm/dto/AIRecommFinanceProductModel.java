package com.callforcode.greenfarm.dto;

import lombok.Data;

@Data
public class AIRecommFinanceProductModel<T> {


    // CHECKSTYLE:OFF
    private String rescode;

    private T finance_product_list;
    // CHECKSTYLE:ON
}

