package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFSeedVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Seed API")
@RequestMapping("/seed")
public interface GFSeedController {

    @ApiOperation("query information about all seeds")
    @GetMapping("/query")
    ResultVo<List<GFSeedVo>> query();

    @ApiOperation("query seed information by seed id")
    @GetMapping("/queryById")
    ResultVo<GFSeedVo> queryById(@RequestParam(value = "id") String seedId);

}
