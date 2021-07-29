package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFProductCommentAddVo {

    private Integer productId;

    private String username;
    
    private String commentContent;
    
    private String commentImage;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
