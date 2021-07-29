package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFProductCommentVo {

    private Integer commentId;

    private Integer productId;

    private String username;

    private String iconUrl;

    private String commentContent;

    private String commentImage;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
