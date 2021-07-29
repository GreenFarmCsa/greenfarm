package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFCommunityVo {

    private Integer communityId;

    private Integer farmId;

    private String farmName;

    private String communityName;

    private String introduction;

    private String communityImageUrl;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
