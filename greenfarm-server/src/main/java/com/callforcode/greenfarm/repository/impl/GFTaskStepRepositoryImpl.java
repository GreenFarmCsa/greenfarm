package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.mapper.GFTaskStepMapper;
import com.callforcode.greenfarm.repository.api.GFTaskStepRepository;
import com.callforcode.greenfarm.vo.CarbonCredit;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFTaskStepRepositoryImpl implements GFTaskStepRepository {

    @Autowired
    private GFTaskStepMapper gfTaskStepMapper;

    @Override
    public List<CarbonCredit> selectActualFootprint(String userName) {
        return gfTaskStepMapper.selectActualFootprint(userName);
    }
}
