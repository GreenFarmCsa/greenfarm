package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.entity.GFSeed;
import com.callforcode.greenfarm.repository.api.GFSeedRepository;
import com.callforcode.greenfarm.service.api.GFSeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFSeedServiceImpl implements GFSeedService {

    @Autowired
    private GFSeedRepository repository;

    @Override
    public List<GFSeed> querySeeds() {
        return repository.querySeeds();
    }

    @Override
    public GFSeed querySeedBySeedId(Integer seedId) {
        return repository.querySeedBySeedId(seedId);
    }
}
