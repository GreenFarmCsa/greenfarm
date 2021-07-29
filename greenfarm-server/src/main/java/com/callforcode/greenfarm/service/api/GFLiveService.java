package com.callforcode.greenfarm.service.api;

import java.util.Date;
import java.util.List;

import com.callforcode.greenfarm.entity.GFLive;

public interface GFLiveService {

    GFLive add(GFLive record);

    List<GFLive> queryByLive(GFLive record);

    int updateByLiveSelective(GFLive record);

    List<GFLive> getLivesByMinutes(Date customDate);

    List<Integer> getLiveIdListBySeconds(Date customDate);

    int updateStatusByIdLiveList(List<Integer> idLiveList);

}
