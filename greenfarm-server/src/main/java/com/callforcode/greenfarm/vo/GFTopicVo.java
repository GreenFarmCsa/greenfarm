package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFTopicVo {

    private Integer topicId;

    private Integer communityId;

    private String username;

    private String topicName;

    private String topicContent;
    
    private String topicImageUrl;

    private Integer likeSum;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private Integer commentsCount;

    private String iconUrl;

    private Boolean hasLiked;

}
