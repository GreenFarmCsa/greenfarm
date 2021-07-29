package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFStepTemplate;
import com.callforcode.greenfarm.entity.GFTaskDetail;
import com.callforcode.greenfarm.entity.GFTaskStep;
import com.callforcode.greenfarm.mapper.GFPlantTaskMapper;
import com.callforcode.greenfarm.mapper.GFStepTemplateMapper;
import com.callforcode.greenfarm.mapper.GFTaskDetailMapper;
import com.callforcode.greenfarm.mapper.GFTaskStepMapper;
import com.callforcode.greenfarm.repository.api.GFPlantTaskRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFPlantTaskRepositoryImpl implements GFPlantTaskRepository {

    @Autowired
    private GFPlantTaskMapper taskMapper;

    @Autowired
    private GFTaskStepMapper stepMapper;

    @Autowired
    private GFTaskDetailMapper detailMapper;

    @Autowired
    private GFStepTemplateMapper templateMapper;

    @Override
    public List<GFStepTemplate> queryStepTemplate(Integer seedId) {
        return templateMapper.queryStepTemplateBySeedId(seedId);
    }

    @Override
    public int addTask(GFPlantTask gfPlantTask) {
        gfPlantTask.setCreateTime(DateToolUtils.getCurrDate());
        gfPlantTask.setModifyTime(DateToolUtils.getCurrDate());
        return taskMapper.insertSelective(gfPlantTask);
    }

    @Override
    public GFPlantTask queryTaskByLandId(Integer landId) {
        return taskMapper.queryTaskByLandId(landId);
    }

    @Override
    public int addTaskStep(GFTaskStep gfTaskStep) {
        return stepMapper.insertSelective(gfTaskStep);
    }

    @Override
    public int updateTaskDetail(GFTaskDetail gfTaskDetail) {
        gfTaskDetail.setModifyTime(DateToolUtils.getCurrDate());
        gfTaskDetail.setCreateTime(null);
        return detailMapper.updateTaskDetailByStepId(gfTaskDetail);
    }

    @Override
    public List<GFPlantTask> queryByUserName(String userName) {
        return taskMapper.queryByUserName(userName);
    }

    @Override
    public List<GFTaskStep> queryTaskStep(Integer taskId) {
        return stepMapper.queryTaskStepByTaskId(taskId);
    }

    @Override
    public GFTaskStep queryTaskStepByStepId(Integer stepId) {
        return stepMapper.selectByPrimaryKey(stepId);
    }

    @Override
    public GFTaskDetail queryTaskDetail(Integer stepId) {
        return detailMapper.queryTaskDetailByStepId(stepId);
    }

    @Override
    public int addTaskDetail(GFTaskDetail gfTaskdetail) {
        gfTaskdetail.setCreateTime(DateToolUtils.getCurrDate());
        gfTaskdetail.setModifyTime(DateToolUtils.getCurrDate());
        return detailMapper.insertSelective(gfTaskdetail);
    }

    @Override
    public GFPlantTask queryPlantTaskByProductId(Integer productId) {
        return taskMapper.queryPlantTaskByProductId(productId);
    }

    @Override
    public int updateTaskStepStatus(Integer stepId, String status) {
        GFTaskStep gfTaskStep = new GFTaskStep();
        gfTaskStep.setStepId(stepId);
        gfTaskStep.setStatus(status);
        return stepMapper.updateByPrimaryKeySelective(gfTaskStep);
    }

    @Override
    public int updatePlantTaskStatus(Integer taskId, String status) {
        GFPlantTask record = new GFPlantTask();
        record.setTaskId(taskId);
        record.setStatus(status);
        record.setModifyTime(DateToolUtils.getCurrDate());
        record.setCreateTime(null);
        return taskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public GFStepTemplate queryStepTemplateByStepId(Integer stepId) {
        return templateMapper.queryStepTemplateByStepId(stepId);
    }

    @Override
    public GFPlantTask queryTaskByTaskId(Integer taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

}
