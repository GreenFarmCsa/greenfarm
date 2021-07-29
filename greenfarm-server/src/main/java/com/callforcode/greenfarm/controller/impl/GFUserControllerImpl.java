package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.controller.api.GFUserController;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.exception.UserOrPasswordErrorException;
import com.callforcode.greenfarm.service.api.GFUserService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.GFUserVo;
import com.callforcode.greenfarm.vo.ResultVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class GFUserControllerImpl implements GFUserController {

    private GFUserService service;

    @Override
    public ResultVo<GFUserVo> login(String userName, String password) {
        if (!GreenFarmConst.GRF_USER_GUEST.contains(userName) && (StringUtils.isBlank(userName) || StringUtils.isBlank(password))) {
            throw new UserOrPasswordErrorException("login failed, username/password can not be null!");
        }
        GFUser gfUser = service.login(userName, password);
        return BeanUtils.createResultVo(BeanUtils.copyBean(gfUser, GFUserVo.class));
    }

    @Override
    public ResultVo logout(String userName) {
        service.logout(userName);
        return new ResultVo<>();
    }

    @Override
    public ResultVo<GFUserVo> queryUserInfo(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new UserOrPasswordErrorException("query user info failed, username can not be null!");
        }
        GFUser user = service.queryUserInfoByUserName(userName);
        return BeanUtils.createResultVo(BeanUtils.copyBean(user, GFUserVo.class));
    }

    @Override
    public ResultVo insertUserInfo(GFUserVo gfUserVo) {
        GFUser gfUser = BeanUtils.copyBean(gfUserVo, GFUser.class);
        service.addUserInfo(gfUser);
        return new ResultVo();
    }
}
