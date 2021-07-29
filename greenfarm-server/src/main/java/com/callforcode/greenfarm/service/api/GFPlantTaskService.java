package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFPlantTask;
import com.callforcode.greenfarm.entity.GFStepTemplate;
import com.callforcode.greenfarm.entity.GFTaskDetail;
import com.callforcode.greenfarm.entity.GFTaskStep;

import java.util.List;

public interface GFPlantTaskService {

    List<GFPlantTask> queryByUserName(String userName);

    GFTaskDetail queryTaskDetail(Integer stepId);

    List<GFTaskStep> queryTaskStep(Integer taskId);

    int reviseTaskDetail(GFTaskDetail gfTaskDetail);

    void addTask(Integer landId, Integer seedId, String userName, boolean subscribed);

    GFPlantTask queryPlantTaskByProductId(Integer productId);

    GFStepTemplate queryStepTemplateByStepId(Integer stepId);

    int reviseOprRecordByStepId(Integer stepId, String url);

    int updateTaskStepStatus(Integer stepId, String status);
}
