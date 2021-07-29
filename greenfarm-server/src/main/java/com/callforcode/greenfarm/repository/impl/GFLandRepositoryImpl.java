package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFLand;
import com.callforcode.greenfarm.mapper.GFLandMapper;
import com.callforcode.greenfarm.repository.api.GFLandRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFLandRepositoryImpl implements GFLandRepository {

    @Autowired
    private GFLandMapper mapper;

    @Override
    public List<GFLand> queryLandsByFarmId(Integer farmId) {
        return mapper.selectByFarmId(farmId);
    }

    @Override
    public int addLand(GFLand gfLand) {
        gfLand.setCreateTime(DateToolUtils.getCurrDate());
        gfLand.setModifyTime(DateToolUtils.getCurrDate());
        return mapper.insertSelective(gfLand);
    }

    @Override
    public int updateLand(GFLand gfLand) {
        gfLand.setModifyTime(DateToolUtils.getCurrDate());
        gfLand.setCreateTime(null);
        return mapper.updateByPrimaryKeySelective(gfLand);
    }

    @Override
    public GFLand queryLandById(Integer landId) {
        return mapper.selectByLandId(landId);
    }

}
