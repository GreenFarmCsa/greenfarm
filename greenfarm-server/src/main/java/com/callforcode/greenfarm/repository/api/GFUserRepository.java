package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFUser;

public interface GFUserRepository {

    GFUser queryUserInfo(String userName);

    int insertUserInfo(GFUser gfUser);

    int updateUserInfo(GFUser gfUser);

}
