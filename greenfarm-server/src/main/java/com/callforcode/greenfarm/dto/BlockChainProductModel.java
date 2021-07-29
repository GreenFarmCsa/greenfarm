package com.callforcode.greenfarm.dto;

import lombok.Data;

@Data
public class BlockChainProductModel {

    private String productId;

    private String userName;

    private String plantStepName;

    private String time;

    private String farmName;

    private String farmLocation;

    private int landId;

    private int landArea;

    private String videoHash;

    private String imageHash;

}
