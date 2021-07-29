package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFLand;

import java.util.List;

public interface GFLandRepository {

    List<GFLand> queryLandsByFarmId(Integer farmId);

    int addLand(GFLand gfLandVo);

    int updateLand(GFLand gfLandVo);

    GFLand queryLandById(Integer landId);
}
