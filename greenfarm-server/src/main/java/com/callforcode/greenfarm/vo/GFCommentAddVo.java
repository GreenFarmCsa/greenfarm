package com.callforcode.greenfarm.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GFCommentAddVo {

    private Integer topicId;

    private String username;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Date modifyTime;

    private String remark;

    private String commentContent;
}
