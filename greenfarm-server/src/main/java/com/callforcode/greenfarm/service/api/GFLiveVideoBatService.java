package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFLiveVideoBat;

public interface GFLiveVideoBatService {

    int add(GFLiveVideoBat record);

    void deleteLiveVideoMappingByLiveId(Integer liveId);

}
