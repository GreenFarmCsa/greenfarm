package com.callforcode.greenfarm.controller.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.callforcode.greenfarm.vo.GFLiveAddVo;
import com.callforcode.greenfarm.vo.GFLiveVo;
import com.callforcode.greenfarm.vo.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Live API")
@RequestMapping("/live")
public interface GFLiveController {

    @ApiOperation("query live by username")
    @GetMapping("/queryByUserName")
    ResultVo<List<GFLiveVo>> queryByUserName(@RequestParam(value = "username") String username);

    @ApiOperation("query live by farm id")
    @GetMapping("/queryByFarmId")
    ResultVo<List<GFLiveVo>> queryByFarmId(@RequestParam(value = "farmId") String farmId);

    @SuppressWarnings("rawtypes")
    @ApiOperation("add live")
    @Transactional
    @PostMapping("/add")
    ResultVo<GFLiveVo> insert(@RequestBody GFLiveAddVo gfLiveVo);

    @SuppressWarnings("rawtypes")
    @ApiOperation("revise live")
    @Transactional
    @PostMapping("/revise")
    ResultVo updateLive(@RequestBody GFLiveVo gfLiveVo);

    @SuppressWarnings("rawtypes")
    @ApiOperation("live heartbeat")
    @Transactional
    @PostMapping("/tickTime")
    ResultVo getTickTime(@RequestParam(value = "liveId") String liveId);

}
