package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFStepTemplate;
import com.callforcode.greenfarm.entity.GFTaskDetail;
import com.callforcode.greenfarm.entity.GFTaskStep;

import java.util.List;

public interface GFPlantTaskRepository {

    List<GFStepTemplate> queryStepTemplate(Integer seedId);

    int addTask(GFPlantTask gfPlantTask);

    GFPlantTask queryTaskByLandId(Integer landId);

    int addTaskStep(GFTaskStep gfTaskStep);

    int updateTaskDetail(GFTaskDetail gfTaskDetail);

    List<GFPlantTask> queryByUserName(String userName);

    List<GFTaskStep> queryTaskStep(Integer taskId);

    GFTaskStep queryTaskStepByStepId(Integer stepId);

    GFTaskDetail queryTaskDetail(Integer stepId);

    int addTaskDetail(GFTaskDetail gfTaskdetail);

    int updateTaskStepStatus(Integer stepId, String status);

    int updatePlantTaskStatus(Integer taskId, String status);

    GFPlantTask queryPlantTaskByProductId(Integer productId);

    GFStepTemplate queryStepTemplateByStepId(Integer stepId);

    GFPlantTask queryTaskByTaskId(Integer taskId);
}
