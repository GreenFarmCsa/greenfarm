package com.callforcode.greenfarm.service.impl;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.entity.GFCarbonFootprint;
import com.callforcode.greenfarm.exception.UpdateRecordCountNotMatchException;
import com.callforcode.greenfarm.repository.api.GFCarbonFootprintRepository;
import com.callforcode.greenfarm.service.api.GFCarbonFootprintService;
import com.callforcode.greenfarm.vo.CarbonCredit;
import com.callforcode.greenfarm.vo.GFDashboardFootprintVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GFCarbonFootprintServiceImpl implements GFCarbonFootprintService {

    @Autowired
    private GFCarbonFootprintRepository gfCarbonFootprintRepository;

    public List<GFCarbonFootprint> queryCarbonFootprint(String userName) {
        return gfCarbonFootprintRepository.queryCarbonFootprint(userName);
    }

    public int addCarbonFootprint(GFCarbonFootprint gfCarbonFootprint) {
        int res = gfCarbonFootprintRepository.addCarbonFootprint(gfCarbonFootprint);
        if (res == 1) {
            return res;
        } else {
            throw new UpdateRecordCountNotMatchException("carbonFootprint add error", 1, res);
        }
    }

    public GFDashboardFootprintVo queryFromDashboard(String userName) {
        GFDashboardFootprintVo gfDashboardFootprintVo = new GFDashboardFootprintVo();
        List<CarbonCredit> tarList = gfCarbonFootprintRepository.queryCarbonCreditDaily(userName, GreenFarmConst.GRF_CARBON_CREDIT_DAILY_TYPE_TARGET);
        List<CarbonCredit> actList = gfCarbonFootprintRepository.queryCarbonCreditDaily(userName, GreenFarmConst.GRF_CARBON_CREDIT_DAILY_TYPE_ACTUAL);
        gfDashboardFootprintVo.setTargetFootprint(tarList);
        gfDashboardFootprintVo.setActualFootprint(actList);
        return gfDashboardFootprintVo;
    }

}
