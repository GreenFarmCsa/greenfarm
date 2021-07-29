package com.callforcode.greenfarm.controller.api;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.callforcode.greenfarm.vo.GFFinanceProductVo;
import com.callforcode.greenfarm.vo.GFUserFinanceVo;
import com.callforcode.greenfarm.vo.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Finance Product API")
@RequestMapping("/finance-product")
public interface GFFinanceProductController {

    @ApiOperation("query finance products by username")
    @GetMapping("/queryByUserName")
    ResultVo<List<GFFinanceProductVo>> queryFinanceProducts(@RequestParam(value = "username") String username);

    @ApiOperation("query finance products by product id")
    @GetMapping("/queryById")
    ResultVo<GFFinanceProductVo> queryFinanceProduct(@RequestParam(value = "id") String financeProductId);

    @ApiOperation("sign finance product")
    @Transactional
    @PostMapping("/apply")
    ResultVo<GFUserFinanceVo> insertUserFinance(@RequestBody GFUserFinanceVo gfUserFinanceVo);

    @ApiOperation("break finance product")
    @Transactional
    @PostMapping("/break")
    ResultVo<GFUserFinanceVo> breakUserFinance(@RequestBody GFUserFinanceVo gfUserFinanceVo);

    @ApiOperation("query finance products by sign status")
    @GetMapping("/queryAllWithSignStatus")
    ResultVo<List<GFFinanceProductVo>> queryAllWithSignStatus(@RequestParam(value = "username") String username);

}
