package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFUserVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "User API")
@RequestMapping("/user")
public interface GFUserController {

    @ApiOperation("login")
    @PostMapping("/login")
    ResultVo<GFUserVo> login(@RequestParam(value = "userName") String userName,
                             @RequestParam(value = "password") String password);

    @ApiOperation("logout")
    @GetMapping("/logout")
    ResultVo logout(@RequestParam(value = "username") String userName);

    @ApiOperation("query user info")
    @GetMapping("/queryInfo")
    ResultVo<GFUserVo> queryUserInfo(@RequestParam(value = "username") String userName);

    @ApiOperation("add user")
    @Transactional
    @PostMapping("/add")
    ResultVo insertUserInfo(@RequestBody GFUserVo gfUserVo);

}
