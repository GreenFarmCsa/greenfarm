package com.callforcode.greenfarm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFLiveVideoBat;
import com.callforcode.greenfarm.mapper.GFLiveVideoBatMapper;
import com.callforcode.greenfarm.repository.api.GFLiveVideoBatRepository;

public class GFLiveVideoBatRepositoryImpl implements GFLiveVideoBatRepository {

    @Autowired
    private GFLiveVideoBatMapper mapper;

    @Override
    public int add(GFLiveVideoBat record) {

        return mapper.insert(record);

    }

    @Override
    public void deleteLiveVideoMappingByLiveId(Integer liveId) {
        mapper.deleteLiveVideoMappingByLiveId(liveId);
    }

}
