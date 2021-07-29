package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFRent;
import com.callforcode.greenfarm.mapper.GFRentMapper;
import com.callforcode.greenfarm.repository.api.GFRentRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFRentRepositoryImpl implements GFRentRepository {

    @Autowired
    private GFRentMapper mapper;

    @Override
    public int addRent(GFRent gfRent) {
        gfRent.setCreateTime(DateToolUtils.getCurrDate());
        gfRent.setModifyTime(DateToolUtils.getCurrDate());
        return mapper.insertSelective(gfRent);
    }

    @Override
    public List<GFRent> queryRentLands(String userName) {
        return mapper.queryByUserName(userName);
    }

    @Override
    public List<GFRent> queryRentLandsByUsernameAndFarmId(String userName, Integer farmId) {
        return mapper.queryRentLandsByUsernameAndFarmId(userName, farmId);
    }

    @Override
    public List<GFRent> queryAllRentLands() {
        return mapper.queryAllRentLands();
    }

    @Override
    public GFRent querySubscribeInfoByLandId(Integer landId) {
        return mapper.querySubscribeByLandId(landId);
    }

}
