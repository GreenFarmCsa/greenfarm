package com.callforcode.greenfarm.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GFTopicAddVo {

    private Integer communityId;

    private String username;

    private String topicName;

    private String topicContent;
    
    private String topicImageUrl;

    private Integer likeSum;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date modifyTime;

    private String remark;

}
