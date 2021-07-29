package com.callforcode.greenfarm.vo;

import lombok.Data;

@Data
public class GFProductBoxVo {

    private Integer taskId;
    
    private Integer stepId;

    private Integer farmId;

    private Integer landId;

    private String username;

    private String productName;

    private String category;

    private String weight;

}
