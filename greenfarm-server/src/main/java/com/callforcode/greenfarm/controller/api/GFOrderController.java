package com.callforcode.greenfarm.controller.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.callforcode.greenfarm.vo.GFOrderAddVo;
import com.callforcode.greenfarm.vo.GFOrderVo;
import com.callforcode.greenfarm.vo.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Order API")
@RequestMapping("/order")
public interface GFOrderController {

    @ApiOperation("add order")
    @Transactional
    @PostMapping("/add")
    ResultVo<GFOrderVo> createOrder(@RequestBody GFOrderAddVo gfOrderVo);

    @ApiOperation("query orders purchased")
    @GetMapping("/queryPurchased")
    ResultVo<List<GFOrderVo>> queryPurchased(@RequestParam(value = "username") String userName);

    @ApiOperation("query orders sold")
    @GetMapping("/querySold")
    ResultVo<List<GFOrderVo>> querySold(@RequestParam(value = "username") String userName);

}
