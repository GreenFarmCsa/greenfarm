package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFFarmVo;
import com.callforcode.greenfarm.vo.GFFinanceProductVo;
import com.callforcode.greenfarm.vo.GFProductVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Recommendation API")
@RequestMapping("/recommendation")
public interface RecommendationController {

    @ApiOperation("query recommended farms")
    @GetMapping("/queryFarms")
    ResultVo<List<GFFarmVo>> queryFarms(@RequestParam(value = "username") String userName);

    @ApiOperation("query recommended agriculture product")
    @GetMapping("/queryProducts")
    ResultVo<List<GFProductVo>> queryProducts(@RequestParam(value = "username") String userName);

    @ApiOperation("query recommended finance product")
    @GetMapping("/queryFinanceProducts")
    ResultVo<List<GFFinanceProductVo>> queryFinanceProducts(@RequestParam(value = "username") String userName);

}
