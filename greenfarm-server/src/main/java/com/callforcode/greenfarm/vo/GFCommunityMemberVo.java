package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class GFCommunityMemberVo {

    private Integer communityId;

    private String username;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
