package com.callforcode.greenfarm.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GFUserVo {

    private String username;

    private String rolename;

    private String fullname;
    
    private String location;
    
    private String password;

    private String phone;

    private String email;

    private String sex;

    private String iconUrl;

    private String description;

    private Integer carbonCredit;

    private List<String> carbonMedals;

    private Date createTime;

    private Date modifyTime;

    private String remark;

}
