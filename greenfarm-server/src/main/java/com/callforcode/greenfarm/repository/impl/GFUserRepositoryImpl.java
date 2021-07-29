package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.mapper.GFUserMapper;
import com.callforcode.greenfarm.repository.api.GFUserRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class GFUserRepositoryImpl implements GFUserRepository {

    @Autowired
    private GFUserMapper mapper;

    @Override
    public GFUser queryUserInfo(String userName) {
        return mapper.selectByPrimaryKey(userName);
    }

    @Override
    public int insertUserInfo(GFUser gfUser) {
        gfUser.setCreateTime(DateToolUtils.getCurrDate());
        gfUser.setModifyTime(gfUser.getCreateTime());
        return mapper.insertSelective(gfUser);
    }

    @Override
    public int updateUserInfo(GFUser gfUser) {
        gfUser.setModifyTime(DateToolUtils.getCurrDate());
        gfUser.setCreateTime(null);
        return mapper.updateByPrimaryKeySelective(gfUser);
    }

}
