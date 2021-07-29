package com.callforcode.greenfarm.service.api;

import com.callforcode.greenfarm.entity.GFLand;

import java.util.List;

public interface GFLandService {

    List<GFLand> queryLandsByFarmId(Integer farmId);

    int addLand(GFLand gfLandVo);

    int updateLand(GFLand gfLand);
    
    GFLand queryLandByLandId(Integer landId);

}
