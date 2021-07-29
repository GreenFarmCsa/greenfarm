package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.exception.UserNotFoundException;
import com.callforcode.greenfarm.exception.UserOrPasswordErrorException;
import com.callforcode.greenfarm.repository.api.GFUserRepository;
import com.callforcode.greenfarm.service.api.GFUserService;
import com.callforcode.greenfarm.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class GFUserServiceImpl implements GFUserService {

    @Autowired
    private GFUserRepository repository;

    @Autowired
    private HttpSession session;

    @Override
    public GFUser login(String userName, String password) {
        GFUser gfUser = repository.queryUserInfo(userName);
        if (Objects.isNull(gfUser)) {
            throw new UserNotFoundException("User not found");
        }
        if (!GreenFarmConst.GRF_USER_GUEST.contains(userName)) {
            if (!gfUser.getPassword().equals(EncryptUtil.encrypt(password))) {
                throw new UserOrPasswordErrorException("Username or Password is not correct");
            }
            session.setMaxInactiveInterval(3600 * 10);
        }
        gfUser.setPassword(null);

        session.setAttribute(GreenFarmConst.GRF_LOGIN_SESSION, gfUser);
        return gfUser;
    }

    @Override
    public void logout(String userName) {
        session.removeAttribute(GreenFarmConst.GRF_LOGIN_SESSION);
    }

    @Override
    public GFUser queryUserInfoByUserName(String userName) {
        GFUser gfUser = repository.queryUserInfo(userName);
        if (Objects.isNull(gfUser)) {
            throw new UserNotFoundException("User not found");
        } else {
            gfUser.setPassword(null);
            return gfUser;
        }
    }

    @Override
    public int addUserInfo(GFUser gfUser) {
        gfUser.setPassword(EncryptUtil.encrypt(gfUser.getPassword()));
        int res = repository.insertUserInfo(gfUser);
        if (res == 1) {
            return res;
        } else {
            throw new UpdateRecordCountNotMatchException("Insert user error", 1, res);
        }
    }

}
