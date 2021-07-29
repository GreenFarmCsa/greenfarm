package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFSeed;

import java.util.List;

public interface GFSeedRepository {

    List<GFSeed> querySeeds();

    GFSeed querySeedBySeedId(Integer seedId);

}
