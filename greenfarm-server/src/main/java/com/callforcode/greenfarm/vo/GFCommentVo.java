package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFCommentVo {

    private Integer commentId;

    private Integer topicId;

    private String username;

    private Date createTime;

    private Date modifyTime;

    private String remark;

    private String commentContent;
}
