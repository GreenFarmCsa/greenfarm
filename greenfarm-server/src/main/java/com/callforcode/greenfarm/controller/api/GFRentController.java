package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Rent Land API")
@RequestMapping("/rent")
public interface GFRentController {

    @ApiOperation("create rent")
    @Transactional
    @PostMapping("/add")
    ResultVo createRent(@RequestBody GFRentAddVo gfRentVo);

    @ApiOperation("query rent lands")
    @GetMapping("/queryRentLands")
    ResultVo<List<GFRentFarmVo>> queryRentLands(@RequestParam(value = "username") String userName);

    @ApiOperation("query subscriber")
    @GetMapping("/querySubscriber")
    ResultVo<GFBoxConfirmVo> querySubscriber(@RequestParam(value = "taskId") String taskId);
}
