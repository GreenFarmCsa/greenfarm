package com.callforcode.greenfarm.fabric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pojo {

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
