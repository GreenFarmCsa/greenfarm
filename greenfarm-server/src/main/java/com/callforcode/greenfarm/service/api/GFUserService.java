package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFUser;

public interface GFUserService {

    GFUser login(String userName, String password);

    void logout(String userName);

    GFUser queryUserInfoByUserName(String userName);

    int addUserInfo(GFUser gfUser);
}
