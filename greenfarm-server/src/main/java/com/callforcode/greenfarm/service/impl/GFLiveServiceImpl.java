package com.callforcode.greenfarm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.callforcode.greenfarm.entity.GFLive;
import com.callforcode.greenfarm.repository.api.GFLiveRepository;
import com.callforcode.greenfarm.service.api.GFLiveService;

public class GFLiveServiceImpl implements GFLiveService {
    @Autowired
    private GFLiveRepository repository;

    @Override
    public GFLive add(GFLive record) {
        return repository.add(record);
    }

    @Override
    public List<GFLive> queryByLive(GFLive record) {
        return repository.queryByLive(record);
    }

    @Override
    public int updateByLiveSelective(GFLive record) {
        return repository.updateByLiveSelective(record);
    }

    @Override
    public List<GFLive> getLivesByMinutes(Date customDate) {
        return repository.getLivesByMinutes(customDate);
    }

    @Override
    public List<Integer> getLiveIdListBySeconds(Date customDate) {
        return repository.getLiveIdListBySeconds(customDate);
    }

    @Override
    public int updateStatusByIdLiveList(List<Integer> idLiveList) {
        return repository.updateStatusByIdLiveList(idLiveList);
    }

}
