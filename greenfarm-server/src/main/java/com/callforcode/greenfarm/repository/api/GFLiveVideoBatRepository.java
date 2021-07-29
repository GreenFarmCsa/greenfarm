package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFLiveVideoBat;

public interface GFLiveVideoBatRepository {
    
    int add(GFLiveVideoBat record);

    void deleteLiveVideoMappingByLiveId(Integer liveId);
    
}
