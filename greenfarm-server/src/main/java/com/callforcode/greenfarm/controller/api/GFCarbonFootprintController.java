package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFCarbonFootprintAddVo;
import com.callforcode.greenfarm.vo.GFCarbonFootprintVo;
import com.callforcode.greenfarm.vo.GFDashboardFootprintVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Carbon-Footprint API")
@RequestMapping("/carbon-footprint")
public interface GFCarbonFootprintController {

    @ApiOperation("query carbon footprint by username")
    @GetMapping("/query")
    ResultVo<List<GFCarbonFootprintVo>> queryCarbonFootprint(@RequestParam(value = "username") String userName);

    @ApiOperation("add carbon footprint")
    @Transactional
    @PostMapping("/add")
    ResultVo addCarbonFootprint(@RequestBody GFCarbonFootprintAddVo gfCarbonFootprintVo);

    @ApiOperation("query carbon footprint from dashboard")
    @GetMapping("/queryFromDashboard")
    ResultVo<GFDashboardFootprintVo> queryFromDashboard(@RequestParam(value = "username") String userName);

}
