package com.callforcode.greenfarm.controller.api;

import com.callforcode.greenfarm.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(tags = "Plant Task API")
@RequestMapping("/plant-task")
public interface GFPlantTaskController {

    @ApiOperation("query plant tasks by username")
    @GetMapping("/query")
    ResultVo<List<GFPlantTaskVo>> queryPlantTask(@RequestParam(value = "username") String userName);

    @ApiOperation("query plant steps by task id")
    @GetMapping("/querySteps")
    ResultVo<List<GFPlantTaskStepVo>> queryPlantTaskStep(@RequestParam(value = "id") String taskId);

    @ApiOperation("query task detail by step id")
    @GetMapping("/queryTaskDetail")
    ResultVo<GFPlantTaskDetailVo> queryPlantTaskDetail(@RequestParam(value = "id") String stepId);

    @ApiOperation("revise task detail by step id")
    @Transactional
    @PutMapping("/revise")
    ResultVo updatePlantTaskDetail(@RequestBody RevisePlantTaskVo revisePlantTaskVo);

    @ApiOperation("query plant task by product id")
    @GetMapping("/queryPlantTaskByProductId")
    ResultVo<GFPlantTaskVo> queryPlantTaskByProductId(@RequestParam(value = "productId") Integer productId);

}
