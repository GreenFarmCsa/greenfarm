package com.callforcode.greenfarm.controller.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.controller.api.GFPlantTaskController;
import com.callforcode.greenfarm.entity.*;
import com.callforcode.greenfarm.exception.SeedNotFoundException;
import com.callforcode.greenfarm.service.api.GFFarmService;
import com.callforcode.greenfarm.service.api.GFLandService;
import com.callforcode.greenfarm.service.api.GFPlantTaskService;
import com.callforcode.greenfarm.service.api.GFSeedService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class GFPlantTaskControllerImpl implements GFPlantTaskController {

    private GFPlantTaskService service;

    private GFLandService landService;

    private GFFarmService farmService;

    private GFSeedService seedService;

    @Override
    public ResultVo<List<GFPlantTaskVo>> queryPlantTask(String userName) {
        List<GFPlantTask> plantTasks = service.queryByUserName(userName);
        List<GFPlantTaskVo> plantTaskVos = new ArrayList<>();
        for (GFPlantTask plantTask : plantTasks) {
            Integer landId = plantTask.getLandId();
            GFLand gfLand = landService.queryLandByLandId(landId);
            int farmId = gfLand.getFarmId();
            GFFarmWithBLOBs gfFarm = farmService.queryFarmByFarmId(farmId);
            String farmName = gfFarm.getFarmName();
            GFPlantTaskVo gfPlantTaskVo = BeanUtils.copyBean(plantTask, GFPlantTaskVo.class);
            List<GFTaskStep> taskSteps = service.queryTaskStep(plantTask.getTaskId());
            int length = taskSteps.size();
            int totalSteps = length + 1;
            int count = 0;
            String status = plantTask.getStatus();
            if (GreenFarmConst.GRF_TASK_STATUS_COMPLETE.equals(status)) {
                count = totalSteps;
            } else {
                for (int i = 0; i < length; i++) {
                    GFTaskStep taskStep = taskSteps.get(i);
                    if (status.equals(taskStep.getPlantStep())) {
                        count = i + 1;
                    }
                }
            }
            double res = (double) count / (double) totalSteps;
            String percentage = getPercentage(res);
            gfPlantTaskVo.setFarmName(farmName);
            gfPlantTaskVo.setFarmId(Integer.toString(farmId));
            gfPlantTaskVo.setPercentage(percentage);
            String imageUrl = gfFarm.getImageUrl();
            String iconUrl = gfFarm.getIconUrl();
            gfPlantTaskVo.setImageUrl(imageUrl);
            gfPlantTaskVo.setIconUrl(iconUrl);
            int seedId = plantTask.getSeedId();
            GFSeed gfSeed = seedService.querySeedBySeedId(seedId);
            if (Objects.isNull(gfSeed)) {
                throw new SeedNotFoundException("the seed info not found.seedId=" + seedId);
            }
            gfPlantTaskVo.setSeedName(gfSeed.getSeedName());
            plantTaskVos.add(gfPlantTaskVo);
        }

        return BeanUtils.createResultVo(plantTaskVos);
    }

    @Override
    public ResultVo<List<GFPlantTaskStepVo>> queryPlantTaskStep(String taskId) {
        List<GFTaskStep> taskSteps = service.queryTaskStep(Integer.parseInt(taskId));
        return BeanUtils.createResultVo(BeanUtils.copyListBean(taskSteps, GFPlantTaskStepVo.class));
    }

    @Override
    public ResultVo<GFPlantTaskDetailVo> queryPlantTaskDetail(String stepId) {
        GFTaskDetail taskDetail = service.queryTaskDetail(Integer.parseInt(stepId));
        GFStepTemplate stepTemplate = service.queryStepTemplateByStepId(Integer.parseInt(stepId));
        GFPlantTaskDetailVo vo = BeanUtils.copyBean(taskDetail, GFPlantTaskDetailVo.class);
        if (Objects.nonNull(stepTemplate)) {
            vo.setVideoUrl(BeanUtils.splitToArray(stepTemplate.getVedioUrl(), ";"));
        } else {
            vo.setVideoUrl(BeanUtils.convertStringWithCommaToArray(null));
        }
        return BeanUtils.createResultVo(vo);
    }

    @Override
    public ResultVo updatePlantTaskDetail(RevisePlantTaskVo revisePlantTaskVo) {
        String operateRecord = BeanUtils.convertJson(revisePlantTaskVo.getOperRecord());
        GFTaskDetail gfTaskDetail = service.queryTaskDetail(revisePlantTaskVo.getStepId());
        gfTaskDetail.setOperRecord(operateRecord);
        gfTaskDetail.setStatus(GreenFarmConst.GRF_TASK_DETAIL_STATUS_APPROVED);
        service.reviseTaskDetail(gfTaskDetail);
        return new ResultVo();
    }

    @Override
    public ResultVo<GFPlantTaskVo> queryPlantTaskByProductId(Integer productId) {
        GFPlantTask plantTask = service.queryPlantTaskByProductId(productId);
        return BeanUtils.createResultVo(BeanUtils.copyBean(plantTask, GFPlantTaskVo.class));

    }

    private String getPercentage(double i) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        return nt.format(i);
    }

}
