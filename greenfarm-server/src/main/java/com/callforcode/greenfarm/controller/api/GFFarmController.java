package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFFarmAddVo;
import com.callforcode.greenfarm.vo.GFFarmVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Farm API")
@RequestMapping("/farm")
public interface GFFarmController {

    @ApiOperation("query all farms")
    @GetMapping("/queryAll")
    ResultVo<List<GFFarmVo>> queryAll();

    @ApiOperation("query farms by location")
    @GetMapping("/queryByLocation")
    ResultVo<List<GFFarmVo>> queryByLocation(@RequestParam(value = "location") String location);

    @ApiOperation("query farms by product")
    @GetMapping("/queryByProductName")
    ResultVo<List<GFFarmVo>> queryByProduct(@RequestParam(value = "productName") String productName);

    @ApiOperation("query farms by multiple conditions")
    @GetMapping("/query")
    ResultVo<List<GFFarmVo>> query(@RequestParam(value = "searchText") String searchText);

    @ApiOperation("query farm by farm id")
    @GetMapping("/queryById")
    ResultVo<GFFarmVo> queryFarmById(@RequestParam(value = "id") String farmId);

    @ApiOperation("query farms by username")
    @GetMapping("/queryByUserName")
    ResultVo<List<GFFarmVo>> queryByUserName(@RequestParam(value = "username") String userName);

    @ApiOperation("query farms by area")
    @GetMapping("/queryByTotalArea")
    ResultVo<List<GFFarmVo>> queryByTotalArea(@RequestParam(value = "totalArea") String totalArea);

    @SuppressWarnings("rawtypes")
    @ApiOperation("add farm")
    @Transactional
    @PostMapping("/add")
    ResultVo add(@RequestBody GFFarmAddVo gfFarmVo);

    @SuppressWarnings("rawtypes")
    @ApiOperation("revise farm")
    @Transactional
    @PutMapping("/revise")
    ResultVo update(@RequestBody GFFarmVo gfFarmVo);

    @SuppressWarnings("rawtypes")
    @ApiOperation("delete FarmRlnInfo by farm id")
    @Transactional
    @DeleteMapping("/deleteFarmRlnInfo")
    ResultVo deleteFarmRlnInfo(@RequestParam(value = "id") String farmId);
}
