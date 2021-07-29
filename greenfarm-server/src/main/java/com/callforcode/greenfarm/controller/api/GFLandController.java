package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.GFLandAddVo;
import com.callforcode.greenfarm.vo.GFLandReviseVo;
import com.callforcode.greenfarm.vo.GFLandVo;
import com.callforcode.greenfarm.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Land API")
@RequestMapping("/land")
public interface GFLandController {

    @ApiOperation("query Lands by farm id")
    @GetMapping("/queryByFarmId")
    ResultVo<List<GFLandVo>> queryLandsByFarmId(@RequestParam(value = "id") String farmId);

    @ApiOperation("add land")
    @Transactional
    @PostMapping("/add")
    ResultVo addLand(@RequestBody GFLandAddVo gfLandVo);

    @ApiOperation("revise land")
    @Transactional
    @PutMapping("/revise")
    ResultVo updateLand(@RequestBody GFLandReviseVo gfLandVo);

}
