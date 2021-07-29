package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.entity.GFFarmWithBLOBs;
import com.callforcode.greenfarm.entity.GFLand;
import com.callforcode.greenfarm.exception.LandNotFoundException;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.repository.api.GFFarmRepository;
import com.callforcode.greenfarm.repository.api.GFLandRepository;
import com.callforcode.greenfarm.service.api.GFLandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFLandServiceImpl implements GFLandService {

    @Autowired
    private GFLandRepository repository;

    @Autowired
    private GFFarmRepository gfFarmRepository;

    @Override
    public List<GFLand> queryLandsByFarmId(Integer farmId) {
        return repository.queryLandsByFarmId(farmId);
    }

    @Override
    public int addLand(GFLand gfLandVo) {
        GFFarmWithBLOBs queryByFarmId = gfFarmRepository.queryByFarmId(gfLandVo.getFarmId());
        if (queryByFarmId.getIdleArea() >= gfLandVo.getArea()) {
            GFFarmWithBLOBs record = new GFFarmWithBLOBs();
            record.setFarmId(gfLandVo.getFarmId());
            record.setIdleArea(queryByFarmId.getIdleArea() - gfLandVo.getArea());
            gfFarmRepository.updateByFarmWithBLOBsSelective(record);
            gfLandVo.setIsRent(false);
            int res = repository.addLand(gfLandVo);
            return res;
        } else {
            return -1;
        }

    }

    @Override
    public int updateLand(GFLand gfLand) {
        int res = repository.updateLand(gfLand);
        if (res == 1) {
            return res;
        } else if (res == 0) {
            throw new LandNotFoundException("land not found.id=" + gfLand.getLandId());
        } else {
            throw new UpdateRecordCountNotMatchException("update land error", 1, res);
        }
    }

    @Override
    public GFLand queryLandByLandId(Integer landId) {
        return repository.queryLandById(landId);
    }

}
