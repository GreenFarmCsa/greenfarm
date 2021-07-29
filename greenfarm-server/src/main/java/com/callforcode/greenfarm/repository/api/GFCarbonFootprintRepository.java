package com.callforcode.greenfarm.repository.api;

import com.callforcode.greenfarm.entity.GFCarbonCreditDaily;
import com.callforcode.greenfarm.entity.GFCarbonFootprint;
import com.callforcode.greenfarm.vo.CarbonCredit;

import java.util.Date;
import java.util.List;

public interface GFCarbonFootprintRepository {

    List<GFCarbonFootprint> queryCarbonFootprint(String userName);

    int addCarbonFootprint(GFCarbonFootprint gfCarbonFootprint);

    GFCarbonCreditDaily queryCarbonCreditDaily(String userName, Date date, String type);

    List<CarbonCredit> queryCarbonCreditDaily(String userName, String type);

    int addCarbonCreditDaily(GFCarbonCreditDaily gfCarbonCreditDaily);

    int updateCarbonCreditDaily(GFCarbonCreditDaily gfCarbonCreditDaily);

}
