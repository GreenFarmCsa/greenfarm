package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFSeed;

import java.util.List;

public interface GFSeedService {

    List<GFSeed> querySeeds();
   
    GFSeed querySeedBySeedId(Integer seedId);

}
