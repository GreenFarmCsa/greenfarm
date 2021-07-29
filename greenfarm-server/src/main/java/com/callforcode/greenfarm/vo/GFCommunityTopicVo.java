package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GFCommunityTopicVo {

    private Integer communityId;

    private Integer farmId;

    private String communityName;

    private String introduction;

    private String communityImageUrl;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private List<GFTopicVo> topics;

}
