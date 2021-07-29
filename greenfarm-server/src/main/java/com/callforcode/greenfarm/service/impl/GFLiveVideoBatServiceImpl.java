package com.callforcode.greenfarm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFLiveVideoBat;
import com.callforcode.greenfarm.repository.api.GFLiveVideoBatRepository;
import com.callforcode.greenfarm.service.api.GFLiveVideoBatService;

public class GFLiveVideoBatServiceImpl implements GFLiveVideoBatService {

    @Autowired
    private GFLiveVideoBatRepository repository;

    @Override
    public int add(GFLiveVideoBat record) {
        return repository.add(record);
    }

    @Override
    public void deleteLiveVideoMappingByLiveId(Integer liveId) {
        repository.deleteLiveVideoMappingByLiveId(liveId);
    }

}
