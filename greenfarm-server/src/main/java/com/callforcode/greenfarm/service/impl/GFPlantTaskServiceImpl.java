package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.*;
import com.callforcode.greenfarm.exception.*;
import com.callforcode.greenfarm.repository.api.GFCarbonFootprintRepository;
import com.callforcode.greenfarm.repository.api.GFFarmRepository;
import com.callforcode.greenfarm.repository.api.GFPlantTaskRepository;
import com.callforcode.greenfarm.repository.api.GFUserRepository;
import com.callforcode.greenfarm.service.api.GFPlantTaskService;
import com.callforcode.greenfarm.util.BeanUtils;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.vo.RevisePlantTaskVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GFPlantTaskServiceImpl implements GFPlantTaskService {

    @Autowired
    private GFPlantTaskRepository repository;

    @Autowired
    private GFCarbonFootprintRepository gfCarbonFootprintRepository;

    @Autowired
    private GFUserRepository gfUserRepository;

    @Autowired
    private GFFarmRepository farmRepository;

    @Override
    public List<GFPlantTask> queryByUserName(String userName) {
        return repository.queryByUserName(userName);
    }

    @Override
    public GFTaskDetail queryTaskDetail(Integer stepId) {
        GFTaskDetail taskDetail = repository.queryTaskDetail(stepId);
        return taskDetail;
    }

    @Override
    public List<GFTaskStep> queryTaskStep(Integer taskId) {
        return repository.queryTaskStep(taskId);
    }

    @Override
    public int reviseTaskDetail(GFTaskDetail gfTaskDetail) {
        int stepId = gfTaskDetail.getStepId();
        //update task-detail operRecord
        int res = repository.updateTaskDetail(gfTaskDetail);
        if (res == 0) {
            throw new PlantTaskNotFoundException("cannot find plant-task detail");
        } else if (res > 1) {
            throw new UpdateRecordCountNotMatchException("update plant-task detail error", 1, res);
        }
        //update task-step status
        res = repository.updateTaskStepStatus(stepId, GreenFarmConst.GRF_STEP_STATUS_END);
        if (res != 1) {
            throw new UpdateRecordCountNotMatchException("update task-step error", 1, res);
        }
        GFTaskStep taskStep = repository.queryTaskStepByStepId(stepId);
        if (Objects.isNull(taskStep)) {
            throw new TaskStepNotFoundException("cannot find task-step");
        }
        Integer taskId = taskStep.getTaskId();
        //update plant-task status
        List<GFTaskStep> gfTaskSteps = repository.queryTaskStep(taskId);
        for (GFTaskStep step : gfTaskSteps) {
            if (GreenFarmConst.GRF_STEP_STATUS_BEING.equals(step.getStatus())) {
                res = repository.updatePlantTaskStatus(taskId, step.getPlantStep());
                if (res != 1) {
                    throw new UpdateRecordCountNotMatchException("update plant-task status error", 1, res);
                }
                break;
            }
        }
        //add carbon-footprint
        GFCarbonFootprint gfCarbonFootprint = createCarbonFootprint(gfTaskDetail);
        res = gfCarbonFootprintRepository.addCarbonFootprint(gfCarbonFootprint);
        if (res != 1) {
            throw new UpdateRecordCountNotMatchException("carbonFootprint add error", 1, res);
        }
        String userName = gfTaskDetail.getUsername();
        //update user carbon-credit
        GFUser gfUser = gfUserRepository.queryUserInfo(userName);
        int carbonCredit = gfUser.getCarbonCredit() + gfCarbonFootprint.getCarbonCredit();
        gfUser.setCarbonCredit(carbonCredit);
        res = gfUserRepository.updateUserInfo(gfUser);
        if (res != 1) {
            throw new UserNotFoundException("user info not found.username=" + userName);
        }
        //add actual carbon-credit-daily
        Date currDate = DateToolUtils.getCurrDateWithoutTime();
        GFCarbonCreditDaily gfCarbonCreditDaily = gfCarbonFootprintRepository.queryCarbonCreditDaily(userName, currDate, GreenFarmConst.GRF_CARBON_CREDIT_DAILY_TYPE_ACTUAL);
        if (gfCarbonCreditDaily == null) {
            gfCarbonCreditDaily = new GFCarbonCreditDaily();
            gfCarbonCreditDaily.setUsername(userName);
            gfCarbonCreditDaily.setCarbonCredit(carbonCredit);
            gfCarbonCreditDaily.setCarbonDate(currDate);
            gfCarbonCreditDaily.setType(GreenFarmConst.GRF_CARBON_CREDIT_DAILY_TYPE_ACTUAL);
            res = gfCarbonFootprintRepository.addCarbonCreditDaily(gfCarbonCreditDaily);
        } else {
            gfCarbonCreditDaily.setCarbonCredit(carbonCredit);
            res = gfCarbonFootprintRepository.updateCarbonCreditDaily(gfCarbonCreditDaily);
        }
        if (res != 1) {
            throw new UpdateRecordCountNotMatchException("carbon-Credit-daily update error", 1, res);
        }
        return res;
    }

    @Override
    public void addTask(Integer landId, Integer seedId, String userName, boolean subscribed) {
        List<GFStepTemplate> stepTemplates = repository.queryStepTemplate(seedId);
        if (stepTemplates == null || stepTemplates.size() == 0) {
            throw new TaskStepTemplateNotFoundException("cannot find task-step-template.seedId=" + seedId);
        }
        //add plant task
        GFPlantTask gfPlantTask = new GFPlantTask();
        gfPlantTask.setLandId(landId);
        gfPlantTask.setSeedId(seedId);
        gfPlantTask.setUsername(userName);
        gfPlantTask.setStatus(stepTemplates.get(0).getPlantStep());
        int res = repository.addTask(gfPlantTask);
        if (res != 1) {
            throw new UpdateRecordCountNotMatchException("add task error", 1, res);
        }

        GFUser gfUser = gfUserRepository.queryUserInfo(userName);
        int caronCredit = gfUser.getCarbonCredit();

        for (int i = 0; i < stepTemplates.size(); i++) {
            GFStepTemplate stepTemplate = stepTemplates.get(i);
            //add task step
            GFTaskStep gfTaskStep = BeanUtils.copyBean(stepTemplate, GFTaskStep.class);
            gfTaskStep.setTaskId(gfPlantTask.getTaskId());
            gfTaskStep.setStatus(GreenFarmConst.GRF_STEP_STATUS_BEING);
            if (subscribed && GreenFarmConst.GRF_PLANT_STEP_SELL
                    .equals(gfTaskStep.getPlantStep())) {
                gfTaskStep.setPlantStep(GreenFarmConst.GRF_PLANT_STEP_BOX);
            }

            res = repository.addTaskStep(gfTaskStep);
            if (res != 1) {
                throw new UpdateRecordCountNotMatchException("add task step error ", 1, res);
            }

            //add task detail
            GFTaskDetail gfTaskDetail = new GFTaskDetail();
            gfTaskDetail.setStepId(gfTaskStep.getStepId());
            if (subscribed) {
                GFFarm farm = farmRepository.queryByLandId(landId);
                if (Objects.isNull(farm)) {
                    throw new FarmNotFoundException("farm info not found.landId=" + landId);
                }
                gfTaskDetail.setUsername(farm.getUsername());
            } else {
                gfTaskDetail.setUsername(userName);
            }
            gfTaskDetail.setStatus(GreenFarmConst.GRF_TASK_DETAIL_STATUS_UNAPPROVED);
            gfTaskDetail.setRemark(gfTaskStep.getRemark());
            res = repository.addTaskDetail(gfTaskDetail);
            if (res != 1) {
                throw new UpdateRecordCountNotMatchException("add task detail error ", 1, res);
            }
            //query target caron-credit-daily
            Integer carbonCreditTemp = 0;
            for (int k = 0; k <= i; k++) {
                carbonCreditTemp += stepTemplates.get(k).getCarbonCredit();
            }
            GFCarbonCreditDaily gfCarbonCreditDaily = gfCarbonFootprintRepository.queryCarbonCreditDaily(userName, gfTaskStep.getBeginTime(), GreenFarmConst.GRF_CARBON_CREDIT_DAILY_TYPE_TARGET);
            if (gfCarbonCreditDaily == null) {
                gfCarbonCreditDaily = new GFCarbonCreditDaily();
                gfCarbonCreditDaily.setUsername(userName);
                gfCarbonCreditDaily.setCarbonCredit(carbonCreditTemp);
                gfCarbonCreditDaily.setCarbonDate(gfTaskStep.getBeginTime());
                gfCarbonCreditDaily.setType(GreenFarmConst.GRF_CARBON_CREDIT_DAILY_TYPE_TARGET);
                res = gfCarbonFootprintRepository.addCarbonCreditDaily(gfCarbonCreditDaily);
            } else {
                gfCarbonCreditDaily.setCarbonCredit(gfCarbonCreditDaily.getCarbonCredit() + carbonCreditTemp);
                res = gfCarbonFootprintRepository.updateCarbonCreditDaily(gfCarbonCreditDaily);
            }
            if (res != 1) {
                throw new UpdateRecordCountNotMatchException("add task detail error ", 1, res);
            }
        }
    }

    @Override
    public GFPlantTask queryPlantTaskByProductId(Integer productId) {
        return repository.queryPlantTaskByProductId(productId);
    }

    private GFCarbonFootprint createCarbonFootprint(GFTaskDetail gfTaskDetail) {
        GFCarbonFootprint gfCarbonFootprint = new GFCarbonFootprint();
        gfCarbonFootprint.setFootprintCategory(GreenFarmConst.GRF_FOOT_PRINT_CATEGORY_PLANT);
        gfCarbonFootprint.setLocation(gfTaskDetail.getLocation());
        gfCarbonFootprint.setUsername(gfTaskDetail.getUsername());
        GFTaskStep gfTaskStep = repository.queryTaskStepByStepId(gfTaskDetail.getStepId());
        if (Objects.isNull(gfTaskStep)) {
            throw new TaskStepNotFoundException("cannot find task-step");
        }
        gfCarbonFootprint.setFootprintName(gfTaskStep.getPlantStep());
        gfCarbonFootprint.setCarbonCredit(gfTaskStep.getCarbonCredit());
        gfCarbonFootprint.setCreateTime(DateToolUtils.getCurrDate());
        gfCarbonFootprint.setModifyTime(DateToolUtils.getCurrDate());
        gfCarbonFootprint.setRemark(gfTaskStep.getRemark());

        gfCarbonFootprint.setFarmId(1);
        return gfCarbonFootprint;
    }

    @Override
    public GFStepTemplate queryStepTemplateByStepId(Integer stepId) {

        return repository.queryStepTemplateByStepId(stepId);
    }

    @Override
    public int reviseOprRecordByStepId(Integer stepId, String url) {
        GFTaskDetail taskDetail = repository.queryTaskDetail(stepId);
        if (Objects.isNull(taskDetail)) {
            throw new GFException("TaskDetail info not found.stepId=" + stepId);
        }
        String operRecord = taskDetail.getOperRecord();
        List<RevisePlantTaskVo.FileInfo> list = BeanUtils.convertOperateRecord(operRecord);
        RevisePlantTaskVo.FileInfo fileInfo = new RevisePlantTaskVo.FileInfo();
        fileInfo.setType(GreenFarmConst.GRF_OPERATE_RECORD_TYPE_LIVE);
        fileInfo.setUrl(url);
        if (Objects.isNull(list)) {
            list = new ArrayList<>();
        }
        list.add(fileInfo);
        taskDetail.setOperRecord(BeanUtils.convertJson(list));
        int res = repository.updateTaskDetail(taskDetail);
        if (res != 1) {
            throw new UpdateRecordCountNotMatchException("TaskDetail update error", 1, res);
        }
        return res;
    }

    @Override
    public int updateTaskStepStatus(Integer stepId, String status) {
        return repository.updateTaskStepStatus(stepId, status);
    }
}
