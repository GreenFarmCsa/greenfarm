package com.callforcode.greenfarm.task.impl;

import com.callforcode.greenfarm.entity.GFLive;
import com.callforcode.greenfarm.exception.GFException;
import com.callforcode.greenfarm.service.api.GFLiveService;
import com.callforcode.greenfarm.service.api.GFLiveVideoBatService;
import com.callforcode.greenfarm.service.api.GFPlantTaskService;
import com.callforcode.greenfarm.task.api.LiveVideoBatTask;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class LiveVideoBatTaskImpl implements LiveVideoBatTask {

    @Value("${green-farm.file.live-path}")
    private String livePath;

    @Autowired
    private GFLiveService liveService;

    @Autowired
    private GFPlantTaskService plantTaskService;

    @Autowired
    private GFLiveVideoBatService liveVideoBatService;

    @Override
    @Scheduled(fixedRate = 60000)
    public void liveVideoTask() {
        List<GFLive> lives = liveService.getLivesByMinutes(new Date(System.currentTimeMillis() - 600000));
        if (lives != null && lives.size() > 0) {
            lives.forEach(x -> {
                    try {
                        plantTaskService.reviseOprRecordByStepId(x.getStepId(), livePath + "/" + x.getLiveId() + ".mp4");
                        liveVideoBatService.deleteLiveVideoMappingByLiveId(x.getLiveId());
                    } catch (Exception e) {
                        log.error("failed to update the live url, reason:{}, liveId:{}, stepId:{}", e.getMessage(), x.getLiveId(), x.getStepId());
                    }
                }
            );
        }
    }

    @Override
    @Scheduled(fixedRate = 20000)
    public void liveVideoUpdateTask() {
        List<Integer> idLiveList = liveService.getLiveIdListBySeconds(new Date(System.currentTimeMillis() - 60000));
        if (null != idLiveList && idLiveList.size() > 0) {
            int resultCount = liveService.updateStatusByIdLiveList(idLiveList);
            if (resultCount == 0) {
                throw new GFException("failed to update live broadcast regularly");
            }
        }
    }

}
