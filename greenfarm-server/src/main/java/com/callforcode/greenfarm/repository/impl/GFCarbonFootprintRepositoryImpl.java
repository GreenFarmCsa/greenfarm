package com.callforcode.greenfarm.repository.impl;

import com.callforcode.greenfarm.entity.GFCarbonCreditDaily;
import com.callforcode.greenfarm.entity.GFCarbonFootprint;
import com.callforcode.greenfarm.mapper.GFCarbonCreditDailyMapper;
import com.callforcode.greenfarm.mapper.GFCarbonFootprintMapper;
import com.callforcode.greenfarm.repository.api.GFCarbonFootprintRepository;
import com.callforcode.greenfarm.util.DateToolUtils;
import com.callforcode.greenfarm.vo.CarbonCredit;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class GFCarbonFootprintRepositoryImpl implements GFCarbonFootprintRepository {

    @Autowired
    private GFCarbonFootprintMapper carbonFootprintMapper;

    @Autowired
    private GFCarbonCreditDailyMapper gfCarbonCreditDailyMapper;

    public List<GFCarbonFootprint> queryCarbonFootprint(String userName) {
        return carbonFootprintMapper.selectByUserName(userName);
    }

    public int addCarbonFootprint(GFCarbonFootprint gfCarbonFootprint) {
        gfCarbonFootprint.setCreateTime(DateToolUtils.getCurrDate());
        gfCarbonFootprint.setModifyTime(gfCarbonFootprint.getCreateTime());
        return carbonFootprintMapper.insertSelective(gfCarbonFootprint);
    }

    @Override
    public GFCarbonCreditDaily queryCarbonCreditDaily(String userName, Date date, String type) {
        GFCarbonCreditDaily carbonCreditDaily = new GFCarbonCreditDaily();
        carbonCreditDaily.setUsername(userName);
        carbonCreditDaily.setCarbonDate(date);
        carbonCreditDaily.setType(type);
        return gfCarbonCreditDailyMapper.selectByUserNameAndDate(carbonCreditDaily);
    }

    @Override
    public List<CarbonCredit> queryCarbonCreditDaily(String userName, String type) {
        GFCarbonCreditDaily carbonCreditDaily = new GFCarbonCreditDaily();
        carbonCreditDaily.setUsername(userName);
        carbonCreditDaily.setType(type);
        return gfCarbonCreditDailyMapper.selectByUserNameAndType(carbonCreditDaily);
    }

    @Override
    public int addCarbonCreditDaily(GFCarbonCreditDaily gfCarbonCreditDaily) {
        return gfCarbonCreditDailyMapper.insertSelective(gfCarbonCreditDaily);
    }

    @Override
    public int updateCarbonCreditDaily(GFCarbonCreditDaily gfCarbonCreditDaily) {
        return gfCarbonCreditDailyMapper.updateByPrimaryKeySelective(gfCarbonCreditDaily);
    }

}
