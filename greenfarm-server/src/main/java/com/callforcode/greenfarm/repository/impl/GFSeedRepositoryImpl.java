package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFSeed;
import com.callforcode.greenfarm.mapper.GFSeedMapper;
import com.callforcode.greenfarm.repository.api.GFSeedRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFSeedRepositoryImpl implements GFSeedRepository {

    @Autowired
    private GFSeedMapper mapper;

    @Override
    public List<GFSeed> querySeeds() {
        return mapper.selectAll();
    }

    @Override
    public GFSeed querySeedBySeedId(Integer seedId) {
        return mapper.selectByPrimaryKey(seedId);
    }
}
