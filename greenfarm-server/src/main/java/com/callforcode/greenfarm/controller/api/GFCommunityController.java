package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFCommunityMemberVo;
import com.callforcode.greenfarm.vo.GFCommunityTopicVo;
import com.callforcode.greenfarm.vo.GFCommunityVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Community API")
@RequestMapping("/community")
public interface GFCommunityController {

    @ApiOperation("query community by farm id")
    @GetMapping("/queryByFarmId")
    ResultVo<GFCommunityVo> queryByFarmId(@RequestParam(value = "id") String farmId);

    @ApiOperation("query joined communities by username")
    @GetMapping("/queryByUserName")
    ResultVo<List<GFCommunityVo>> queryByUserName(@RequestParam(value = "name") String userName);

    @ApiOperation("join community")
    @Transactional
    @PostMapping("/join")
    ResultVo join(@RequestBody GFCommunityMemberVo communityMemberVo);

    @ApiOperation("query community and topic info by farm id")
    @GetMapping("/queryTopicCommunityByFarmId")
    ResultVo<GFCommunityTopicVo> queryTopicCommunityByFarmId(@RequestParam(value = "id") String farmId);

}
